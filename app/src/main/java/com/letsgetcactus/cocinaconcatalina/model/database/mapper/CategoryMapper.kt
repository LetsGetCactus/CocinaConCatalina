package com.letsgetcactus.cocinaconcatalina.model.database.mapper

import com.letsgetcactus.cocinaconcatalina.model.Category
import com.letsgetcactus.cocinaconcatalina.model.database.dto.CategoryDto

fun CategoryDto.toCategory(): Category {

    return Category(
        id = id.toInt(),
        name = name
    )
}