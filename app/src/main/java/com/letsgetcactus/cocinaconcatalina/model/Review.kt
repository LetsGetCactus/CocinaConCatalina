package com.letsgetcactus.cocinaconcatalina.model

import java.util.Date

data class Review(
    val user_id: Int,
    val recipe_id: Int,
    val id: Int,
    var punctuation: Int,
    var dateOfReview: Date
)
