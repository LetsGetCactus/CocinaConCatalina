package com.letsgetcactus.cocinaconcatalina.data.mapper


import com.letsgetcactus.cocinaconcatalina.data.dto.RecipeDto
import com.letsgetcactus.cocinaconcatalina.model.Origin
import com.letsgetcactus.cocinaconcatalina.model.Recipe
import com.letsgetcactus.cocinaconcatalina.model.enum.DificultyEnum

/**
 * Mappers DTO (from DB) to model (to app)
 */
fun RecipeDto.toRecipe(): Recipe {
    return Recipe(
        id = id,
        title = title,
        avgRating = avgRating,
        totalRating = totalRating,
        ratingCount = ratingCount,
        steps = steps,
        ingredientList = ingredientList.map { it.toIngredient() },
        allergenList = allergenList.map { it.toAllergen() },
        categoryList = categoryList.map { it.toCategory() },
        prepTime = prepTime,
        dificulty = DificultyEnum.entries.find { it.name == dificulty } ?: DificultyEnum.EASY,
        origin = Origin(
            country = origin.country,
        ),
        portions = portions,
        active = active,
        img = img,
        video = video
    )
}



/**
 * Maps a Recipe object to Firebase
 */
fun Recipe.toMap(): Map<String, Any?> {
    return mapOf(
        "id" to id,
        "title" to title,
        "avgRating" to avgRating,
        "totalRating" to totalRating,
        "ratingCount" to ratingCount,
        "steps" to steps,
        "ingredientList" to ingredientList.map { ing ->
            mapOf(
                "name" to ing.name,
                "quantity" to ing.quantity,
                "unit" to ing.unit.name
            )
        },
        "allergenList" to allergenList.map { all ->
            mapOf(
                "name" to all.name,
                "img" to all.img.name
            )
        },
        "categoryList" to categoryList.map { cat ->
            mapOf("name" to cat.name
            )
        },
        "prepTime" to prepTime,
        "dificulty" to dificulty?.name,
        "origin" to mapOf(
            "country" to origin.country
        ),
        "portions" to portions,
        "active" to active,
        "img" to img,
        "video" to (video ?: "")
    )
}


