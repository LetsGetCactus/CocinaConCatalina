package com.letsgetcactus.cocinaconcatalina.model.filters

import com.letsgetcactus.cocinaconcatalina.model.Recipe

/**
 * Filter Engine to be used by both VieModels (Recipe and User)
 */
object RecipeFiltersEngine {
    /**
    * This will set the filters obtained from the FilterScreen or SearchBar
    */
    fun applyFilters(
        recipes: List<Recipe>,
        filter: RecipeSearchFilters
    ): List<Recipe> {
        return recipes.filter { recipe ->

            // Search text (searchbar)
            if (filter.query.isNotBlank() &&
                !recipe.title.lowercase().contains(filter.query.lowercase())
            ) return@filter false

            // Origin
            if (filter.origin != null && recipe.origin.country.equals(filter.origin))
                return@filter false

            // Dish Type
            if (filter.dishType != null && recipe.categoryList.equals(filter.dishType))
                return@filter false

            // Difficulty
            if (filter.difficulty != null && recipe.dificulty != filter.difficulty)
                return@filter false

            // Preparation Time
            if (filter.prepTime != null && recipe.prepTime > filter.prepTime)
                return@filter false

            // Max ingredients
            if (filter.maxIngredients != null &&
                recipe.ingredientList.size > filter.maxIngredients
            ) return@filter false

            // Rating
            if (filter.rating != null && recipe.avgRating < filter.rating)
                return@filter false

            // Allergens to exclude
            if (filter.allergens.isNotEmpty() &&
                recipe.allergenList.any { it.equals(filter.allergens) }
            ) return@filter false

            true
        }
    }

}