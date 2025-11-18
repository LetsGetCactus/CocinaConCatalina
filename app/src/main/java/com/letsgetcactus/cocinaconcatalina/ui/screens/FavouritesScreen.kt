package com.letsgetcactus.cocinaconcatalina.ui.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.vector.DefaultTintColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.Navigation
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.letsgetcactus.cocinaconcatalina.R
import com.letsgetcactus.cocinaconcatalina.database.UserRepository
import com.letsgetcactus.cocinaconcatalina.model.NavigationRoutes
import com.letsgetcactus.cocinaconcatalina.model.Recipe
import com.letsgetcactus.cocinaconcatalina.model.enum.DificultyEnum
import com.letsgetcactus.cocinaconcatalina.model.enum.DishTypeEnum
import com.letsgetcactus.cocinaconcatalina.model.enum.OriginEnum
import com.letsgetcactus.cocinaconcatalina.ui.components.BackStackButton
import com.letsgetcactus.cocinaconcatalina.ui.components.ButtonMain
import com.letsgetcactus.cocinaconcatalina.ui.components.filters.ChipSelector
import com.letsgetcactus.cocinaconcatalina.ui.theme.CocinaConCatalinaTheme
import com.letsgetcactus.cocinaconcatalina.viewmodel.RecipeViewModel
import com.letsgetcactus.cocinaconcatalina.viewmodel.UserViewModel

@Composable
fun FavouritesScreen(
    modifier: Modifier = Modifier,
    onNavigate: (String) -> Unit,
    userViewModel: UserViewModel,
    navController: NavController

) {

    //For chipset filters , by origin
    var selectedOrigin: OriginEnum? by remember { mutableStateOf(null) }

    val favouriteRecipes by userViewModel.favouriteRecipe.collectAsState()


    //UI
    Column(modifier = modifier.background(MaterialTheme.colorScheme.background)) {
        ChipSelector(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp)
                .horizontalScroll(rememberScrollState()),
            title = null,
            options = OriginEnum.entries.map { it.name },
            selectedOptions = selectedOrigin?.let { setOf(it.name) } ?: emptySet(),
            onSelectionChanged = { selectedNames ->
                selectedOrigin =
                    OriginEnum.entries.firstOrNull { it.name in selectedNames }
            },
            singleSelection = true
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.size(50.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                BackStackButton(
                   navController=navController
                )
                Text(
                    text = stringResource(R.string.favs),
                    style = MaterialTheme.typography.displayLarge,
                    color = MaterialTheme.colorScheme.tertiary
                )
            }
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                if (favouriteRecipes.isEmpty()) {
                    item {
                        Text(
                            text = "No tienes recetas favoritas todavía",
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                } else {
                    items(favouriteRecipes.size) { favs ->
                        FavCard(
                            recipe = favouriteRecipes[favs],
                            userViewModel = userViewModel,
                            onNavigate = onNavigate,

                        )
                    }
                }
            }
        }
    }
}

@Composable
fun FavCard(
    recipe: Recipe,
    userViewModel: UserViewModel,
   onNavigate: (String) -> Unit
) {


    Box(
        modifier = Modifier
            .fillMaxWidth()
            .widthIn(min = 400.dp, max = 800.dp)
            .heightIn(min = 160.dp, max = 200.dp)
            .shadow(8.dp, shape = MaterialTheme.shapes.small)
            .background(
                color = MaterialTheme.colorScheme.primary,
                shape = MaterialTheme.shapes.small
            )
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable {
               onNavigate(NavigationRoutes.ITEM_RECIPE_SCREEN + "?recipe=${recipe.id}")
            }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, bottom = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = recipe.img,
                contentDescription = stringResource(R.string.image_description),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .widthIn(min = 120.dp, max = 220.dp)
                    .heightIn(min = 100.dp, max = 160.dp)
                    .clip(MaterialTheme.shapes.small)
                    .weight(1f)
            )

            Spacer(modifier = Modifier.size(8.dp))

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = recipe.title,
                    style = MaterialTheme.typography.bodySmall,
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
                .clickable { userViewModel.changeFavourite(recipe) }
        )
    }
}
