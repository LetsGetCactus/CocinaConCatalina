package com.letsgetcactus.cocinaconcatalina.database.mapper


import com.letsgetcactus.cocinaconcatalina.database.dto.RecipeDto
import com.letsgetcactus.cocinaconcatalina.model.Origin
import com.letsgetcactus.cocinaconcatalina.model.Recipe
import com.letsgetcactus.cocinaconcatalina.model.enum.DificultyEnum
import java.util.Locale

/**
 * Mappers DTO (from DB) to model (to app)
 */
fun RecipeDto.toRecipe(language: String = Locale.getDefault().language): Recipe {
    val lang = if (language in listOf("es", "gl", "en")) language else "en"

    return Recipe(
        id = this.id,
        title = this.title[lang] ?: this.title["en"] ?: "",
        avgRating = this.avgRating,
        steps = this.steps.map { stepMap ->
            stepMap[lang] ?: stepMap["en"] ?: ""
        },
        ingredientList = this.ingredientList.map { it.toIngredient(language) },
        allergenList = this.allergenList.map { it.toAllergen(language) },
        categoryList = this.categoryList.map { it.toCategory(lang) },
        prepTime = this.prepTime,
        dificulty = DificultyEnum.entries.find { it.name == this.dificulty } ?: DificultyEnum.EASY,
        origin = Origin(
            id = (origin.id as? Number)?.toInt() ?: 0,
            country = (origin.country),
            flag = (origin.flag as? Number)?.toInt() ?: 0
        ),
        portions = this.portions,
        active = this.active,
        img = this.img,
        video = this.video
    )
}


/**
 * Mapps a Recipe object to Firebase
 */
fun Recipe.toMap(): Map<String, Any?> {
    return mapOf(
        "id" to id,
        "title" to title,
        "avgRating" to avgRating,
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
        "categoryList" to categoryList.map { it },
        "prepTime" to prepTime,
        "dificulty" to (dificulty?.name ?: DificultyEnum.EASY.name),
        "origin" to mapOf(
            "id" to origin.id,
            "country" to origin.country,
            "flag" to origin.flag
        ),
        "portions" to portions,
        "active" to active,
        "img" to img,
        "video" to video
    )
}

/**
 * Translates the strings properties in a Recipe
 * @param language Language for the recipe to be shown
 */
fun Recipe.selectLanguage(language: String = "en"): String {
    val supportedLanguage = listOf("es", "en", "gl")
    return if (language in supportedLanguage) language else "en"

}
