package com.letsgetcactus.cocinaconcatalina.data.dto

/**
 * Data type in Firebase Database for Ingredients
 */
data class IngredientDto(
    val name: String = "",
    val quantity: String = "",
    val unit: String = ""
)