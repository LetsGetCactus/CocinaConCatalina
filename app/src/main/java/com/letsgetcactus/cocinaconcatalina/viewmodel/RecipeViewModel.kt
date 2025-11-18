package com.letsgetcactus.cocinaconcatalina.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.letsgetcactus.cocinaconcatalina.model.Recipe
import com.letsgetcactus.cocinaconcatalina.model.database.FirebaseConnection
import com.letsgetcactus.cocinaconcatalina.model.enum.AllergenEnum
import com.letsgetcactus.cocinaconcatalina.model.enum.DificultyEnum
import com.letsgetcactus.cocinaconcatalina.model.enum.DishTypeEnum
import com.letsgetcactus.cocinaconcatalina.model.enum.OriginEnum

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.Locale


/**
This class will load the data from Firebase and export it in the UI, updating them whenever they change
 */
class RecipeViewModel(): ViewModel() {
    //All recipes
    private val _recipes = MutableStateFlow<List<Recipe>>(emptyList())
    val recipes: StateFlow<List<Recipe>> = _recipes

    //Filtered recipes
    private val _filteredRecipes = MutableStateFlow<List<Recipe>>(emptyList())
    val filteredRecipes: StateFlow<List<Recipe>> = _filteredRecipes

    //To keep the selected recipe from the users UI (Shared viewmodel)
    private val _selectedRecipe = MutableStateFlow<Recipe?>(null)
    val selectedRecipe: StateFlow<Recipe?> = _selectedRecipe //This will read the ItemRecipeScreen

    init {
        fetchRecipes()

    }

    /**
     * Gets all the recipes from FirebaseConnection
     */
    fun fetchRecipes() {
        viewModelScope.launch {
            val result = FirebaseConnection.getAsianOriginalRecipes()
            _recipes.value = result

            Log.i(
                "RecipeViewModel",
                "Obtained ${result.size}} recipes in ${Locale.getDefault().language}"
            )
        }
    }

    /**
     * Method to save the users selection on ListRecipeHost
     * Shares the shared state between screens
     */
    fun selectRecipe(recipe: Recipe) {
        val recipe = _recipes.value.find { it.id == recipe.id }
        recipe?.let {
            _selectedRecipe.value = it
            Log.i("RecipeViewModel", "Selected recipe: ${it.title}")
        }
    }

    /**
     * Applies filters to search for Recipes
     */
    fun applyFilters(
        origin: OriginEnum?,
        dishType: DishTypeEnum?,
        difficulty: DificultyEnum?,
        prepTime: Int?,
        maxIngredients: Int?,
        rating: Int?,
        allergens: List<AllergenEnum>
    ) {
        _filteredRecipes.value = _recipes.value.filter {
            recipe ->
            (origin == null || recipe.origin.country == origin.name) &&
                    (dishType == null || recipe.categoryList.equals(dishType)) &&
                    (difficulty == null || recipe.dificulty == difficulty) &&
                    (prepTime == null || recipe.prepTime <= prepTime) &&
                    (maxIngredients == null || recipe.ingredientList.size <= maxIngredients) &&
                    (rating == null || recipe.avgRating>= rating) &&
                    allergens.all  {
                        selectedAllergen ->
                        recipe.allergenList.none {
                            recipeAllergen ->
                            recipeAllergen.name == selectedAllergen.name
                        }
                    }
        }
    }


    /**
     * To upload a new recipe (admin only)
     */
    fun addRecipe(recipe: Recipe){

    }

}