package com.letsgetcactus.cocinaconcatalina.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.letsgetcactus.cocinaconcatalina.R
import com.letsgetcactus.cocinaconcatalina.model.NavigationRoutes
import com.letsgetcactus.cocinaconcatalina.ui.theme.CocinaConCatalinaTheme

@Composable
fun ItemRecipeScreen(
    modifier: Modifier = Modifier,
    onNavigate: (String) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(24.dp, 40.dp), // padding interno propio
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.size(56.dp))

        LazyColumn(
            modifier = Modifier.fillMaxSize() // usamos Modifier normal, no modifier
        ) {
            item {
                Text(
                    text = "Recetita rica rica amiga",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            item {
                Box(
                    modifier = Modifier.fillMaxWidth() // Modifier, no modifier
                ) {
                    Image(
                        painter = painterResource(R.drawable.recipe),
                        contentDescription = stringResource(R.string.recipe_not_found),
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(min = 250.dp)
                    )

                    // Buttons
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalAlignment = Alignment.Bottom,
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .offset(x = 8.dp, y = 16.dp)
                    ) {
                        // Edit
                        Box(
                            Modifier.clickable(true) {
                                onNavigate(NavigationRoutes.MODIFIED_SCREEN)
                            }
                        ) {
                            Image(
                                painter = painterResource(R.drawable.circle),
                                contentDescription = null,
                                contentScale = ContentScale.Fit,
                                modifier = Modifier.size(48.dp)
                            )
                            Image(
                                painter = painterResource(R.drawable.edit),
                                contentDescription = stringResource(R.string.edit),
                                modifier = Modifier
                                    .align(Alignment.Center)
                                    .offset(x = 8.dp, y = 16.dp)
                                    .size(48.dp)
                            )
                        }

                        // Favs
                        Box {
                            Image(
                                painter = painterResource(R.drawable.circle),
                                contentDescription = null,
                                contentScale = ContentScale.Fit,
                                modifier = Modifier.size(48.dp)
                            )
                            Image(
                                painter = painterResource(R.drawable.set_fav),
                                contentDescription = stringResource(R.string.favs),
                                modifier = Modifier
                                    .align(Alignment.Center)
                                    .offset(y = 16.dp)
                                    .size(48.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.size(48.dp))
            }

            item {
                Row {
                    Text(
                        text = stringResource(R.string.ingredients),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.weight(1f)
                    )
                    LazyRow {
                        item { ModifyAlergensList() }
                    }
                }
            }

            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    ModifyItemIngredients()
                }
            }

            item {
                Text(
                    text = stringResource(R.string.steps),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.weight(1f)
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    ModifyItemSteps()
                }
            }
        }
    }
}


@Composable
fun ModifyItemIngredients() {
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = stringResource(R.string.quantity),
            modifier = Modifier.weight(1f),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground
        )
        Text(
            text =stringResource(R.string.unit),
            modifier = Modifier.weight(1f),
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.size(20.dp))
        Text(
            text = stringResource(R.string.ingredient),
            modifier = Modifier.weight(1f),
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

@Composable
fun ModifyItemSteps() {
    Row {
        Text(text = "pasos",
            color = MaterialTheme.colorScheme.onBackground)
    }
}

@Composable
fun ModifyAlergensList() {
    //TODO: rellenar con listado de alergenos de la receta
    Image(
        painter = painterResource(R.drawable.mustard),
        contentDescription = "mostaza",
        modifier = Modifier.size(24.dp)
    )
    Image(
        painter = painterResource(R.drawable.gluten),
        contentDescription = "mostaza",
        modifier = Modifier.size(24.dp)
    )
    Image(
        painter = painterResource(R.drawable.soja),
        contentDescription = "mostaza",
        modifier = Modifier.size(24.dp)
    )
}


@Composable
@Preview
fun PreviewItemRecipe() {
    CocinaConCatalinaTheme(darkTheme = true) {
        ItemRecipeScreen(onNavigate = {})
    }
}