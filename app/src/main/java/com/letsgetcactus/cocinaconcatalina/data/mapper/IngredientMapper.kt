package com.letsgetcactus.cocinaconcatalina.data.mapper

import com.letsgetcactus.cocinaconcatalina.data.dto.IngredientDto
import com.letsgetcactus.cocinaconcatalina.model.Ingredient
import com.letsgetcactus.cocinaconcatalina.model.enum.UnitsTypeEnum

/**
 * Uses the DTO to map the Ingredient data from Firebase to the apps type,
 * In this case, translates
 */
fun IngredientDto.toIngredient(): Ingredient {



    return Ingredient(
        name = this.name,
        quantity = this.quantity,
        unit = UnitsTypeEnum.entries.find { it.name == this.unit } ?: UnitsTypeEnum.UNITS
    )
}