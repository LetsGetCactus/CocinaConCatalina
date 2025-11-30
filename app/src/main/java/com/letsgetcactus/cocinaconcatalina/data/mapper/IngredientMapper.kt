package com.letsgetcactus.cocinaconcatalina.data.mapper

import com.letsgetcactus.cocinaconcatalina.model.Ingredient
import com.letsgetcactus.cocinaconcatalina.data.dto.IngredientDto
import com.letsgetcactus.cocinaconcatalina.model.enum.UnitsTypeEnum
import java.util.Locale

/**
 * Uses the DTO to map the Ingredient data from Firebase to the apps type,
 * In this case, translates
 */
fun IngredientDto.toIngredient(language: String= Locale.getDefault().language): Ingredient {

    val lang = if (language in listOf("es", "gl", "en")) language else "en"

    return Ingredient(
        name = this.name[lang] ?: this.name["en"] ?: "",
        quantity = this.quantity,
        unit = UnitsTypeEnum.entries.find { it.name == this.unit } ?: UnitsTypeEnum.UNITS
    )
}