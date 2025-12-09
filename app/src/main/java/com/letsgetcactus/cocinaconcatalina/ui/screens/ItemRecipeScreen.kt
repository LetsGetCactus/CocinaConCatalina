package com.letsgetcactus.cocinaconcatalina.ui.screens

import android.app.Activity
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.compose.LocalActivity
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
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.letsgetcactus.cocinaconcatalina.R
import com.letsgetcactus.cocinaconcatalina.data.mapper.OriginMapper
import com.letsgetcactus.cocinaconcatalina.model.Allergen
import com.letsgetcactus.cocinaconcatalina.model.Ingredient
import com.letsgetcactus.cocinaconcatalina.ui.NavigationRoutes
import com.letsgetcactus.cocinaconcatalina.ui.components.BackStackButton
import com.letsgetcactus.cocinaconcatalina.ui.components.RecipeRatingSelector
import com.letsgetcactus.cocinaconcatalina.viewmodel.RecipeViewModel
import com.letsgetcactus.cocinaconcatalina.viewmodel.UserViewModel
import kotlinx.coroutines.launch

/**
 * Recipe's detailed Screen
 * Here the user can:
 * - Read and follow a recipe +  use cook mode to prevent the screen from suspend
 * - Add Recipe to Favourites
 * - Go to ModifiedRecipeScreen
 */
@Composable
fun ItemRecipeScreen(
    modifier: Modifier = Modifier,
    onNavigate: (String) -> Unit,
    navController: NavHostController,
    userViewModel: UserViewModel,
    recipeViewModel: RecipeViewModel,

    ) {
    //Cook Mode - Screen does not suspend while activated
    var cookMode by remember { mutableStateOf(false) }
    val activity= LocalActivity.current as Activity
    LaunchedEffect(cookMode) {
        if(cookMode) activity.window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        else activity.window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
    }


    //Combine both
    val recipeSelected = userViewModel.selectedRecipe.collectAsState().value
        ?: recipeViewModel.selectedRecipe.collectAsState().value

    recipeSelected?.let { currentRecipe ->

        //To change favs button whether a recipe is in favs or not
        val favRecepies by userViewModel.favouriteRecipe.collectAsState()
        val isRecipeFav = favRecepies.any { it.id == currentRecipe.id }


        //Flag
        val originEnum = OriginMapper.mapOriginToEnum(currentRecipe.origin.country)
        val flagForRecipe = originEnum.flag


val context= LocalContext.current
        val scope = rememberCoroutineScope()

        Box(
            modifier= Modifier
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
        Column(
            modifier = modifier
                .background(MaterialTheme.colorScheme.background)
                .widthIn(max =560.dp)
                .padding(start = 24.dp, end = 24.dp, bottom = 40.dp, top = 16.dp),

            ) {
            Row(
                modifier=Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.cook_mode),
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.labelSmall
                )
                Spacer(Modifier.size(8.dp))
                Switch(
                    checked = cookMode,
                    onCheckedChange = {
                        cookMode = it
                        Toast.makeText(
                            context, context.getString(
                                if (cookMode) R.string.cook_mode_on else R.string.cook_mode_off
                            ), Toast.LENGTH_SHORT
                        ).show()
                    },

                )
            }
            Spacer(modifier = Modifier.size(56.dp))

            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                item {
                    Row {
                        BackStackButton(
                            navController = navController
                        )
                        Text(
                            text = currentRecipe.title,
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }

                item {
                    Box(
                        modifier = Modifier.fillMaxWidth() // Modifier, no modifier
                    ) {
                        Image(
                            painter = rememberAsyncImagePainter(currentRecipe.img),
                            contentDescription = currentRecipe.title,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxWidth()
                                .heightIn(min = 250.dp, max=500.dp)
                                .widthIn(min=400.dp, max=700.dp)
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
                                    userViewModel.selectRecipe(recipeSelected)
                                    recipeViewModel.selectRecipe(recipeSelected)

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
                            Box(
                                Modifier.clickable { userViewModel.changeFavourite(currentRecipe) }
                            ) {
                                Image(
                                    painter = painterResource(R.drawable.circle),
                                    contentDescription = stringResource(R.string.favs),
                                    contentScale = ContentScale.Fit,
                                    modifier = Modifier.size(48.dp)
                                )
                                Image(
                                    painter = painterResource(
                                        if (isRecipeFav) R.drawable.lotus
                                        else R.drawable.set_fav
                                    ),
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
                                painter = painterResource(flagForRecipe),
                                contentDescription = currentRecipe.origin.country,
                                modifier = Modifier
                                    .align(Alignment.TopEnd)
                                    .size(40.dp)
                                    .offset(x = -24.dp, y = -4.dp)

                            )
                        }

                    }

                    //Preptime & portions
                    Row(
                        horizontalArrangement = Arrangement.Start,
                    ) {

                        IconAndText(
                            modifier = Modifier,
                            imageResource = R.drawable.user_red_fat,
                            imageContent = R.string.portions,
                            textIn = currentRecipe.portions.toString()
                        )
                        Spacer(Modifier.size(8.dp))
                        IconAndText(
                            modifier = Modifier,
                            imageResource = R.drawable.timer_red_fat,
                            imageContent = R.string.prep_time,
                            textIn = currentRecipe.prepTime.toString()
                        )
                        Spacer(Modifier.size(24.dp))
                        if(currentRecipe.title.endsWith("(Mod)")) {
                            IconAndText(
                                modifier = Modifier.clickable(
                                    onClick = {
                                        val user = userViewModel.currentUser.value?.id
                                        scope.launch {
                                            userViewModel.deleteModified(
                                                currentRecipe.id,
                                                user
                                            )
                                        }
                                        navController.popBackStack()
                                    }),
                                imageResource = R.drawable.trash,
                                imageContent = R.string.delete_recipe,
                                textIn = stringResource(R.string.delete_recipe)
                            )

                        }
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
                    ItemIngredients(currentRecipe.ingredientList)
                    Spacer(Modifier.size(16.dp))
                }

                //Steps
                item {
                    Text(
                        text = stringResource(R.string.steps),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.weight(1f)
                    )
                    ItemSteps(currentRecipe.steps)
                }

                //rating
                item {
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
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "(${currentRecipe.avgRating})",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.primary
                            )
                            RecipeRatingSelector(
                                initialRating = currentRecipe.avgRating.toInt(),
                                onRatingSelected = { rate ->

                                    val userId = userViewModel.currentUser.value?.id
                                    val isModified = currentRecipe.title.trim().contains("(Mod)")

                                    if (isModified && userId!= null) {

                                        userViewModel.rateRecipe(
                                            currentRecipe.id,
                                            rate,
                                            userId
                                        )

                                    } else {
                                        recipeViewModel.rateRecipe(currentRecipe.id, rate)
                                    }
                                }

                            )
                            Text(
                                text = "(${currentRecipe.ratingCount})",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }}
            }
        }
    }
}

@Composable
fun ItemIngredients(ingredients: List<Ingredient>) {

    Column {
        ingredients.forEach { ingredient ->
            Row {
                Text(
                    text = ingredient.quantity,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.size(8.dp))
                Text(
                    text = stringResource(id = ingredient.unit.enumId),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.size(16.dp))
                Text(
                    text = ingredient.name,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        }
    }
}

@Composable
fun ItemSteps(steps: List<String>) {
    var count = 1
    Column {
        for (step in steps) {
            Row {
                Text(
                    "${count}. ",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = step,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )
                count++
            }
            Spacer(modifier = Modifier.size(4.dp))
        }

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
            color = MaterialTheme.colorScheme.primary
        )
    }
}

