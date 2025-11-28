package com.letsgetcactus.cocinaconcatalina.data

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import com.google.firebase.functions.FirebaseFunctions
import com.letsgetcactus.cocinaconcatalina.data.dto.RecipeDto
import com.letsgetcactus.cocinaconcatalina.data.mapper.toMap
import com.letsgetcactus.cocinaconcatalina.data.mapper.toRecipe
import com.letsgetcactus.cocinaconcatalina.model.Allergen
import com.letsgetcactus.cocinaconcatalina.model.Category
import com.letsgetcactus.cocinaconcatalina.model.Ingredient
import com.letsgetcactus.cocinaconcatalina.model.Origin
import com.letsgetcactus.cocinaconcatalina.model.Recipe
import com.letsgetcactus.cocinaconcatalina.model.User
import com.letsgetcactus.cocinaconcatalina.model.enum.AllergenEnum
import com.letsgetcactus.cocinaconcatalina.model.enum.DificultyEnum
import com.letsgetcactus.cocinaconcatalina.model.enum.UnitsTypeEnum
import kotlinx.coroutines.tasks.await
import java.util.Locale

/**
 * Singleton to connect with Firebase
 * It also uses the dto and mappers to download and 'translate' correctly the data from the DB into the app and vice versa
 */
object FirebaseConnection {

    private val db = Firebase.firestore


    //ALL RECIPES
    /**
     * Gets a recipe by its ID
     * It first check on modifiedRecipes, then original ones
     * @param userId id from the user
     * @param recipeId to look for the recipe
     * @param language to get the recipes in that language
     * @return Recipe or null
     */
    suspend fun getRecipeById(
        userId: String,
        recipeId: String,
        language: String = Locale.getDefault().language
    ): Recipe? {
        return try {
            //Modified
            val modified = db.collection("users")
                .document(userId)
                .collection("modifiedRecipes")
                .document(recipeId)
                .get()
                .await()

            if (modified.exists()) return modified.toObject(Recipe::class.java)
            //If not found, search on original
            val original = db.collection("asianOriginalRecipes")
                .document(recipeId)
                .get()
                .await()

            original.toObject(RecipeDto::class.java)?.toRecipe(language)

        } catch (e: Exception) {
            Log.e("FirebaseConnection", "Error fetching recipe by id $recipeId", e)
            null
        }
    }


    //ASIAN ORIGINAL RECIPES
    /**
     * Gets all asian original recipes and translate Firebase's data into our data for the app
     * @param language to get the recipes in that language
     * @return a list of all the original asian recipes in the database
     */
    suspend fun getAsianOriginalRecipes(language: String = Locale.getDefault().language): List<Recipe> {
        // Get the apps language
        // If it's not determined, use system instead , if system not supported, use english
        val supportedLanguages = listOf("es", "en", "gl")
        if (language !in supportedLanguages) "en"

        return try {
            val result = db.collection("asianOriginalRecipes").get().await()
            val dto = result.toObjects(RecipeDto::class.java)

            //Converts in language through mapper
            val listRecipes = dto.map { it.toRecipe(language) } // RecipeMapper


            Log.i("FirebaseConnection", "Got ${listRecipes.size} recipes in $language")
            listRecipes

        } catch (e: Exception) {
            Log.e("FirebaseRepository", "Error fetching asian original recipes", e)
            emptyList()
        }
    }

    /**
     * To get only one recipe
     * @param recipeId Id from the Recipe to be getting
     * @return a Recipe
     */
    suspend fun getAsianOriginalRecipeById(
        recipeId: String,
        language: String = Locale.getDefault().language
    ): Recipe? {
        return try {
            val supportedLanguages = listOf("es", "en", "gl")
            if (language !in supportedLanguages) "en"

            val result = db.collection("asianOriginalRecipes")
                .document(recipeId)
                .get()
                .await()

            result.toObject(RecipeDto::class.java)?.toRecipe(language)
        } catch (e: Exception) {
            Log.e("FirebaseConnection", "Error trying to get a single Recipe from asianOg", e)
            null
        }
    }

    /**
     * To upload to Firebase a recipe to the asianOriginalRecipe collection
     * Google Cloud Translation API will translate it before saving it
     * @param recipe to be saved
     * @param originalLanguage for the API to translate into the 2 others
     * @return boolean whether the upload has been successful or not
     */
    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun uploadRecipeAndTranslations(
        recipe: Recipe,
        originalLanguage: String? = null
    ): Boolean {
        return try {
            val functions = FirebaseFunctions.Companion.getInstance("europe-southwest1")


            // If there's not a language passed on originalLanguage, it detects it by the system language
            val language = originalLanguage ?: Locale.getDefault().language

            val data = mapOf(
                "receta" to recipe.toMap(),
                "idiomaOriginal" to language
            )

            // Calls Cloud Function: call()
            val result = functions
                .getHttpsCallable("traducirReceta") //Method for calling the API (script in javascript)
                .call(data)
                .await()


            val response = result.data as? Map<*, *>
            val success = response?.get("success") as? Boolean ?: false

            if (!success) {
                Log.e("FirebaseConnection", "Error on Transaltion API ${response?.get("error")}")
            } else {
                Log.i("FirebaseConnection", "Correctly translated and saved ${recipe.title}")
            }

            success
        } catch (e: Exception) {
            Log.e("FirebaseConnection", "Error uploading/translating recipe: ${e.message}", e)
            false
        }
    }

    //USER
    /**
     * Gets an User by Id
     * @param userId Id from the user to get
     * @returns the User
     */
    suspend fun getUserById(userId: String): User? {
        return try {
            Log.i("FirebaseConnection", "Intentando obtener usuario con ID: $userId")
            val result = db.collection("users")
                .document(userId)
                .get()
                .await()
            Log.i("FirebaseConnection", "Documento obtenido: ${result.exists()}")
            val user = result.toObject(User::class.java)
            Log.i("FirebaseConnection", "Usuario parseado: $user")
            user
        } catch (e: Exception) {
            Log.e("FirebaseConnection", "Could not fetch user: $userId", e)
            null
        }
    }


    /**
     * Adds an User to the DB
     * @param user Object User tu save on Firebase
     */
    suspend fun saveUser(user: User) {
        try {
            db.collection("users")
                .document(user.id)
                .set(user)
                .await()

            Log.i("FirebaseConnection", "User saved in DB")
        } catch (e: Exception) {
            Log.e("FirebaseConnection", " Could not save user ${user.id} into DB", e)
        }
    }


    //USER'S RECIPES: fav and mod
    /**
     * Gets all modified recipes from user
     * @param userId Id from the user to obtain his subcollection of modified recipes
     * @param language to get the recipes in that language
     * @return a list of modified recipes by the user or an empty list
     */
    suspend fun getUserModifiedRecipes(
        userId: String,
        language: String = Locale.getDefault().language
    ):List<Recipe> {
        return try {
            val result = db.collection("users")
                .document(userId)
                .collection("modifiedRecipes")
                .get()
                .await()

            result.documents.mapNotNull { doc ->
                val data = doc.data ?: return@mapNotNull null

                // Title
                val title = when (val t = data["title"]) {
                    is String -> t
                    is Map<*, *> -> (t[Locale.getDefault().language] ?: t["en"] ?: "") as String
                    else -> ""
                }

                // Steps
                val steps = (data["steps"] as? List<*>)?.map { step ->
                    when (step) {
                        is String -> step
                        is Map<*, *> -> (step[Locale.getDefault().language] ?: step["en"] ?: "") as String
                        else -> ""
                    }
                } ?: emptyList()

                // Ingredients
                val ingredientList = (data["ingredientList"] as? List<*>)?.mapNotNull { ing ->
                    val map = ing as? Map<*, *> ?: return@mapNotNull null
                    val name = map["name"] as? String ?: ""
                    val quantity = map["quantity"]?.toString() ?: ""
                    val unitStr = map["unit"] as? String ?: "UNITS"
                    val unit = try {
                        UnitsTypeEnum.valueOf(unitStr)
                    } catch (e: Exception) {
                        UnitsTypeEnum.UNITS
                    }
                    Ingredient(name, quantity, unit)
                } ?: emptyList()

                // Allergens
                val allergenList = (data["allergenList"] as? List<*>)?.mapNotNull { all ->
                    val map = all as? Map<*, *> ?: return@mapNotNull null
                    val nameStr = map["name"] as? String ?: return@mapNotNull null
                    val imgEnum = try {
                        AllergenEnum.valueOf(nameStr.uppercase())
                    } catch (e: Exception) {
                        null
                    }
                    imgEnum?.let { Allergen(nameStr, it) }
                } ?: emptyList()

                // Categories
                val categoryList = (data["categoryList"] as? List<*>)?.mapNotNull { cat ->
                    val map = cat as? Map<*, *> ?: return@mapNotNull null
                    val id = (map["id"]?.toString()?.toIntOrNull() ?: 0)
                    val name = map["name"] as? String ?: ""
                    Category(id, name)
                } ?: emptyList()

                // Dificulty
                val dificulty = (data["dificulty"] as? String)?.let { str ->
                    DificultyEnum.entries.find { it.name == str }
                } ?: DificultyEnum.EASY

                // Origin
                val originMap = data["origin"] as? Map<*, *>
                val origin = Origin(
                    id = (originMap?.get("id") as? Number)?.toInt() ?: 0,
                    country = originMap?.get("country") as? String ?: "",
                    flag = (originMap?.get("flag")?.toString()?.toIntOrNull() ?: 0)
                )

                // Recipe final
                Recipe(
                    id = data["id"] as? String ?: doc.id,
                    title = title,
                    avgRating = (data["avgRating"] as? Number)?.toInt() ?: 0,
                    steps = steps,
                    ingredientList = ingredientList,
                    allergenList = allergenList,
                    categoryList = categoryList,
                    prepTime = (data["prepTime"] as? Number)?.toInt() ?: 0,
                    dificulty = dificulty,
                    origin = origin,
                    portions = (data["portions"] as? Number)?.toInt() ?: 1,
                    active = data["active"] as? Boolean ?: true,
                    img = data["img"] as? String ?: "",
                    video = data["video"] as? String
                )
            }

        } catch (e: Exception) {
            Log.e("FirebaseConnection", "Error fetching modified recipes", e)
            emptyList()
        }
    }

//    /**
//     * Adds a modified recipe to user's modifiedRecipes subcollection
//     * @param userId Id from the user
//     * @param recipe modified Recipe to be saved in
//     */
//    suspend fun saveUserModifiedRecipe(
//        userId: String,
//        recipe: Recipe,
//        language: String? = null
//    ): Boolean {
//
//        val currentUser = FirebaseAuth.getInstance().currentUser
//        if (currentUser == null) {
//            Log.e("FirebaseConnection", "Usuario no autenticado")
//            return false
//        }
//        try {
//
//            val functions = FirebaseFunctions.Companion.getInstance("europe-southwest1")
//
//            val language = language ?: Locale.getDefault().language
//
//            val data = mapOf(
//                "receta" to recipe.toMap(),
//                "idiomaOriginal" to language
//            )
//
//
//            val result = functions
//                .getHttpsCallable("traducirRecetaModificada") //script
//                .call(data)
//                .await()
//
//
//            val response = result.data as? Map<*, *>
//            val success = response?.get("success") as? Boolean ?: false
//
//            if (!success) {
//                Log.e(
//                    "FirebaseConnection",
//                    "Error on Transaltion API for modified recipe ${response?.get("error")}"
//                )
//            } else {
//                Log.i(
//                    "FirebaseConnection",
//                    "Correctly translated and saved modified recipe ${recipe.title}"
//                )
//            }
//
//            return success
//        } catch (e: Exception) {
//            Log.e(
//                "FirebaseConnection",
//                "Error saving modified recipe ${recipe.id} from user $userId",
//                e
//            )
//            return false
//
//        }
//    }
//
    /**
     * Adds a modified recipe to user's modifiedRecipes subcollection (does not translate)
     * @param userId Id from the user
     * @param recipe modified Recipe to be saved in
     */
    suspend fun addModifiedRecipe(userId: String, recipe: Recipe) {
        try {
            //Add (Mod) to title
            if(recipe.title.trim().endsWith("(Mod)",ignoreCase = true)) recipe.title else recipe.title + "(Mod)"

            Log.i("FirebaseConnection","receta a subir $recipe")

            db.collection("users")
                .document(userId)
                .collection("modifiedRecipes")
                .document(recipe.id)
                .set(recipe.toMap())
                .await()

            Log.i("FirebaseConnection", "${recipe.title} correctly saved for user")
        } catch (e: Exception) {
            Log.e("FirebaseConnection", "Error saving modified recipe: ${recipe.title}", e)
        }
    }



    /**
     * Gets user's favourites Recipes by their Id's
     * @param userId Id from the user to obtain his subcollection
     * @param language to get the recipes in that language
     * @return a list of his fav recipes or empty list if there's none
     */
    suspend fun getUserFavouriteRecipes(
        userId: String,
        language: String = Locale.getDefault().language
    ): List<Recipe> {
        val supportedLanguages = listOf("es", "en", "gl")
        if (language !in supportedLanguages) "en"

        return try {
            val result = db.collection("users")
                .document(userId)
                .collection("favouriteRecipes")
                .get()
                .await()

            Log.i("FirebaseConnect8ion", "Fetched ${result.size()} favourites recipes (String ID)")
            result.toObjects(RecipeDto::class.java).map { it.toRecipe(language) }


        } catch (e: Exception) {
            Log.e("FirebaseConnection", " Error fetching favourites recipes (String ID)", e)
            emptyList()
        }
    }

    /**
     * Adds a recipe to the user's favorites
     * @param userId Id from the user itself to use his subcollection of favs
     * @param recipeId Id from the recipe to be saved on the user's favs
     */
    suspend fun addRecipeToUsersFavorites(userId: String, recipeId: String) {
        try {
            val result = db.collection("users")
                .document(userId)
                .collection("favouriteRecipes")
                .document(recipeId)

            result.set(mapOf("id" to recipeId)).await()
            Log.i("FirebaseConnection", "Added a recipe to users favs")
        } catch (e: Exception) {
            Log.i("FirebaseConnection", "Coul not add a recipe to users favs", e)
        }
    }

    /**
     * Removes a recipe from user's favs
     * @param userId Id from the user
     * @param recipeId Id from the Recipe to be removed
     */
    suspend fun removeUsersFavouriteRecipe(userId: String, recipeId: String) {
        try {
            db.collection("users")
                .document(userId)
                .collection("favouriteRecipes")
                .document(recipeId)
                .delete()
                .await()
            Log.i("FirebaseConnection", "Removed recipe $recipeId from user's favs")
        } catch (e: Exception) {
            Log.e("FirebaseConnection", "Error removing favourite $recipeId on user $userId", e)
        }
    }
}