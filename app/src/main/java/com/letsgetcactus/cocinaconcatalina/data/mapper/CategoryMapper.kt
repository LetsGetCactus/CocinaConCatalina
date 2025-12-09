package com.letsgetcactus.cocinaconcatalina.data.mapper

import com.letsgetcactus.cocinaconcatalina.data.dto.CategoryDto
import com.letsgetcactus.cocinaconcatalina.model.Category

/**
 * Gets the Firebase-type category and "translates" it into our model Category
 */
fun CategoryDto.toCategory(): Category {

    return Category(
        name = this.name
    )
}