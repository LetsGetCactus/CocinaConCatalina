package com.letsgetcactus.cocinaconcatalina.model

import java.util.Date

data class Review(
    val user_id: Int=0,
    val recipe_id: Int= 0,
    val id: Int=0,
    var punctuation: Int=0,
    var dateOfReview: Date= Date()
)
