package com.letsgetcactus.cocinaconcatalina.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.letsgetcactus.cocinaconcatalina.R
import com.letsgetcactus.cocinaconcatalina.model.enum.DificultyEnum
import com.letsgetcactus.cocinaconcatalina.ui.theme.CocinaConCatalinaTheme


/**
 * Recipe's ratings visualizer by a bunch of ramen icons coloured
 */
@Composable
fun RecipeRating(
    avgRating: Int,
    difficulty: DificultyEnum
) {


    Row(
        horizontalArrangement = Arrangement.End
    ) {
        for (i in 1..5) {
            val res = if (i <= 5 - avgRating) R.drawable.icon_grey else difficulty.icon
            Image(
                painter = painterResource(res),
                contentDescription = avgRating.toString(),
                modifier = Modifier
                    .size(32.dp)
                    .padding(horizontal = 2.dp)
            )
        }
    }
}



/**
 * Recipe rating selector for the user to rate a recipe
 */
@Composable
fun RecipeRatingSelector(
    initialRating: Int,
    onRatingSelected: (Int) -> Unit = {}
) {
    var selectedRating by remember { mutableStateOf(initialRating) }

    Row(horizontalArrangement = Arrangement.Start) {
        for (i in 1..5) {
            val iconRes = if (i <= selectedRating) R.drawable.icon else R.drawable.icon_grey
            Image(
                painter = painterResource(iconRes),
                contentDescription = "Rating $i",
                modifier = Modifier
                    .size(40.dp)
                    .padding(horizontal = 4.dp)
                    .clickable {
                        selectedRating = i
                        onRatingSelected(i)
                    }
            )
        }
    }
}

@Preview
@Composable
fun PreviewRating() {
    CocinaConCatalinaTheme(darkTheme = false) {
        Column {
            RecipeRating(3, DificultyEnum.CHEF)
            RecipeRatingSelector(2)
        }
    }
}