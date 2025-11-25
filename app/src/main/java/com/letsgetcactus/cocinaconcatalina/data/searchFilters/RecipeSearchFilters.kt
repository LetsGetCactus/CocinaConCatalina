package com.letsgetcactus.cocinaconcatalina.data.searchFilters

import com.letsgetcactus.cocinaconcatalina.model.enum.AllergenEnum
import com.letsgetcactus.cocinaconcatalina.model.enum.DificultyEnum
import com.letsgetcactus.cocinaconcatalina.model.enum.DishTypeEnum
import com.letsgetcactus.cocinaconcatalina.model.enum.OriginEnum

/**
 * Data class to get the possible filters that will be applied on a search for a recipe or a list of them
 */
data class RecipeSearchFilters(
    val origin: OriginEnum? = null,
    val dishType: DishTypeEnum? = null,
    val difficulty: DificultyEnum? = null,
    val prepTime: Int? = null,
    val maxIngredients: Int? = null,
    val rating: Int? = null,
    val allergens: List<AllergenEnum> = emptyList(),
    val query: String = ""
)