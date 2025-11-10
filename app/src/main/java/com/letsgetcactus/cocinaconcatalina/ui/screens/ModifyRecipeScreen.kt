package com.letsgetcactus.cocinaconcatalina.ui.screens

import DropDownMenuSelector
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.letsgetcactus.cocinaconcatalina.R
import com.letsgetcactus.cocinaconcatalina.model.Ingredient
import com.letsgetcactus.cocinaconcatalina.model.NavigationRoutes
import com.letsgetcactus.cocinaconcatalina.model.enum.AllergenEnum
import com.letsgetcactus.cocinaconcatalina.model.enum.UnitsTypeEnum
import com.letsgetcactus.cocinaconcatalina.ui.components.FAB
import com.letsgetcactus.cocinaconcatalina.ui.components.filters.AllergenIconsSelector
import com.letsgetcactus.cocinaconcatalina.ui.theme.CocinaConCatalinaTheme
import com.letsgetcactus.cocinaconcatalina.viewmodel.RecipeViewModel

@Composable
fun ModifyRecipeScreen(
    onNavigate: (String) -> Unit,
    navController: NavHostController,
    onIngredientChange: (Ingredient) -> Unit = {},

    ) {
    // Get the shared ViewModel from ListRecipeHostScreen
    val parentEntry = remember(navController.currentBackStackEntry) {
        navController.getBackStackEntry(NavigationRoutes.LIST_RECIPES_HOST_SCREEN)
    }
    val viewModel: RecipeViewModel = viewModel(parentEntry)
    val recipe by viewModel.selectedRecipe.collectAsState()
    Log.i("ModifyRecipeScreen", "Dentro de la Screen")

    val scrollState = rememberScrollState()


    recipe?.let {

            currentRecipe ->
        Log.i("ModifyRecipeScreen", "Dentro de la Screen con ${currentRecipe.title}")

        //To activate recipe's allergens
        var selectedAllergens by remember(currentRecipe) {
            mutableStateOf(
                AllergenEnum.entries.associateWith {
                    allergen ->
                    currentRecipe.allergenList.any { it.img == allergen }
                }
            )
        }
        Log.i("ModifyRecipeScreen", "Allergens in recipe: ${currentRecipe.allergenList.map { it.img }}")



        Box {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
                    .verticalScroll(scrollState)
                    .padding(32.dp)
            ) {
                Text(
                    text = "Modificar ${currentRecipe.title}",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.primary
                )


                // Image
                val img = rememberAsyncImagePainter(
                    model = currentRecipe.img,
                    placeholder = painterResource(R.drawable.recipe), // drawable by defaul while updating
                    error = painterResource(R.drawable.recipe) // in case there's an error
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 200.dp)
                ) {
                    Image(
                        painter = img,
                        contentDescription = currentRecipe.title,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                Spacer(modifier = Modifier.size(24.dp))

                // Alergens . Horizontal scroll
                Text(
                    text = stringResource(R.string.allergens),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.primary
                )

                AllergenIconsSelector(
                    selectedAllergens = selectedAllergens,
                    onSelectionChanged = { selectedAllergens = it },
                    modifier = Modifier.fillMaxWidth()
                )



                Spacer(modifier = Modifier.size(24.dp))

                // Ingredients
                Text(
                    text = stringResource(R.string.ingredients),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.primary
                )
                ModifyItemIngredients(
                    ingredientList = currentRecipe.ingredientList,
                    onIngredientChange = { ingredient ->
                        //Upadte viewmdoel
                        viewModel.selectRecipe(
                            currentRecipe.copy(
                                ingredientList = currentRecipe.ingredientList.map {
                                    if (it.name == ingredient.name) ingredient else it
                                }
                            )
                        )
                    })

                Spacer(Modifier.size(24.dp))

                //Steps
                Text(
                    text = stringResource(R.string.steps),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.primary
                )


                Column() {
                    var count = 1
                    currentRecipe.steps.forEach { step ->
                        Row(

                        ) {
                            Text(text = "${count}.")
                            Spacer(modifier = Modifier.size(8.dp))
                            Text(
                                text = step,
                                color = MaterialTheme.colorScheme.onBackground,
                                style = MaterialTheme.typography.bodyMedium

                            )
                            count++
                        }
                        Spacer(modifier = Modifier.size(16.dp))
                    }
                }
            }
        }
        //FAB
        Column(
            Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 48.dp),
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.Bottom
        ) {
            FAB(
                onNavigate = { onNavigate(NavigationRoutes.ITEM_RECIPE_SCREEN) }
            )
        }
    }
}


@Composable
fun ModifyItemIngredients(
    ingredientList: List<Ingredient>,
    onIngredientChange: (Ingredient) -> Unit
) {

    var unit by remember { mutableStateOf(UnitsTypeEnum.GRAM) }


    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        ingredientList.forEach { ingredient ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth(),

                ) {
                //Edit quantity
                TextField(
                    value = ingredient.quantity,
                    onValueChange = { newQty: String ->
                        onIngredientChange(ingredient.copy(quantity = newQty))
                    },

                    modifier = Modifier.weight(.75f),
                    shape = MaterialTheme.shapes.small,
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = MaterialTheme.colorScheme.surface,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                        focusedTextColor = MaterialTheme.colorScheme.onSurface,
                        unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                    ),
                    textStyle = MaterialTheme.typography.bodySmall

                )

                Spacer(Modifier.width(8.dp))

                // Choose unit from selector
                DropDownMenuSelector(
                    options = UnitsTypeEnum.entries.toTypedArray(),
                    selected = unit,
                    onSelect = { unit = it },
                    placeholder = UnitsTypeEnum.GRAM.toString(),
                    modifier = Modifier.weight(1f)
                )

                Spacer(Modifier.width(8.dp))

                // Ingredient name (read only)
                Text(
                    text = ingredient.name,
                    modifier = Modifier.weight(1.5f),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }

}


//@Preview(showBackground = true)
//@Composable
//fun PreviewModifyRecipeScreen() {
//    CocinaConCatalinaTheme(darkTheme = false) {
//        ModifyRecipeScreen(onNavigate = {})
//    }
//}
