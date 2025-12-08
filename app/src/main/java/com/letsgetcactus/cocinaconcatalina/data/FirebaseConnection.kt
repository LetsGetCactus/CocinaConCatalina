package com.letsgetcactus.cocinaconcatalina.data


import android.net.Uri
import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.FirebaseStorage
import com.letsgetcactus.cocinaconcatalina.data.dto.RecipeDto
import com.letsgetcactus.cocinaconcatalina.data.mapper.toMap
import com.letsgetcactus.cocinaconcatalina.data.mapper.toRecipe
import com.letsgetcactus.cocinaconcatalina.model.Recipe
import com.letsgetcactus.cocinaconcatalina.model.User
import kotlinx.coroutines.tasks.await
import java.math.BigDecimal
import java.math.RoundingMode
import java.util.Locale
import java.util.UUID


/**
 * Singleton to connect with Firebase
 */
object FirebaseConnection {


    //ALL RECIPES
    /**
     * Gets a recipe by its ID
     * It first check on modifiedRecipes, then original ones
     * @param userId id from the user
     * @param recipeId to look for the recipe
     * @return Recipe or null
     */
    suspend fun getRecipeById(userId: String,recipeId: String): Recipe? {
        return try {
            //Modified
            val modified = Firebase.firestore.collection("users")
                .document(userId)
                .collection("modifiedRecipes")
                .document(recipeId)
                .get()
                .await()

            if (modified.exists()) return modified.toObject(Recipe::class.java)
            //If not found, search on original
            val original = Firebase.firestore.collection("asianOriginalRecipes")
                .document(recipeId)
                .get()
                .await()

            original.toObject(RecipeDto::class.java)?.toRecipe()

        } catch (e: Exception) {
            Log.e("FirebaseConnection", "Error fetching recipe by id $recipeId", e)
            null
        }
    }


    //ASIAN ORIGINAL RECIPES
    /**
     * Gets all asian original recipes and translate Firebase's data into our data for the app
     * @return a list of all the original asian recipes in the database
     */
    suspend fun getAsianOriginalRecipes(): List<Recipe> {

        return try {
            val result = Firebase.firestore.collection("asianOriginalRecipes").get().await()
            val dto = result.toObjects(RecipeDto::class.java)

            //Converts in language through mapper
            val listRecipes = dto.map { it.toRecipe() } // RecipeMapper


            Log.i("FirebaseConnection", "Got ${listRecipes.size} recipes ")
            listRecipes

        } catch (e: Exception) {
            Log.e("FirebaseRepository", "Error fetching asian original recipes", e)
            emptyList()
        }
    }



    /**
     * To upload an image ti Firestore and get it's uri to save on a Recipe in db
     * @param img Uri from the image to upload
     */
    suspend fun imgToFirestore(img: Uri?): String? {
        try {
            val storage = FirebaseStorage.getInstance().reference.child("${UUID.randomUUID()}")
            //need to be converted cause it only works with uri or ByteArray
            if (img != null) {
                storage.putFile(img).await()

                val imgUrl = storage.downloadUrl.await()

                Log.i(
                    "FirebaseConnection",
                    "Uploading recipe image to obtain its url (String) on Storage"
                )
                return imgUrl.toString()
            } else {
                Log.i("FirebaseConnection", "Image is null. Coul not upload")
                return null
            }
        } catch (e: Exception) {
            Log.i("FirebaseConnection", "Error on upolading to Storage recipe's img", e)
            return e.message
        }
    }


    /**
     * Uploads a new asianOriginalRecipe to the db
     * @param recipe to be uploaded
     */
    suspend fun uploadRecipeToDb(recipe: Recipe): Boolean{
        return try {
            val uid = FirebaseAuth.getInstance().currentUser?.uid
            if (uid == null) {
                Log.e("FirebaseConnection", "User not logged in")
                return false
            }

            Log.i("FirebaseConnection", "Uploading recipe directly as $uid")

            FirebaseFirestore.getInstance()
                .collection("asianOriginalRecipes")
                .document(recipe.id)
                .set(recipe.toMap())
                .await()

            Log.i("FirebaseConnection", "Recipe uploaded without cloud functions")
            true

        } catch (e: Exception) {
            Log.e("FirebaseConnection", "Error uploading recipe: ${e.message}", e)
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
            val result = Firebase.firestore.collection("users")
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
            Firebase.firestore.collection("users")
                .document(user.id)
                .set(user)
                .await()

            Log.i("FirebaseConnection", "User saved in DB")
        } catch (e: Exception) {
            Log.e("FirebaseConnection", " Could not save user ${user.id} into DB", e)
        }
    }


    /**
     * Suspend function to get the user an email to help him restore his password
     * Waits till Firebase executes or not the action
     * @param email from the user to be used
     * @return true or false whether the user could obtain a new password or not
     */
    suspend fun getUserForgottenPassword(email: String): Boolean {
        return try {
            FirebaseAuth.getInstance()
                .sendPasswordResetEmail(email)
                .await()
            true
        } catch (_: Exception) {
            false

        }
    }

    /**
     * Deletes all user data in Firebase users collection and Firebase Auth
     * @param userId Id from the user collection to be deleted
     * @return Boolean whether data was fully deleted or not
     */
    suspend fun deleteUserAccount(userId: String): Boolean {
        return try {
            val auth = FirebaseAuth.getInstance()

            val currentUser = auth.currentUser
            if (currentUser != null && currentUser.uid == userId) {

                val userCollection = Firebase.firestore.collection("users")
                    .document(userId)

                val favs = userCollection.collection("favouriteRecipes").get().await()
                favs.documents.forEach { it.reference.delete().await() }

                val mods = userCollection.collection("modifiedRecipes").get().await()
                mods.documents.forEach { it.reference.delete().await() }


                userCollection.delete().await()


                currentUser.delete().await()
                true
            } else {
                false
            }
        } catch (e: Exception) {
            Log.e("FirebaseConnection", "Error deleting user account $userId", e)
            false
        }
    }

    //USER'S RECIPES: fav and mod
    /**
     * Gets all modified recipes from user
     * @param userId Id from the user to obtain his subcollection of modified recipes
     * @return a list of modified recipes by the user or an empty list
     */
    suspend fun getUserModifiedRecipes(userId: String): List<Recipe> {
        return try {
            val result = Firebase.firestore.collection("users")
                .document(userId)
                .collection("modifiedRecipes")
                .get()
                .await()

            result.toObjects(RecipeDto::class.java).map { it.toRecipe() }


        } catch (e: Exception) {
            Log.e("FirebaseConnection", "Error fetching modified recipes", e)
            emptyList()
        }
    }


    /**
     * Adds a modified recipe to user's modifiedRecipes subcollection (does not translate)
     * @param userId Id from the user
     * @param recipe modified Recipe to be saved in
     */
    suspend fun addModifiedRecipe(userId: String, recipe: Recipe) {
        try {

            Log.i("FirebaseConnection", "receta a subir $recipe")

            Firebase.firestore.collection("users")
                .document(userId)
                .collection("modifiedRecipes")
                .document(recipe.id)
                .set(recipe.toMap())
                .await()

            Log.i("FirebaseConnection", "Recipe:${recipe.toMap()} correctly saved for user")
        } catch (e: Exception) {
            Log.e("FirebaseConnection", "Error saving modified recipe: ${recipe.title}", e)
        }
    }


    /**
     * Removes a modified recipe from the user
     * @param recipeId Recipe to be deleted
     * @param userId Id from the user whose subcolection has the recipe to be deleted
     * @return boolean true if the recipe was deleted, fals if not
     *
     */
    suspend fun removeModifiedFromUser(recipeId: String, userId: String) {
        try {
            Firebase.firestore.collection("users")
                .document(userId)
                .collection("modifiedRecipes")
                .document(recipeId)
                .delete()
                .await()

            Log.i("FirebaseConnection", "Recipe ${recipeId}deleted from user's modified")
        } catch (e: Exception) {
            Log.e("FirebaseConnection", "Could no delete recipe $recipeId from user's modified", e)

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
            val result = Firebase.firestore.collection("users")
                .document(userId)
                .collection("favouriteRecipes")
                .get()
                .await()

            Log.i(
                "FirebaseConnect8ion",
                "Fetched ${result.size()} favourites recipes (String ID)"
            )
            result.toObjects(RecipeDto::class.java).map { it.toRecipe() }


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
            val result = Firebase.firestore.collection("users")
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
            Firebase.firestore.collection("users")
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


    /**
     * To rate a recipe on ItemRecipeScreen
     * It sums to the rating and shows de average rating from all the votes
     * @param recipeId from the recipe who got the vote
     * @param rating int for the rating received
     * @param userId Id from the user, only if it comes form a modified recipe
     */
    suspend fun rateRecipe(recipeId: String, rating: Int, userId: String?) {

        val recipeToRate =if (userId== null) {
            Log.i("FirebaseConnection", "Actualizando puntuacion de receta Og")
            Firebase.firestore.collection("asianOriginalRecipes")
                .document(recipeId)



        } else {
            Log.i("FirebaseConnection", "Actualizando puntuacion de receta MOD")

            Firebase.firestore.collection("users")
                .document(userId)
                .collection("modifiedRecipes")
                .document(recipeId)
        }

            try {
                Firebase.firestore.runTransaction { transaction ->
                    val snapshot = transaction.get(recipeToRate)

                    val totalRating = snapshot.getLong("totalRating")?.toInt() ?: 0
                    val currentCount = snapshot.getLong("ratingCount")?.toInt() ?: 0
                    Log.i(
                        "FirebaseConnection",
                        "Puntuacion anterior: \n" +
                                "totalRating= $totalRating\n" +
                                "currentCount= $currentCount\n" +
                                "total=${(totalRating/ currentCount).toDouble()}"
                    )

                    val newRating = totalRating + rating
                    val newCount = currentCount +1
                    val newAvg = BigDecimal(newRating.toDouble() / newCount).setScale(2,
                        RoundingMode.HALF_UP).toDouble()

                    Log.i("FirebaseConnection", "Puntuacion nueva:\n" +
                            "newRating= $newRating\n" +
                            "newCount= $newCount\n" +
                            "total= $newAvg")
                    transaction.update(
                        recipeToRate, mapOf(
                            "ratingSum" to newRating,
                            "ratingCount" to newCount,
                            "avgRating" to newAvg
                        )
                    )
                    return@runTransaction newAvg
                }.await()
            }catch (e: Exception){
                Log.e("FirebaseConnection","Error en rateRecipes: ${e.message}")
            }
    }
}