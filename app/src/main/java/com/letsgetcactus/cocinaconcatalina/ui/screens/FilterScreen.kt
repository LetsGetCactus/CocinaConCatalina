package com.letsgetcactus.cocinaconcatalina.ui.screens

import DropDownMenuSelector
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.letsgetcactus.cocinaconcatalina.R
import com.letsgetcactus.cocinaconcatalina.model.enum.AllergenEnum
import com.letsgetcactus.cocinaconcatalina.model.enum.DificultyEnum
import com.letsgetcactus.cocinaconcatalina.model.enum.DishTypeEnum
import com.letsgetcactus.cocinaconcatalina.model.enum.OriginEnum
import com.letsgetcactus.cocinaconcatalina.ui.components.ButtonMain
import com.letsgetcactus.cocinaconcatalina.ui.components.ButtonSecondary
import com.letsgetcactus.cocinaconcatalina.ui.components.filters.AllergenIconsSelector
import com.letsgetcactus.cocinaconcatalina.ui.components.filters.ChipSelector
import com.letsgetcactus.cocinaconcatalina.ui.components.filters.SliderSelector
import com.letsgetcactus.cocinaconcatalina.ui.theme.CocinaConCatalinaTheme
import com.letsgetcactus.cocinaconcatalina.viewmodel.RecipeViewModel

@Composable
fun FilterScreen(
    onSearchClick: () -> Unit,
    recipeViewModel: RecipeViewModel
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
                options = OriginEnum.values(),
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
                options = DificultyEnum.values().map { it.name },
                selectedOptions = selectedDifficulty?.let { setOf(it.name) } ?: emptySet(),
                onSelectionChanged = { selectedNames ->
                    selectedDifficulty =
                        DificultyEnum.values().firstOrNull { it.name in selectedNames }
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
                        recipeViewModel.applyFilters(
                            origin = selectedOrigin,
                            dishType = selectedDishType,
                            difficulty = selectedDifficulty,
                            prepTime = prepTime.toInt(),
                            maxIngredients = maxIngredients.toInt(),
                            rating = rating.toInt(),
                            allergens = selectedAllergens.filter { it.value }.keys.toList()
                        )
                        onSearchClick()
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
                        selectedAllergens = AllergenEnum.values().associateWith { false }
                    },
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(48.dp))
        }
    }
}
