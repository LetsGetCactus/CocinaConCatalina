package com.letsgetcactus.cocinaconcatalina.database.dto


import com.letsgetcactus.cocinaconcatalina.model.Origin

data class RecipeDto(
    val id: String = "",
    val title: Map<String,String> = emptyMap(),
    val avgRating: Int = 0,
    val steps: List<Map<String,String>> = emptyList(),
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