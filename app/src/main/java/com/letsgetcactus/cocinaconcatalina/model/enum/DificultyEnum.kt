package com.letsgetcactus.cocinaconcatalina.model.enum

import androidx.compose.ui.graphics.Color
import com.letsgetcactus.cocinaconcatalina.R
import com.letsgetcactus.cocinaconcatalina.ui.theme.Black
import com.letsgetcactus.cocinaconcatalina.ui.theme.Green
import com.letsgetcactus.cocinaconcatalina.ui.theme.Red
import com.letsgetcactus.cocinaconcatalina.ui.theme.Yellow

/**
 * Enum for the level of difficulty of a recipe
 * @param color palette to show the difficulty level (green -> yellow -> red -> black)
 * @param icon apps icon on each difficulty color to match
 */
enum class DificultyEnum (
  override  val enumId: Int,
    val color: Color,
    val icon: Int
): TranslatableEnum {
    EASY (
     R.string.easy,
        Green,
        R.drawable.icon_green_shadow),
    MEDIUM (
        R.string.medium,
        Yellow,
        R.drawable.icon_yellow_shadow),
    HARD (
        R.string.hard,
        Red,
        R.drawable.icon),
    CHEF (
        R.string.chef,
        Black,
        R.drawable.icon_black_shadow)
}