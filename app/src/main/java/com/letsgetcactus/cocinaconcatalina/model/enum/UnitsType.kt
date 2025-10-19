package com.letsgetcactus.cocinaconcatalina.model.enum

import com.letsgetcactus.cocinaconcatalina.R

enum class UnitsType(val unitToDisplay: Int){
    GRAM(R.string.g),
    KILOGRAM(R.string.kg),
    MILLILITER(R.string.ml),
    LITER(R.string.l),
    UNITS(R.string.uds),
    UNIT(R.string.ud),
    LARGE_SPOON(R.string.full_spoon),
    SMALL_SPOON(R.string.small_spoon),
    A_BIT(R.string.a_bit)
}