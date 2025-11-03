package com.letsgetcactus.cocinaconcatalina.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.letsgetcactus.cocinaconcatalina.R
import com.letsgetcactus.cocinaconcatalina.ui.theme.CocinaConCatalinaTheme

@Composable
fun RecipeRating(avgRating: Int) {
    Row(
        horizontalArrangement = Arrangement.End
    ) {
        for (i in 1..avgRating) {
            Image(
                painter = painterResource(R.drawable.icon),
                contentDescription = avgRating.toString(),
                modifier = Modifier
                    .size(40.dp)
                    .padding(horizontal = 4.dp)
            )
        }

    }
}
//TODO: composable para seleccionar el Reciperating


@Preview
@Composable
fun PreviewRating() {
    CocinaConCatalinaTheme(darkTheme = false) {
        RecipeRating(5)
    }
}