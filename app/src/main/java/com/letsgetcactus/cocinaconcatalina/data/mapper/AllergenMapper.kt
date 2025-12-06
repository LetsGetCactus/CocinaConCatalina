package com.letsgetcactus.cocinaconcatalina.data.mapper

import android.util.Log
import com.letsgetcactus.cocinaconcatalina.data.dto.AllergenDto
import com.letsgetcactus.cocinaconcatalina.model.Allergen
import com.letsgetcactus.cocinaconcatalina.model.enum.AllergenEnum

/**
 * Class that translates Firebases simple types (DTO) into our Kotlin types for the recipes
 *
 */
fun AllergenDto.toAllergen(language: String): Allergen {
    val mapToEnum = mapOf(
        "SHELLFISH_ICON" to AllergenEnum.MOLLUSK,
        "EGG_ICON" to AllergenEnum.EGG,
        "FISH_ICON" to AllergenEnum.FISH,
        "SOY_ICON" to AllergenEnum.SOY,
        "PEANUT_ICON" to AllergenEnum.PEANUT,
        "GLUTEN_ICON" to AllergenEnum.GLUTEN,
        "MILK_ICON" to AllergenEnum.DAIRY,
        "MUSTARD_ICON" to AllergenEnum.MUSTARD,
        "CELERY_ICON" to AllergenEnum.CELERY,
        "SESAME_ICON" to AllergenEnum.SESAME,
        "NUTS_ICON" to AllergenEnum.NUTS,
        "CRAB_ICON" to AllergenEnum.CRAB,
        "ALTRAMUZ_ICON" to AllergenEnum.ALTRAMUZ,
        "SULFITE_ICON" to AllergenEnum.SULFITE
    )


    val allergenEnum = mapToEnum[this.img] ?: AllergenEnum.ALTRAMUZ
    Log.i("AllergenMapper", "Firebase allergen: ${this.img}")

    // 👇 Nuevo manejo multilenguaje
    val lang = if (language !in listOf("es", "gl", "en")) "en" else language
    val localizedName = this.name[lang] ?: this.name["en"] ?: ""

    return Allergen(
        name = localizedName,
        img = allergenEnum
    )
}