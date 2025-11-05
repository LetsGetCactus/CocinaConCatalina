package com.letsgetcactus.cocinaconcatalina.model.enum

import androidx.compose.ui.graphics.Color
import com.letsgetcactus.cocinaconcatalina.R

enum class DificultyEnum (
  override  val enumId: Int,
    val color: Color
): TranslatableEnum {
    EASY (
     R.string.easy,
        Color.Green),
    MEDIUM (
        R.string.medium,
        Color.Yellow),
    HARD (
        R.string.hard,
        Color.Red),
    CHEF (
        R.string.chef,
        Color.Black)
}