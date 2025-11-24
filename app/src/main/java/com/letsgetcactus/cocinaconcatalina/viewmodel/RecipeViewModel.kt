package com.letsgetcactus.cocinaconcatalina.viewmodel

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.letsgetcactus.cocinaconcatalina.model.Recipe
import com.letsgetcactus.cocinaconcatalina.data.FirebaseConnection
import com.letsgetcactus.cocinaconcatalina.model.enum.AllergenEnum
import com.letsgetcactus.cocinaconcatalina.model.enum.DificultyEnum
import com.letsgetcactus.cocinaconcatalina.model.enum.DishTypeEnum
import com.letsgetcactus.cocinaconcatalina.model.enum.OriginEnum
import com.letsgetcactus.cocinaconcatalina.model.filters.RecipeFiltersEngine
import com.letsgetcactus.cocinaconcatalina.model.filters.RecipeSearchFilters

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Locale


/**
This class will load the data from Firebase and export it in the UI, updating them whenever they change
 */
class RecipeViewModel(): ViewModel() {
    //All recipes
    private val _asianOgRecipes = MutableStateFlow<List<Recipe>>(emptyList())
    val asianOgRecipes: StateFlow<List<Recipe>> = _asianOgRecipes.asStateFlow()

    //Filtered recipes
    private val _filteredRecipes = MutableStateFlow<List<Recipe>>(emptyList())
    val filteredRecipes: StateFlow<List<Recipe>> = _filteredRecipes.asStateFlow()

    //For SearchBar
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    //To keep the selected recipe from the users UI (Shared viewmodel) ?????????? TODO
    private val _selectedRecipe = MutableStateFlow<Recipe?>(null)
    val selectedRecipe: StateFlow<Recipe?> = _selectedRecipe.asStateFlow() //This will read the ItemRecipeScreen

    //For filters to be applied
    private val _activeFilter = MutableStateFlow(RecipeSearchFilters())
    val activeFilter = _activeFilter.asStateFlow()


    init {
        loadAsianOgRecipes()
    }

    /**
     * Gets all the recipes from FirebaseConnection
     */
    fun loadAsianOgRecipes(language: String = Locale.getDefault().language ) {
        viewModelScope.launch {
            val result = FirebaseConnection.getAsianOriginalRecipes(language)
            _asianOgRecipes.value = result.sortedBy { it.title.lowercase() }
            filterRecipes()

            Log.i(
                "RecipeViewModel",
                "Obtained ${result.size}} recipes in ${Locale.getDefault().language}"
            )
        }
    }

    /**
     * Gets query from searchBar and applyes it to filterRecipes to obtain recipes from it
     * @param query to search for on Recipes
     */
    fun setSearchBar(query: String){
        _searchQuery.value=query
        filterRecipes()
    }

    /**
     * Method to save the users selection on ListRecipeHost
     * Shares the shared state between screens
     */
    fun selectRecipe(recipe: Recipe) {
        val recipe = _asianOgRecipes.value.find { it.id == recipe.id }
        recipe?.let {
            _selectedRecipe.value = it
            Log.i("RecipeViewModel", "Selected recipe: ${it.title}")
        }
    }

    /**
     * Gets the filter data from FilterScreen and saves them into private vals to be used on FilterRecipes()
     */
    fun setFilters(
        origin: OriginEnum?,
        dishType: DishTypeEnum?,
        difficulty: DificultyEnum?,
        prepTime: Int?,
        maxIngredients: Int?,
        rating: Int?,
        allergens: List<AllergenEnum>
    ) {
        _activeFilter.value = RecipeSearchFilters(
            origin = origin,
            dishType = dishType,
            difficulty = difficulty,
            prepTime = prepTime,
            maxIngredients = maxIngredients,
            rating = rating,
            allergens = allergens,
            query = _searchQuery.value
        )

        filterRecipes()
    }


    /**
     * Applies obtained filters on setFilters() to the search
     * (Filter engine)
     */
    private fun filterRecipes(){
        val filteredResultRecipes = RecipeFiltersEngine.applyFilters(
            recipes = _asianOgRecipes.value,
            filter = _activeFilter.value
        ).sortedBy { it.title.lowercase() }

        _filteredRecipes.value = filteredResultRecipes
    }

    /**
     * To search on the SearchBar
     * @param query to be searched
     */
    fun search(query: String) {
        _searchQuery.value = query
        _activeFilter.value = _activeFilter.value.copy(query = query)
        filterRecipes()
    }

    /**
     * Resets/clears RecipeViewModel private filter vals to null
     */
    fun resetFilters(){
       _activeFilter.value = RecipeSearchFilters()
        _searchQuery.value= ""
        filterRecipes()
    }

    /**
     * Adds a new asian original recipe to the db
     * @param recipe to be uploaded
     */
    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun addRecipe(recipe: Recipe): Boolean{
        FirebaseConnection.uploadRecipeAndTranslations(recipe, Locale.getDefault().language)
        return true
    }


}