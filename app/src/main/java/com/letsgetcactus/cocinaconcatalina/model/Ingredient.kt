package com.letsgetcactus.cocinaconcatalina.model

import com.letsgetcactus.cocinaconcatalina.model.enum.UnitsType

data class Ingredient(
    val name: String,
    val quantity: String,
    val unit: UnitsType
)