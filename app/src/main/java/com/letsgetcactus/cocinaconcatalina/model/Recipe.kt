package com.letsgetcactus.cocinaconcatalina.model

import androidx.compose.ui.text.LinkAnnotation
import com.letsgetcactus.cocinaconcatalina.model.enum.DificultyEnum

data class Recipe(
    val id: String,
    val title: String,
    var avgRating: Int,
    val steps: String,
    var ingredientList: List<Ingredient>,
    val allergenList: List<Allergen>,
    var categoryList: List<Category>,
    var prepTime: Int,
    val dificulty: DificultyEnum,
    val origin: Origin,
    var portions: Int,
    var active: Boolean = true,
    var img: LinkAnnotation.Url,
    var video: LinkAnnotation.Url
)
