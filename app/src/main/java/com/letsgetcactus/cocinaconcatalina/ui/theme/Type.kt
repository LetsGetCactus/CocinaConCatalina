package com.letsgetcactus.cocinaconcatalina.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.letsgetcactus.cocinaconcatalina.R

val JotiOne = FontFamily(Font(R.font.jotione_regular))
val RockSalt = FontFamily(Font(R.font.rocksalt_regular))

val Typography = Typography(
    // Main title for Login & Register
    displayLarge = TextStyle(
        //Main title
        fontFamily = JotiOne,
        fontWeight = FontWeight.Normal,
        fontSize = 32.sp,
    ),
    // Title for Favourites & Recipes title
    headlineLarge = TextStyle(
        fontFamily = JotiOne,
        fontWeight = FontWeight.Light,
        fontSize = 24.sp
    ),
    // Main text
    bodyLarge = TextStyle(
        fontFamily = JotiOne,
        fontWeight = FontWeight.Normal,
        fontSize = 24.sp
    ),
    // Rocksalt text for subtitle in recipes
    bodyMedium = TextStyle( //
        fontFamily = RockSalt,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    ),
    // text
    labelLarge = TextStyle(
        fontFamily = JotiOne,
        fontWeight = FontWeight.Normal,
        fontSize = 20.sp
    ),
    // Botón secundario
    labelMedium = TextStyle(
        fontFamily = JotiOne,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    //Mini text
    labelSmall = TextStyle(
        fontFamily = JotiOne,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    ),
    // small text
    bodySmall = TextStyle(
        fontFamily = JotiOne,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    )
)