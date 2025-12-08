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

    /**
     * ADMIN ONLY
     * Adds a new asian original recipe to the db
     * @param recipe to be uploaded
     */
    suspend fun addRecipeToDB(recipe: Recipe, img: Uri?): Boolean {
        val urlFromStorage = FirebaseConnection.imgToFirestore(img)
        Log.i("recipeRepository", "Got url from storage: $urlFromStorage")
        if (urlFromStorage != null) recipe.img = urlFromStorage
        Log.i("recipeRepository", "Sending prepared recipe to firebase $recipe")

        FirebaseConnection.uploadRecipeToDb(recipe)
        return true
    }

    /**
     * To rate a recipe on ItemRecipeScreen
     * It sums to the rating and shows de average rating from all the votes
     * @param id from the recipe who got the vote
     * @param rating int for the rating received
     */
    suspend fun rateRecipe(id: String, rating: Int) {
        FirebaseConnection.rateRecipe(id, rating, null)
    }
}