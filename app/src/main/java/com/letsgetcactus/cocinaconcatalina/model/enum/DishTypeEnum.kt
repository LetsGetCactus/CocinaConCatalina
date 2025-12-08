package com.letsgetcactus.cocinaconcatalina.model.enum

import com.letsgetcactus.cocinaconcatalina.R

/**
 * Enumeration for different types of dishes
 */
enum class DishTypeEnum(override val enumId: Int): TranslatableEnum{
    STARTER(R.string.starter),
    MAIN(R.string.main),
    DESSERT(R.string.dessert),
    SAUCE(R.string.sauce),
    SIDE_DISH(R.string.side_dish),
    SOUP(R.string.soup)
}