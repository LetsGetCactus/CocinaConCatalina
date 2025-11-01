package com.letsgetcactus.cocinaconcatalina.model

import com.letsgetcactus.cocinaconcatalina.model.enum.AllergenEnum

data class Allergen(
    val idA: Int,
    val name: String,
    val img: AllergenEnum,
    val isActive: Boolean =true
)
