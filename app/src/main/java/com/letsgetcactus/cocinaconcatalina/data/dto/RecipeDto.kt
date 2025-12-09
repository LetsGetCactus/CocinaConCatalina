package com.letsgetcactus.cocinaconcatalina.data.dto
import com.letsgetcactus.cocinaconcatalina.model.Origin

/**
 * Data class to match types on Firebase Recipes
 * Dto's let us download the same type of Recipe as we have on the DB and then map it (by mappers)
 *  to obtain our Recipe model for the app
 */
data class RecipeDto(
    val id: String = "",
    val title:String="",
    val avgRating: Double = 0.0,
    val totalRating: Int = 0,
    val ratingCount: Int = 0,
    val steps: List<String> = emptyList(),
    val ingredientList: List<IngredientDto> = emptyList(),
    val allergenList: List<AllergenDto> = emptyList(),
    val categoryList: List<CategoryDto> = emptyList(),
    val prepTime: Int = 0,
    val dificulty: String = "",
    val origin: Origin = Origin(),
    val portions: Int = 1,
    val active: Boolean = true,
    val img: String = "",
    val video: String = ""
)