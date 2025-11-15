package com.letsgetcactus.cocinaconcatalina.model.database.mapper

import com.letsgetcactus.cocinaconcatalina.model.Category
import com.letsgetcactus.cocinaconcatalina.model.database.dto.CategoryDto
fun CategoryDto.toCategory(language: String = "en"): Category {
    val lang = if (language !in listOf("es", "gl", "en")) "en" else language

    return Category(
        id = this.id.toInt(),
        name = this.name[lang] ?: this.name["en"] ?: ""
    )
}