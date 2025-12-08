package com.letsgetcactus.cocinaconcatalina.data.mapper

import com.letsgetcactus.cocinaconcatalina.data.dto.IngredientDto
import com.letsgetcactus.cocinaconcatalina.model.Ingredient
import com.letsgetcactus.cocinaconcatalina.model.enum.UnitsTypeEnum

/**
 * Uses the DTO to map the Ingredient data from Firebase to the apps Ingredient type
 * and UnitTypeEnum
 */
fun IngredientDto.toIngredient(): Ingredient {
    return Ingredient(
        name = name,
        quantity = quantity,
        unit = UnitsTypeEnum.fromString(unit)
    )
}
