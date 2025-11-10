package com.letsgetcactus.cocinaconcatalina.model

import androidx.compose.ui.text.LinkAnnotation
import com.letsgetcactus.cocinaconcatalina.model.enum.DificultyEnum

data class Recipe(
    val id: String = "",
    val title: String = "",
    val avgRating: Int = 0,
    val steps: List<String> = emptyList(),
    val ingredientList: List<Ingredient> = emptyList(),
    val allergenList: List<Allergen> = emptyList(),
    val categoryList: List<Category> = emptyList(),
    val prepTime: Int = 0,
    val dificulty: DificultyEnum = DificultyEnum.EASY,
    val origin: String="",
    val portions: Int = 1,
    val active: Boolean = true,
    val img: String = "",
    val video: String = ""
)
