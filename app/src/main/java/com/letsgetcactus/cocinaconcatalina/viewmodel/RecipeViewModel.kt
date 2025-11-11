package com.letsgetcactus.cocinaconcatalina.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.letsgetcactus.cocinaconcatalina.model.Recipe
import com.letsgetcactus.cocinaconcatalina.model.database.FirebaseConnection
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
This class will load the data from Firebase and export it in the UI, updating them whenever they change
 */
class RecipeViewModel(): ViewModel() {
    private val _recipes = MutableStateFlow<List<Recipe>>(emptyList())
    val recipes: StateFlow<List<Recipe>> = _recipes

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

            Log.i("RecipeViewModel", "Obtained ${result.size} recipes")
        }
    }

    /**
     * Method to save the users selection on ListRecipeHost
     * Shares the shared state between screens
     */
    fun selectRecipe(recipe: Recipe) {
        _selectedRecipe.value = recipe
        Log.i("RecipeViewModel", "Selected recipe: ${recipe.title}")
    }
}