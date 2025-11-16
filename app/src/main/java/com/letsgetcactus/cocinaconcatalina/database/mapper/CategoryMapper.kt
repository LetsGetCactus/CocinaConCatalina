package com.letsgetcactus.cocinaconcatalina.database.mapper

import com.letsgetcactus.cocinaconcatalina.model.Category
import com.letsgetcactus.cocinaconcatalina.database.dto.CategoryDto

fun CategoryDto.toCategory(): Category {

    return Category(
        id = id.toInt(),
        name = name
    )
}