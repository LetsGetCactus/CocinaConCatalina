package com.letsgetcactus.cocinaconcatalina.model

import com.letsgetcactus.cocinaconcatalina.model.enum.DificultyEnum

data class Recipe(
    val id: String,
    var title: String,
    val avgRating: Int,
    val steps: List<String>,
    val ingredientList: List<Ingredient>,
    val allergenList: List<Allergen>,
    val categoryList: List<Category>,
    val prepTime: Int,
    val dificulty: DificultyEnum?,
    val origin: Origin = Origin(),
    val portions: Int,
    val active: Boolean,
    val img: String,
    val video: String?
)
