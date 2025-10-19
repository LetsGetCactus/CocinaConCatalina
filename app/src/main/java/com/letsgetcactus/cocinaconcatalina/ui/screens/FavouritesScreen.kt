package com.letsgetcactus.cocinaconcatalina.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import com.letsgetcactus.cocinaconcatalina.R
import com.letsgetcactus.cocinaconcatalina.ui.theme.CocinaConCatalinaTheme

@Composable
fun FavouritesScreen() {


    Column(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(24.dp)
    ) {
        Spacer(Modifier.size(96.dp))

        Text(
            text = stringResource(R.string.favs),
            modifier = Modifier.padding(horizontal = 32.dp),
            style = MaterialTheme.typography.displayLarge,
            color = MaterialTheme.colorScheme.tertiary
        )
        LazyColumn {
            item {
                FavCard()
            }
        }

    }

}

@Composable
fun FavCard() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .widthIn(min = 400.dp, max = 800.dp)
            .heightIn(min= 160.dp, 200.dp)
            .shadow(8.dp,
                shape = MaterialTheme.shapes.small,
                )
            .background(
                color = MaterialTheme.colorScheme.primary,
                shape = MaterialTheme.shapes.small
            )
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, bottom = 8.dp),
            verticalAlignment = Alignment.CenterVertically,

        ) {
            Image(
                painter = painterResource(R.drawable.recipe),
                contentDescription = stringResource(R.string.image_description),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .widthIn(min = 120.dp, max = 220.dp)
                    .heightIn(min = 100.dp, max = 160.dp)
                    .padding(vertical = 8.dp)
                    .clip(      shape = MaterialTheme.shapes.small,)
                    .weight(1f)

            )

            Spacer(Modifier.size(8.dp))

            Column(
                modifier= Modifier.weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(R.string.insert_recipe_name),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onPrimary
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = stringResource(R.string.insert_description),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }

        Image(
            painter = painterResource(R.drawable.broken_heart),
            contentDescription = stringResource(R.string.image_description),
            modifier = Modifier
                .align(Alignment.TopEnd)
                .size(32.dp)
        )
    }
}


@Composable
@Preview
fun PreviewFavourites() {
    CocinaConCatalinaTheme(darkTheme = false) {
        FavouritesScreen()
    }
}