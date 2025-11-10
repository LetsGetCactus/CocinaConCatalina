package com.letsgetcactus.cocinaconcatalina.model

import com.letsgetcactus.cocinaconcatalina.model.enum.UnitsTypeEnum

data class Ingredient(
    val name: String="",
    val quantity: String="",
    val unit: UnitsTypeEnum= UnitsTypeEnum.UNITS,
)