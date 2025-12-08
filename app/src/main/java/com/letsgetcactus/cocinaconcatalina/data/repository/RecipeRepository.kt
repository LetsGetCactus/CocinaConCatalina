package com.letsgetcactus.cocinaconcatalina.data.repository

import android.net.Uri
import android.util.Log
import com.letsgetcactus.cocinaconcatalina.data.FirebaseConnection
import com.letsgetcactus.cocinaconcatalina.model.Recipe
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Object to connect the RecipeViewModel with the Firebase singleton
 */
object RecipeRepository {

    /**
     * Gets all original recipes from the db
     * @return a list of all the asian original recipes
     */
    suspend fun getAllAsianOriginalRecipes(): List<Recipe> =
        withContext(Dispatchers.IO) {
            try {
                val result = FirebaseConnection.getAsianOriginalRecipes()
                result.sortedBy { it.title.lowercase() }
            } catch (e: Exception) {
                Log.e("RecipeRepository", "Error getting asian og full list of recipes", e)
                emptyList()
            }
        }


//    /**
//     * Gets all recipes form Firebase and the user's modified ones
//     * @param userId ID from the user to obtains his modified subcollection of recipes
//     * @return a list of all the recipes on the DB
//     */
//    suspend fun getAllRecipesInDb(userId: String?): List<Recipe> = withContext(
//        Dispatchers.IO
//    ) {
//        try {
//            val ogRecipes = FirebaseConnection.getAsianOriginalRecipes()
//            val modRecipes = if (!userId.isNullOrBlank()) FirebaseConnection.getUserModifiedRecipes(
//                userId,
//
//            ) else emptyList()
//
//            (ogRecipes + modRecipes).sortedBy { it.title.lowercase() }
//        } catch (e: Exception) {
//            Log.e("RecipeRepository", "Error geting all the Recipes from DB", e)
//            emptyList()
//        }
//    }

//    /**
//     * For searching filtered Recipes from the FilterRecipeScreen
//     * @param userId Id form user to obtain his modified recipes subcollection
//     * @param language language for the recipes to be shown in
//     * @param origin country of origin from the recipe
//     * @param category of the recipe
//     * @param difficulty DifficultyEnum
//     * @param maxPrepTime needed time to make the recipe
//     * @param maxIngredients maximun number of ingredients needed for the recipe to be made
//     * @param minRating minimun rating of the recipe
//     */
//    suspend fun filteredRecipes(
//        userId: String?,
//        language: String,
//        origin: String? = null,
//        category: String? = null,
//        difficulty: Any? = null,
//        maxPrepTime: Int? = null,
//        maxIngredients: Int? = null,
//        minRating: Int? = null,
//    ): List<Recipe> = withContext(Dispatchers.IO) {
//        try {
//            val allRecipes = getAllRecipesInDb(userId, language)
//
//            allRecipes.filter { filteredBy ->
//                (origin == null || filteredBy.origin.country.equals(origin, ignoreCase = true)) &&
//                        (category == null || filteredBy.categoryList.any { cat ->
//                            cat.name.equals(
//                                category,
//                                ignoreCase = true
//                            )
//                        }) &&
//                        (difficulty == null || filteredBy.dificulty == difficulty) &&
//                        (maxPrepTime == null || filteredBy.prepTime <= maxPrepTime) &&
//                        (maxIngredients == null || filteredBy.ingredientList.size <= maxIngredients) &&
//                        (minRating == null || filteredBy.avgRating >= minRating)
//            }.sortedBy {
//                it.title.lowercase()
//            }
//
//        } catch (e: Exception) {
//            Log.e("RecipeRepository", "Error trying filtering recipes", e)
//            emptyList()
//        }
//    }


    /**
     * ADMIN ONLY
     * Adds a new asian original recipe to the db
     * @param recipe to be uploaded
     */
    suspend fun addRecipeToDB(recipe: Recipe, img: Uri?): Boolean{
        val urlFromStorage= FirebaseConnection.imgToFirestore(img)
        Log.i("recipeRepository","Got url from storage: $urlFromStorage")
        if(urlFromStorage!=null) recipe.img=urlFromStorage
        Log.i("recipeRepository","Sending prepared recipe to firebase $recipe")

        FirebaseConnection.uploadRecipeToDb(recipe)
        return true
    }

    /**
     * To rate a recipe on ItemRecipeScreen
     * It sums to the rating and shows de average rating from all the votes
     * @param id from the recipe who got the vote
     * @param rating int for the rating received
     */
    suspend fun rateRecipe(id: String, rating: Int){
        FirebaseConnection.rateRecipe(id,rating,null)
    }
}