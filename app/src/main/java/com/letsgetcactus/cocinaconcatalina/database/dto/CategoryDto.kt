package com.letsgetcactus.cocinaconcatalina.database.dto

data class CategoryDto(
    val id: Long = 0,
    val name:  Map<String, String> = emptyMap()
)