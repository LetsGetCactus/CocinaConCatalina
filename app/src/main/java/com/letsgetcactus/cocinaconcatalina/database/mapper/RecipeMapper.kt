package com.letsgetcactus.cocinaconcatalina.database.mapper

import com.letsgetcactus.cocinaconcatalina.model.Recipe
import com.letsgetcactus.cocinaconcatalina.model.Category
import com.letsgetcactus.cocinaconcatalina.database.dto.RecipeDto
import com.letsgetcactus.cocinaconcatalina.model.enum.DificultyEnum

fun RecipeDto.toRecipe(): Recipe {
    return Recipe(
        id = this.id,
        title = this.title,
        avgRating = this.avgRating,
        steps = this.steps,
        ingredientList = this.ingredientList.map { it.toIngredient() },
        allergenList = this.allergenList.map { it.toAllergen() },
        categoryList = this.categoryList.map { Category(name = it.name) },
        prepTime = this.prepTime,
        dificulty = DificultyEnum.entries.find { it.name == this.dificulty } ?: DificultyEnum.EASY,
        origin = this.origin,
        portions = this.portions,
        active = this.active,
        img = this.img,
        video = this.video
    )
}