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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.letsgetcactus.cocinaconcatalina.R
import com.letsgetcactus.cocinaconcatalina.model.Allergen
import com.letsgetcactus.cocinaconcatalina.model.Ingredient
import com.letsgetcactus.cocinaconcatalina.model.NavigationRoutes
import com.letsgetcactus.cocinaconcatalina.model.enum.AllergenEnum
import com.letsgetcactus.cocinaconcatalina.model.enum.UnitsTypeEnum
import com.letsgetcactus.cocinaconcatalina.ui.components.BackStackButton
import com.letsgetcactus.cocinaconcatalina.ui.components.FAB
import com.letsgetcactus.cocinaconcatalina.ui.components.filters.AllergenIconsSelector
import com.letsgetcactus.cocinaconcatalina.ui.components.filters.SliderSelector
import com.letsgetcactus.cocinaconcatalina.viewmodel.RecipeViewModel
import com.letsgetcactus.cocinaconcatalina.viewmodel.UserViewModel
import kotlinx.coroutines.launch

@Composable
fun ModifyRecipeScreen(
    onNavigate: (String) -> Unit,
    navController: NavHostController,
    userViewModel: UserViewModel,
    recipeViewModel: RecipeViewModel
) {

    val currentRecipe = userViewModel.selectedRecipe.collectAsState().value
        ?: recipeViewModel.selectedRecipe.collectAsState().value


    val scope = rememberCoroutineScope()

    currentRecipe?.let { recipe ->

        var selectedAllergens by remember { mutableStateOf(emptyMap<AllergenEnum, Boolean>()) }

        // Inicializamos selectedAllergens al cargar la receta
        LaunchedEffect(currentRecipe?.id) {
            currentRecipe?.let { recipe ->
                selectedAllergens = AllergenEnum.entries.associateWith { allergen ->
                    recipe.allergenList.any { it.img == allergen }
                }
            }
        }


        Box(modifier = Modifier.padding(vertical = 48.dp)) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
                    .padding(32.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                // Title
                item {
                    Row {
                        BackStackButton(navController = navController)
                        Text(
                            text = recipe.title,
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }

                // Image
                item {
                    val img = rememberAsyncImagePainter(
                        model = recipe.img,
                        placeholder = painterResource(R.drawable.recipe),
                        error = painterResource(R.drawable.recipe)
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(min = 200.dp)
                    ) {
                        Image(
                            painter = img,
                            contentDescription = recipe.title,
                            contentScale = ContentScale.Fit,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }

                // Allergens
                item {
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
                }

                // Ingredients
                item {
                    Text(
                        text = stringResource(R.string.ingredients),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.primary
                    )

                    ModifyItemIngredients(
                        ingredientList = recipe.ingredientList,
                        onIngredientListChange = { newList ->
                            val updatedRecipe = recipe.copy(ingredientList = newList)
                            userViewModel.selectRecipe(updatedRecipe)
                        }
                    )
                }

                // Steps
                item {
                    Text(
                        text = stringResource(R.string.steps),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.primary
                    )

                    ModifySteps(
                        steps = recipe.steps,
                        onStepsChange = { newSteps ->
                            val updatedRecipe = recipe.copy(steps = newSteps)
                            userViewModel.selectRecipe(updatedRecipe)
                        }
                    )
                }

                // Sliders: Portions & Prep Time
                item {
                    SliderSelector(
                        label = stringResource(R.string.portions),
                        value = recipe.portions.toFloat(),
                        valueRange = 1f..20f,
                        onValueChange = { newValue ->
                            userViewModel.selectRecipe(recipe.copy(portions = newValue.toInt()))
                        }
                    )

                    SliderSelector(
                        label = stringResource(R.string.prep_time),
                        value = recipe.prepTime.toFloat(),
                        valueRange = 1f..120f,
                        onValueChange = { newValue ->
                            userViewModel.selectRecipe(recipe.copy(prepTime = newValue.toInt()))
                        }
                    )
                }
            }

            // FAB
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp, vertical = 48.dp),
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.Bottom
            ) {
                FAB(
                    onNavigate = {
                        scope.launch {
                            val allergenList = selectedAllergens.filter { it.value }.map { Allergen(name=it.key.name, img = it.key) }
                            val newModRecipe = recipe.copy(
                                allergenList = allergenList,
                                avgRating = 0 //As a new recipe it should not save the current recipe's rating
                            )

                            Log.i("ModifyRecipeScreen","$newModRecipe")
                            userViewModel.saveModifiedRecipe(newModRecipe)
                            onNavigate(NavigationRoutes.ITEM_RECIPE_SCREEN) //Mostrar la receta modificada?
                        }
                    }
                )
            }
        }
    }
}


@Composable
fun ModifyItemIngredients(
    ingredientList: List<Ingredient>,
    onIngredientListChange: (List<Ingredient>) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        ingredientList.forEachIndexed { index, ingredient ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {

                //Quantity
                TextField(
                    value = ingredient.quantity,
                    onValueChange = { newQty ->
                        val updated = ingredient.copy(quantity = newQty)
                        val newList = ingredientList.toMutableList().also { it[index] = updated }
                        onIngredientListChange(newList)
                    },
                    modifier = Modifier.width(60.dp),
                    singleLine = true,
                    textStyle = MaterialTheme.typography.labelSmall
                )

                Spacer(Modifier.width(8.dp))

                // Units
                DropDownMenuSelector(
                    options = UnitsTypeEnum.entries.toTypedArray(),
                    selected = ingredient.unit,
                    onSelect = { newUnit ->
                        val updated = ingredient.copy(unit = newUnit)
                        val newList = ingredientList.toMutableList().also { it[index] = updated }
                        onIngredientListChange(newList)
                    },
                    placeholder = ingredient.unit.toString(),
                    modifier = Modifier.width(110.dp)
                )

                Spacer(Modifier.width(8.dp))

                // Name
                TextField(
                    value = ingredient.name,
                    onValueChange = { newName ->
                        val updated = ingredient.copy(name = newName)
                        val newList = ingredientList.toMutableList().also { it[index] = updated }
                        onIngredientListChange(newList)
                    },
                    modifier = Modifier.width(150.dp),
                    singleLine = true,
                    textStyle = MaterialTheme.typography.labelSmall
                )

                Spacer(Modifier.width(8.dp))

                // If 0 at quantity
                if (ingredient.quantity.trim() == "0" || ingredient.quantity.isEmpty()){
                    Button(
                        onClick = {
                            val newList = ingredientList.toMutableList().also { it.removeAt(index) }
                            onIngredientListChange(newList)
                        },
                        shape = MaterialTheme.shapes.large,
                        modifier = Modifier.background(MaterialTheme.colorScheme.primary)
                            .width(50.dp),

                    ) {
                        Text(
                            text = stringResource(R.string.deleteX),
                            color = MaterialTheme.colorScheme.onPrimary,
                            style = MaterialTheme.typography.labelSmall,
                            )
                    }
                }
            }
        }
    }
}

@Composable
fun ModifySteps(
    steps: List<String>,
    onStepsChange: (List<String>) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        steps.forEachIndexed { index, step ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    "${index + 1}.",
                    modifier = Modifier.padding(end = 8.dp),
                    style = MaterialTheme.typography.bodySmall
                )

                TextField(
                    value = step,
                    onValueChange = { newText ->
                        val newList = steps.toMutableList().also { it[index] = newText }
                        onStepsChange(newList)
                    },
                    modifier = Modifier.weight(1f),
                    textStyle = MaterialTheme.typography.bodySmall
                )

                Spacer(Modifier.width(8.dp))

                //For deleting a step
                Button(
                    onClick = {
                        val newList = steps.toMutableList().also { it.removeAt(index) }
                        onStepsChange(newList)
                    },
                    modifier = Modifier.background(MaterialTheme.colorScheme.primary),
                    shape = MaterialTheme.shapes.large
                ) {
                    Text(
                        text = stringResource(R.string.deleteX),
                        color = MaterialTheme.colorScheme.onPrimary,
                        style = MaterialTheme.typography.labelSmall,
                    )
                }
            }
        }

        //For adding a step
        Button(
            onClick = { onStepsChange(steps + "") },
            modifier = Modifier
                .padding(top = 8.dp)
                .background(MaterialTheme.colorScheme.primary),
            shape = MaterialTheme.shapes.large
        ) {
            Text(
                text = stringResource(R.string.new_step),
                color = MaterialTheme.colorScheme.onPrimary,
                style = MaterialTheme.typography.labelSmall,

                )
        }
    }
}
