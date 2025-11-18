package com.letsgetcactus.cocinaconcatalina.database

import com.letsgetcactus.cocinaconcatalina.model.Recipe
import com.letsgetcactus.cocinaconcatalina.model.database.FirebaseConnection

object RecipeRepository {

    /**
     * Gets a recipe from Firestore by ID
     * @param id from the Recipe
     * @return Recipe or null
     */
    suspend fun getRecipeById(id: String): Recipe? {
        return try {
            FirebaseConnection.getRecipeById(id)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}