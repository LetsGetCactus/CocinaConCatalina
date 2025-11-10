package com.letsgetcactus.cocinaconcatalina.model.database.mapper

import com.letsgetcactus.cocinaconcatalina.model.Allergen
import com.letsgetcactus.cocinaconcatalina.model.database.dto.AllergenDto
import com.letsgetcactus.cocinaconcatalina.model.enum.AllergenEnum

/**
 * Class that translates Firebases simple types (DTO) into our Kotlin types for the recipes
 *
 */
fun AllergenDto.toAllergen(): Allergen {

    return Allergen(
        name = name,
        img = AllergenEnum.entries.find { it.name == this.img } ?: AllergenEnum.ALTRAMUZ

    )
}