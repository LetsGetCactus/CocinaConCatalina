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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.letsgetcactus.cocinaconcatalina.model.Recipe
import com.letsgetcactus.cocinaconcatalina.model.enum.DificultyEnum
import com.letsgetcactus.cocinaconcatalina.ui.components.ImageAndTextComponent
import com.letsgetcactus.cocinaconcatalina.ui.components.RecipeRating
import com.letsgetcactus.cocinaconcatalina.ui.theme.CocinaConCatalinaTheme
import com.letsgetcactus.cocinaconcatalina.viewmodel.RecipeViewModel

@Composable
fun ListRecipeHostScreen(
    modifier: Modifier = Modifier,
    onNavigate: () -> Unit,
    viewModel: RecipeViewModel = viewModel()
) {
    val recipesVModel by viewModel.recipes.collectAsState()
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LegendComposable()
        ListRecipeContent(
            onNavigate = { selected ->
                Log.i("ListRecipeHostScreen", "Clicked recipe: ${selected.title}")
                viewModel.selectRecipe(selected)
                onNavigate()
            },
            recipes = recipesVModel
        )
    }
}


@Composable
fun ListRecipeContent(
    modifier: Modifier = Modifier,
    onNavigate: (Recipe) -> Unit,
    recipes: List<Recipe>
) {
    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
    ) {
        items(recipes) { it ->
            RecipeCard(
                recipe = it,
                onNavigate = onNavigate

            )
        }
    }
}

@Composable
private fun RecipeCard(
    recipe: Recipe,
    onNavigate: (Recipe) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        shape = MaterialTheme.shapes.small
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .clickable {
                    Log.i("ListRecipeHostScreen", "Clicked recipe inside card: ${recipe.title}")
                    onNavigate(recipe)
                }
        ) {
            Image(
                painter = rememberAsyncImagePainter(recipe.img),
                contentDescription = recipe.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxWidth()
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomEnd)
                    .background(Color.Black.copy(alpha = 0.4f))
                    .padding(8.dp),
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.Bottom
            ) {
                Text(
                    text = recipe.title,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSecondary
                )
                RecipeRating(
                    recipe.avgRating,
                    difficulty = recipe.dificulty
                )
            }
        }
    }
}

@Composable
fun LegendComposable() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        DificultyEnum.entries.forEach { difficulty ->
            ImageAndTextComponent(
                textToShow = difficulty.enumId,
                colorText = difficulty.color,
                textStyle = MaterialTheme.typography.labelSmall,
                imgToShow = difficulty.icon,
                imgDescription = difficulty.enumId,
                iconSize = 16
            )
            Spacer(Modifier.size(16.dp))
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewListRecipeHost() {
    CocinaConCatalinaTheme(darkTheme = false) {
        ListRecipeHostScreen(
            onNavigate = {},
            modifier = Modifier
        )
    }
}
