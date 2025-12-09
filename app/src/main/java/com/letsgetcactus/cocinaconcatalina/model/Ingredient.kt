package com.letsgetcactus.cocinaconcatalina.model

import com.letsgetcactus.cocinaconcatalina.model.enum.UnitsTypeEnum

/**
 * Each Ingredient on the Recipe's list
 * What a recipe is made of
 * @param name of the Ingredient
 * @param quantity how much of it is needed
 * @param unit measure of the quantity
 */
data class Ingredient(
    val name: String,
    val quantity: String,
    val unit: UnitsTypeEnum,
)