package com.letsgetcactus.cocinaconcatalina.model.enum

import androidx.compose.ui.graphics.Color

enum class DificultyEnum(
    val label: String,
    val color: Color
) {
    EASY (
        "easy",
        Color.Green),
    MEDIUM (
        "medium",
        Color.Yellow),
    HARD (
        "hard",
        Color.Red),
    CHEF (
        "chef",
        Color.Black)
}