package com.letsgetcactus.cocinaconcatalina.model

import com.letsgetcactus.cocinaconcatalina.model.enum.DificultyEnum

/**
 * Main type for the app, it contains a full Recipe to be shown to the user
 * @param img pubic url from Storage real image
 * @param video NOT IMPLEMENTED YET!
 */
data class Recipe(
    val id: String,
    var title: String,
    val avgRating: Double ,
    val totalRating: Int ,
    val ratingCount: Int ,
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
