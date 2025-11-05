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
import com.letsgetcactus.cocinaconcatalina.model.enum.DificultyEnum
import com.letsgetcactus.cocinaconcatalina.ui.theme.CocinaConCatalinaTheme

@Composable
fun RecipeRating(
    avgRating: Int,
    difficulty: DificultyEnum) {

    val iconRes = when (difficulty) {
        DificultyEnum.EASY -> R.drawable.icon_green_shadow
        DificultyEnum.MEDIUM -> R.drawable.icon_yellow_shadow
        DificultyEnum.HARD -> R.drawable.icon_red_shadow
        DificultyEnum.CHEF -> R.drawable.icon_black_shadow
    }


    Row(
        horizontalArrangement = Arrangement.End
    ) {
        for (i in 1..avgRating) {
            Image(
                painter = painterResource(iconRes),
                contentDescription = avgRating.toString(),
                modifier = Modifier
                    .size(40.dp)
                    .padding(horizontal = 2.dp)
            )
        }

    }
}
//TODO: composable ReciperatingSelector
@Composable
fun  RecipeRatingSelector( avgRating: Int) {
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

@Preview
@Composable
fun PreviewRating() {
    CocinaConCatalinaTheme(darkTheme = false) {
        RecipeRating(5, DificultyEnum.CHEF)
    }
}