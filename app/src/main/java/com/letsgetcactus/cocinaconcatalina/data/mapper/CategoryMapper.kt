package com.letsgetcactus.cocinaconcatalina.data.mapper

import com.letsgetcactus.cocinaconcatalina.data.dto.CategoryDto
import com.letsgetcactus.cocinaconcatalina.model.Category

fun CategoryDto.toCategory(): Category {

    return Category(
        name = this.name
    )
}