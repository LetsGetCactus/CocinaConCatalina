package com.letsgetcactus.cocinaconcatalina.database.dto

/**
Data class with simple types so Firebase can deserialize directly from the db
 */
data class AllergenDto(
    val name: String = "",
    val img: String = ""
)