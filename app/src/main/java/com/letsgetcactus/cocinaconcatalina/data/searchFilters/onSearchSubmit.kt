package com.letsgetcactus.cocinaconcatalina.data.searchFilters

import com.letsgetcactus.cocinaconcatalina.viewmodel.RecipeViewModel
import com.letsgetcactus.cocinaconcatalina.viewmodel.UserViewModel

/**
 * Establishes the Source to the search on the SearchBar composable
 *  (what ViewModel needs to search depending of what type of Recipe is been searched)
 */
fun onSearchSubmit(
    query: String,
    source: Source,
    recipeViewModel: RecipeViewModel,
    userViewModel: UserViewModel
) {
    when (source) {
        Source.ASIAN_OG -> recipeViewModel.search(query)
        Source.MODIFIED -> userViewModel.search(query)
        Source.ALL, Source.FILTERED -> {
            recipeViewModel.search(query)
            userViewModel.search(query)
        }

    }
}