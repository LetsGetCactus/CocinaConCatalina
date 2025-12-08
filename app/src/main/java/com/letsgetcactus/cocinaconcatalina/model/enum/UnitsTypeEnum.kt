package com.letsgetcactus.cocinaconcatalina.model.enum

import com.letsgetcactus.cocinaconcatalina.R

enum class UnitsTypeEnum(
    override val enumId: Int
) : TranslatableEnum {

    GRAM(R.string.g),
    KILOGRAM(R.string.kg),
    MILLILITER(R.string.ml),
    LITER(R.string.l),
    UNITS(R.string.uds),
    UNIT(R.string.ud),
    LARGE_SPOON(R.string.full_spoon),
    SMALL_SPOON(R.string.small_spoon),
    A_BIT(R.string.a_bit);

    companion object {
        fun fromString(value: String?): UnitsTypeEnum {
            return entries.find { it.name.equals(value, ignoreCase = true) }
                ?: GRAM
        }
    }
}
