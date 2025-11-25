package com.letsgetcactus.cocinaconcatalina.data.dto

data class CategoryDto(
    val id: Long = 0,
    val name:  Map<String, String> = emptyMap()
)