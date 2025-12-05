package com.letsgetcactus.cocinaconcatalina.viewmodel

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.letsgetcactus.cocinaconcatalina.data.repository.RecipeRepository
import com.letsgetcactus.cocinaconcatalina.data.searchFilters.RecipeFiltersEngine
import com.letsgetcactus.cocinaconcatalina.data.searchFilters.RecipeSearchFilters
import com.letsgetcactus.cocinaconcatalina.model.Recipe
import com.letsgetcactus.cocinaconcatalina.model.enum.AllergenEnum
import com.letsgetcactus.cocinaconcatalina.model.enum.DificultyEnum
import com.letsgetcactus.cocinaconcatalina.model.enum.DishTypeEnum
import com.letsgetcactus.cocinaconcatalina.model.enum.OriginEnum
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Locale


/**
 * This class will load the data from Firebase asianOriginalRecipies
 * and export it in the UI, updating them whenever they change
 * Filters, searchs, selects and resets on asianOriginals
 */
class RecipeViewModel(
    private val recipeRepository: RecipeRepository
) : ViewModel() {
    //All recipes (og)
    private val _asianOgRecipes = MutableStateFlow<List<Recipe>>(emptyList())
    val asianOgRecipes: StateFlow<List<Recipe>> = _asianOgRecipes.asStateFlow()

    //Filtered recipes
    private val _filteredRecipes = MutableStateFlow<List<Recipe>>(emptyList())
    val filteredRecipes: StateFlow<List<Recipe>> = _filteredRecipes.asStateFlow()

    //For SearchBar
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    //To keep the selected recipe from the users UI
    private val _selectedRecipe = MutableStateFlow<Recipe?>(null)
    val selectedRecipe = _selectedRecipe.asStateFlow() //This will read the ItemRecipeScreen

    //For active filters
    private val _activeFilter = MutableStateFlow(RecipeSearchFilters())
    val activeFilter = _activeFilter.asStateFlow()


    init {
        loadAsianOgRecipes()
    }

    /**
     * Gets all the recipes from FirebaseConnection
     */
    fun loadAsianOgRecipes(language: String = Locale.getDefault().language) {
        viewModelScope.launch {
            val result = recipeRepository.getAllAsianOriginalRecipes(language)
            _asianOgRecipes.value = result.sortedBy { it.title.lowercase() }

            if (result.isNotEmpty()) filterRecipes()

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
            query = ""
        )
        _searchQuery.value = ""
        filterRecipes()
    }


    /**
     * Applies obtained filters on setFilters() to the search
     * (Filter engine)
     */
    private fun filterRecipes() {

        Log.i("RecipeViewModel", "Filtering with: ${_activeFilter.value}")
        val filteredResultRecipes = RecipeFiltersEngine.applyFilters(
            recipes = _asianOgRecipes.value,
            filter = _activeFilter.value
        ).sortedBy { it.title.lowercase() }

        _filteredRecipes.value = filteredResultRecipes
        Log.i(
            "RecipeViewModel", "Filtered result count: ${filteredResultRecipes.size}"
        )
    }

    /**
     * To search on the SearchBar
     * @param query to be searched
     */
    fun search(query: String) {
        _searchQuery.value = query

        // Each search is isolated and not adding to the others
        _activeFilter.value = RecipeSearchFilters(
            query = query,
            origin = null,
            dishType = null,
            difficulty = null,
            prepTime = null,
            maxIngredients = null,
            rating = null,
            allergens = emptyList()
        )

        filterRecipes()
    }
    /**
     * Resets/clears RecipeViewModel private filter vals to null
     */
    fun resetFilters() {
        _activeFilter.value = RecipeSearchFilters()
        _searchQuery.value = ""
        filterRecipes()
    }

    /**
     * Adds a new asian original recipe to the db
     * @param recipe to be uploaded
     */
    suspend fun addRecipe(recipe: Recipe, img: Uri?) {
        recipeRepository.addRecipeToDB(recipe, img)
        Log.i("REcipeViewModel", "Sent recipe $recipe to Repo")
    }


}