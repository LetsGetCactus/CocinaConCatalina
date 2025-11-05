package com.letsgetcactus.cocinaconcatalina.model

import com.letsgetcactus.cocinaconcatalina.model.enum.AllergenEnum

data class Allergen(
    val name: String,
    val img: AllergenEnum,
)
