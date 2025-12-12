package com.letsgetcactus.cocinaconcatalina.viewmodel

import com.letsgetcactus.cocinaconcatalina.model.Recipe
import com.letsgetcactus.cocinaconcatalina.model.User

/**
 * This class stores the user's State and his data
 * To not navigate into the app before it's data is all loaded correctly
 */
data class UserState(
    val isLoading: Boolean = true,
    val user: User? = null,
    val originalRecipesLoaded: Boolean= false,
    val favouriteRecipes: List<Recipe> = emptyList(),
    val modifiedRecipes: List<Recipe> = emptyList(),
    val error: String? = null
) {
    val isLoggedIn: Boolean get() = user != null
    val isReady: Boolean
        get() = user != null && !isLoading && originalRecipesLoaded
}