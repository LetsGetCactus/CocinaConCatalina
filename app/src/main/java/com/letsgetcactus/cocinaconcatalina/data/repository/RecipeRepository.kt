package com.letsgetcactus.cocinaconcatalina.data.repository

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.letsgetcactus.cocinaconcatalina.data.FirebaseConnection
import com.letsgetcactus.cocinaconcatalina.model.Recipe
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.Locale

object RecipeRepository {



    /**
     * Gets all modified recipes from the user
     * This method id called from the menu drawer
     * @param userId Id from user to obtain his subcollection
     * @return a list of the user's modified recipes
     */
    suspend fun getAllAsianOriginalRecipes(language: String): List<Recipe> =
        withContext(Dispatchers.IO) {
            try {
                val result = FirebaseConnection.getAsianOriginalRecipes(language)
                result.sortedBy { it.title.lowercase() }
            } catch (e: Exception) {
                Log.e("RecipeRepository", "Error getting asian og full list of recipes", e)
                emptyList()
            }
        }


    /**
     * To get only one recipe
     * @param userId Id from user to search in his modified recipes first, in case there is an userId
     * @param recipeId ID from the recipe we are searching for
     * @param language to obtain the recipe in
     * @return a recipe or null whether the Recipe is found or not
     */
    suspend fun getRecipeById(userId: String?, recipeId: String, language: String): Recipe? =
        withContext(Dispatchers.IO) {
            try {
                //If there's an userId first check modified
                if (!userId.isNullOrBlank()) {
                    FirebaseConnection.getRecipeById(userId, recipeId, language)
                        ?.let { return@withContext it }
                }
                //else search on asianOriginal
                FirebaseConnection.getAsianOriginalRecipeById(recipeId, language)
            } catch (e: Exception) {
                Log.e("RecipeRepository", "Error getRecipeById $recipeId", e)
                null
            }
        }

    /**
     * Gets all recipes form Firebase and the user's modified ones
     * @param userId ID from the user to obtains his modified subcollection of recipes
     * @param language for the recipes to be shown
     * @return a list of all the recipes on the DB
     */
    suspend fun getAllRecipesInDb(userId: String?, language: String): List<Recipe> = withContext(
        Dispatchers.IO
    ) {
        try {
            val ogRecipes = FirebaseConnection.getAsianOriginalRecipes(language)
            val modRecipes = if (!userId.isNullOrBlank()) FirebaseConnection.getUserModifiedRecipes(
                userId,
                language
            ) else emptyList()

            (ogRecipes + modRecipes).sortedBy { it.title.lowercase() }
        } catch (e: Exception) {
            Log.e("RecipeRepository", "Error geting all the Recipes from DB", e)
            emptyList()
        }
    }

    /**
     * For searching filtered Recipes from the FilterRecipeScreen
     * @param userId Id form user to obtain his modified recipes subcollection
     * @param language language for the recipes to be shown in
     * @param origin country of origin from the recipe
     * @param category of the recipe
     * @param difficulty DifficultyEnum
     * @param maxPrepTime needed time to make the recipe
     * @param maxIngredients maximun number of ingredients needed for the recipe to be made
     * @param minRating minimun rating of the recipe
     */
    suspend fun filteredRecipes(
        userId: String?,
        language: String,
        origin: String? = null,
        category: String? = null,
        difficulty: Any? = null,
        maxPrepTime: Int? = null,
        maxIngredients: Int? = null,
        minRating: Int? = null,
    ): List<Recipe> = withContext(Dispatchers.IO) {
        try {
            val allRecipes = getAllRecipesInDb(userId, language)

            allRecipes.filter { filteredBy ->
                (origin == null || filteredBy.origin.country.equals(origin, ignoreCase = true)) &&
                        (category == null || filteredBy.categoryList.any { cat ->
                            cat.name.equals(
                                category,
                                ignoreCase = true
                            )
                        }) &&
                        (difficulty == null || filteredBy.dificulty == difficulty) &&
                        (maxPrepTime == null || filteredBy.prepTime <= maxPrepTime) &&
                        (maxIngredients == null || filteredBy.ingredientList.size <= maxIngredients) &&
                        (minRating == null || filteredBy.avgRating >= minRating)
            }.sortedBy {
                it.title.lowercase()
            }

        } catch (e: Exception) {
            Log.e("RecipeRepository", "Error trying filtering recipes", e)
            emptyList()
        }
    }


    /**
     * ADMIN ONLY
     * To add a new recipe to AsianOriginals
     * @param recipe the recipe to be upload and saved on the db
     */
    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun addAsianOriginalRecipe(recipe: Recipe, language: String= Locale.getDefault().language){
        try {
            FirebaseConnection.uploadRecipeAndTranslations(recipe,language)
        }catch (e: Exception){
            Log.e("RecipeRepository","Error trying to save a new recipe to the db",e)
        }
    }
}