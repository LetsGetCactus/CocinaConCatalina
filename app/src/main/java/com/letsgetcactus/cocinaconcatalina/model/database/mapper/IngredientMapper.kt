package com.letsgetcactus.cocinaconcatalina.model.database.mapper

import com.letsgetcactus.cocinaconcatalina.model.Ingredient
import com.letsgetcactus.cocinaconcatalina.model.database.dto.IngredientDto
import com.letsgetcactus.cocinaconcatalina.model.enum.UnitsTypeEnum
import java.util.Locale

fun IngredientDto.toIngredient(language: String= Locale.getDefault().language): Ingredient {

    val lang = if (language in listOf("es", "gl", "en")) language else "en"

    return Ingredient(
        name = this.name[lang] ?: this.name["en"] ?: "",
        quantity = this.quantity,
        unit = UnitsTypeEnum.entries.find { it.name == this.unit } ?: UnitsTypeEnum.UNITS
    )
}