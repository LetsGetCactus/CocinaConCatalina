package com.letsgetcactus.cocinaconcatalina.model.database.mapper

import com.letsgetcactus.cocinaconcatalina.model.Ingredient
import com.letsgetcactus.cocinaconcatalina.model.database.dto.IngredientDto
import com.letsgetcactus.cocinaconcatalina.model.enum.UnitsTypeEnum

fun IngredientDto.toIngredient(): Ingredient {
    return Ingredient(
        name = this.name,
        quantity = this.quantity,
        unit = UnitsTypeEnum.values().find { it.name == this.unit } ?: UnitsTypeEnum.UNITS
    )
}