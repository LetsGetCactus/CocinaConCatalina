package com.letsgetcactus.cocinaconcatalina.database.dto

data class RecipeDto(
    val id: String = "",
    val title: String = "",
    val avgRating: Int = 0,
    val steps: List<String> = emptyList(),
    val ingredientList: List<IngredientDto> = emptyList(),
    val allergenList: List<AllergenDto> = emptyList(),
    val categoryList: List<CategoryDto> = emptyList(),
    val prepTime: Int = 0,
    val dificulty: String = "",
    val origin: String = "",
    val portions: Int = 1,
    val active: Boolean = true,
    val img: String = "",
    val video: String = ""
)