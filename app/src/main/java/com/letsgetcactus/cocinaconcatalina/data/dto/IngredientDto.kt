package com.letsgetcactus.cocinaconcatalina.data.dto

/**
 * Validates the data from Firebase to then, if needed , use it on the mapper for "translation" to the app type data
 */
data class IngredientDto(
    val name: String="",
    val quantity: String= "",
    val unit: String = ""
)