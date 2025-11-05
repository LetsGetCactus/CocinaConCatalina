package com.letsgetcactus.cocinaconcatalina.model

import androidx.compose.ui.text.LinkAnnotation

data class Origin(
    val id: Int,
    val country: String,
    val flag: LinkAnnotation.Url
)
