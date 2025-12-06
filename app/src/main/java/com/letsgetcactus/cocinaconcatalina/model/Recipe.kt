package com.letsgetcactus.cocinaconcatalina.model

import com.letsgetcactus.cocinaconcatalina.model.enum.DificultyEnum

data class Recipe(
    val id: String,
    var title: String,
    val avgRating: Double = 0.0,
    val totalRating: Int = 0,
    val ratingCount: Int = 0,
    val steps: List<String>,
    val ingredientList: List<Ingredient>,
    val allergenList: List<Allergen>,
    val categoryList: List<Category>,
    val prepTime: Int,
    val dificulty: DificultyEnum?,
    val origin: Origin = Origin(),
    val portions: Int,
    val active: Boolean,
    var img: String,
    val video: String?
)
