package com.letsgetcactus.cocinaconcatalina.model

import com.letsgetcactus.cocinaconcatalina.model.enum.AllergenEnum

/**
 * Model type for allergens
 * @param name of the allergen itself
 * @param img round icon to show for the allergen on ItemRecipeScreen
 */
data class Allergen(
    val name: String,
    val img: AllergenEnum,
)
