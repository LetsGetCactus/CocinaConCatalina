package com.letsgetcactus.cocinaconcatalina.ui.screens

import DropDownMenuSelector
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.letsgetcactus.cocinaconcatalina.R
import com.letsgetcactus.cocinaconcatalina.model.enum.AllergenEnum
import com.letsgetcactus.cocinaconcatalina.model.enum.DificultyEnum
import com.letsgetcactus.cocinaconcatalina.model.enum.DishTypeEnum
import com.letsgetcactus.cocinaconcatalina.model.enum.OriginEnum
import com.letsgetcactus.cocinaconcatalina.data.searchFilters.Source
import com.letsgetcactus.cocinaconcatalina.ui.NavigationRoutes
import com.letsgetcactus.cocinaconcatalina.ui.components.ButtonMain
import com.letsgetcactus.cocinaconcatalina.ui.components.ButtonSecondary
import com.letsgetcactus.cocinaconcatalina.ui.components.filters.AllergenIconsSelector
import com.letsgetcactus.cocinaconcatalina.ui.components.filters.ChipSelector
import com.letsgetcactus.cocinaconcatalina.ui.components.filters.SliderSelector
import com.letsgetcactus.cocinaconcatalina.viewmodel.RecipeViewModel
import com.letsgetcactus.cocinaconcatalina.viewmodel.UserViewModel

@Composable
fun FilterScreen(
    recipeSource: Source,
    navControler: NavController,
    recipeViewModel: RecipeViewModel,
    userViewModel: UserViewModel
) {
    // States
    var selectedOrigin: OriginEnum? by remember { mutableStateOf(null) }
    var selectedDishType: DishTypeEnum? by remember { mutableStateOf(null) }
    var selectedDifficulty: DificultyEnum? by remember { mutableStateOf(null) }
    var prepTime by remember { mutableStateOf(0f) }
    var maxIngredients by remember { mutableStateOf(0f) }
    var rating by remember { mutableStateOf(0f) }
    var selectedAllergens by remember { mutableStateOf(AllergenEnum.entries.associateWith { false }) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(vertical = 48.dp, horizontal = 24.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top,
        contentPadding = PaddingValues(bottom = 48.dp)
    ) {
        item {
            // Title
            Text(
                text = stringResource(R.string.advanced_filters),
                modifier = Modifier.fillMaxWidth(),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onTertiary,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Origin
            DropDownMenuSelector(
                options = OriginEnum.entries.toTypedArray(),
                selected = selectedOrigin,
                onSelect = { selectedOrigin = it },
                placeholder = stringResource(R.string.origin),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // DishType
            DropDownMenuSelector(
                options = DishTypeEnum.values(),
                selected = selectedDishType,
                onSelect = { selectedDishType = it },
                placeholder = stringResource(R.string.dish_type),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Difficulty
            ChipSelector(
                modifier = Modifier.fillMaxWidth(),
                title = stringResource(R.string.level_dif),
                options = DificultyEnum.entries.map { it.name },
                selectedOptions = selectedDifficulty?.let { setOf(it.name) } ?: emptySet(),
                onSelectionChanged = { selectedNames ->
                    selectedDifficulty =
                        DificultyEnum.entries.firstOrNull { it.name in selectedNames }
                },
                singleSelection = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Preparation time
            SliderSelector(
                label = stringResource(R.string.prep_time),
                value = prepTime,
                onValueChange = { prepTime = it },
                valueRange = 0f..120f,
                steps = 12
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Max ingredients
            SliderSelector(
                label = stringResource(R.string.max_ingredients),
                value = maxIngredients,
                onValueChange = { maxIngredients = it },
                valueRange = 0f..20f,
                steps = 19
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Rating
            SliderSelector(
                label = stringResource(R.string.rating),
                value = rating,
                onValueChange = { rating = it },
                valueRange = 0f..5f,
                steps = 5
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Allergens
            Text(
                text = stringResource(R.string.allergens_not),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onTertiary
            )
            AllergenIconsSelector(
                selectedAllergens = selectedAllergens,
                onSelectionChanged = { selectedAllergens = it },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                ButtonMain(
                    buttonText = stringResource(R.string.search),
                    onNavigate = {
                        val allergenSelected = selectedAllergens.filter { it.value }.keys.toList()
                        when (recipeSource) {
                            Source.ASIAN_OG -> {
                                recipeViewModel.setFilters(
                                    origin = selectedOrigin,
                                    dishType = selectedDishType,
                                    difficulty = selectedDifficulty,
                                    prepTime = prepTime.toInt(),
                                    maxIngredients = maxIngredients.toInt(),
                                    rating = rating.toInt(),
                                    allergens = allergenSelected
                                )
                                recipeViewModel.search(recipeViewModel.searchQuery.value)
                            }

                            Source.MODIFIED -> {
                                userViewModel.setFilters(
                                    origin = selectedOrigin,
                                    dishType = selectedDishType,
                                    difficulty = selectedDifficulty,
                                    prepTime = prepTime.toInt(),
                                    maxIngredients = maxIngredients.toInt(),
                                    rating = rating.toInt(),
                                    allergens = allergenSelected
                                )
                                userViewModel.search(userViewModel.searchQuery.value)
                            }

                            Source.ALL, Source.FILTERED -> {
                                userViewModel.setFilters(
                                    origin = selectedOrigin,
                                    dishType = selectedDishType,
                                    difficulty = selectedDifficulty,
                                    prepTime = prepTime.toInt(),
                                    maxIngredients = maxIngredients.toInt(),
                                    rating = rating.toInt(),
                                    allergens = allergenSelected
                                )
                                userViewModel.search(userViewModel.searchQuery.value)

                                recipeViewModel.setFilters(
                                    origin = selectedOrigin,
                                    dishType = selectedDishType,
                                    difficulty = selectedDifficulty,
                                    prepTime = prepTime.toInt(),
                                    maxIngredients = maxIngredients.toInt(),
                                    rating = rating.toInt(),
                                    allergens = allergenSelected
                                )
                                recipeViewModel.search(recipeViewModel.searchQuery.value)
                            }
                        }

                        navControler.navigate(NavigationRoutes.LIST_RECIPES_HOST_SCREEN+"?source=FILTERED")
                    },
                    modifier = Modifier.weight(1f)
                )
                ButtonSecondary(
                    buttonText = stringResource(R.string.clear),
                    onNavigate = {
                        selectedOrigin = null
                        selectedDishType = null
                        selectedDifficulty = null
                        prepTime = 0f
                        maxIngredients = 0f
                        rating = 0f
                        selectedAllergens = AllergenEnum.entries.associateWith { false }

                        when (recipeSource) {
                            Source.ASIAN_OG -> recipeViewModel.resetFilters()
                            Source.MODIFIED -> userViewModel.resetFilters()
                            Source.ALL, Source.FILTERED -> {
                                userViewModel.resetFilters()
                                recipeViewModel.resetFilters()
                            }
                        }
                    },
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(48.dp))
        }
    }
}
