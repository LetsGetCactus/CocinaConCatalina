package com.letsgetcactus.cocinaconcatalina.model.database.dto

data class IngredientDto(
    val name: Map<String, String> = emptyMap(),
    val quantity: String= "",
    val unit: String = ""
)