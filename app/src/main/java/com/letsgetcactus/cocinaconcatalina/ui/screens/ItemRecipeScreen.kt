package com.letsgetcactus.cocinaconcatalina.ui.screens

import android.util.Log
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.letsgetcactus.cocinaconcatalina.R
import com.letsgetcactus.cocinaconcatalina.model.Allergen
import com.letsgetcactus.cocinaconcatalina.model.Ingredient
import com.letsgetcactus.cocinaconcatalina.model.NavigationRoutes
import com.letsgetcactus.cocinaconcatalina.model.Recipe
import com.letsgetcactus.cocinaconcatalina.ui.components.RecipeRatingSelector
import com.letsgetcactus.cocinaconcatalina.viewmodel.RecipeViewModel

@Composable
fun ItemRecipeScreen(
    modifier: Modifier = Modifier,
    onNavigate: (String) -> Unit,
    navController: NavHostController
) {


    // Shared ViewModel from ListRecipeHostScreen
    val parentEntry = remember(navController.currentBackStackEntry) {
        navController.getBackStackEntry(NavigationRoutes.LIST_RECIPES_HOST_SCREEN)
    }
    val viewModel: RecipeViewModel = viewModel(parentEntry)

    val recipe by viewModel.selectedRecipe.collectAsState()

    recipe?.let { currentRecipe ->
        Column {
            Text(text = currentRecipe.title)
        }


        Log.i("ItemRecipeScreen", "Entrando en la pantalla para mostrar ${currentRecipe}")

        Column(
            modifier = modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(24.dp, 40.dp), // padding interno propio

        ) {
            Spacer(modifier = Modifier.size(56.dp))

            LazyColumn(
                modifier = Modifier.fillMaxSize() // usamos Modifier normal, no modifier
            ) {
                item {
                    Text(
                        text = currentRecipe.title,
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
                            painter = rememberAsyncImagePainter(currentRecipe.img),
                            contentDescription = currentRecipe.title,
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
                                        .offset(x = 6.dp, y = 12.dp)
                                        .size(40.dp)
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
                                        .offset(y = 12.dp)
                                        .size(40.dp)
                                )
                            }
                        }
                        //Origin - flag
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .offset(x = 12.dp)
                        ) {
                            Image(
                                painter = rememberAsyncImagePainter(currentRecipe.origin),
                                contentDescription = currentRecipe.origin,
                                modifier = Modifier
                                    .align(Alignment.TopEnd)
                                    .size(40.dp)
                                    .shadow(16.dp)
                            )
                        }

                    }

                    //Preptime & portions
                    Row(
                        horizontalArrangement = Arrangement.Start,
                    ) {
                        Image(
                            painter = rememberAsyncImagePainter(currentRecipe.origin),
                            contentDescription = currentRecipe.origin,
                            modifier = Modifier

                                .size(24.dp)
                                .shadow(16.dp)
                        )
                        Spacer(Modifier.size(8.dp))
                        IconAndText(
                            modifier = Modifier,
                            imageResource = R.drawable.user_white,
                            imageContent = R.string.portions,
                            textIn = "3"
                        )
                        Spacer(Modifier.size(8.dp))
                        IconAndText(
                            modifier = Modifier,
                            imageResource = R.drawable.timer,
                            imageContent = R.string.prep_time,
                            textIn = "160"
                        )


                    }
                    Spacer(modifier = Modifier.size(32.dp))
                }

                item {
                    Row {
                        Text(
                            text = stringResource(R.string.ingredients),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.weight(1f)
                        )
                        LazyRow {
                            item { AlergensList(currentRecipe.allergenList) }
                        }
                    }
                }

                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    ) {
                        ItemIngredients(currentRecipe.ingredientList)
                    }
                }

                item {
                    Text(
                        text = stringResource(R.string.steps),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.weight(1f)
                    )
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    ) {

                        ItemSteps(currentRecipe.steps)

                    }
                }

                item { //TODO puntuacion seleccionable
                    Spacer(modifier = Modifier.size(24.dp))
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {

                        Text(
                            stringResource(R.string.rating_ramen),
                            color = MaterialTheme.colorScheme.primary,
                            style = MaterialTheme.typography.bodySmall
                        )
                        RecipeRatingSelector(3)
                    }
                }
            }
        }
    }
}

@Composable
fun ItemIngredients(ingredients: List<Ingredient>) {

    Column {
        ingredients.forEach { ingredient ->
            Row {
                Text(text = ingredient.quantity.toString())
                Spacer(modifier = Modifier.size(8.dp))
                Text(text = stringResource(id = ingredient.unit.enumId))
                Spacer(modifier = Modifier.size(16.dp))
                Text(text = ingredient.name)
            }
        }
    }
}

@Composable
fun ItemSteps(steps: List<String>) {
    var count = 1

    Row {
        for (step in steps) {
            Text("${count}. ")
            Text(text = step)
        }
        Spacer(modifier = Modifier.size(16.dp))
    }
}


@Composable
fun AlergensList(allergen: List<Allergen>) {
    allergen.forEach { alergen ->
        Image(
            painter = painterResource(id = alergen.img.colorDrawable),
            contentDescription = alergen.name,
            modifier = Modifier.size(24.dp)
        )
    }
}

@Composable
fun IconAndText(
    modifier: Modifier = Modifier,
    imageResource: Int,
    imageContent: Int,
    textIn: String
) {
    Row(
        modifier = modifier,
    ) {
        Image(
            painter = painterResource(id = imageResource),
            contentDescription = stringResource(id = imageContent),
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier.size(4.dp))
        Text(
            text = textIn,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
}

//
//@Composable
//@Preview
//fun PreviewItemRecipe() {
//    CocinaConCatalinaTheme(darkTheme = true) {
//        ItemRecipeScreen(
//            onNavigate = {},
//            modifier = Modifier,
//            recipe =
//        )
//    }
//}