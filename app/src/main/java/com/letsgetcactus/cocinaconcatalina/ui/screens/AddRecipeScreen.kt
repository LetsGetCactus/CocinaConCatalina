package com.letsgetcactus.cocinaconcatalina.ui.screens

import DropDownMenuSelector
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.letsgetcactus.cocinaconcatalina.R
import com.letsgetcactus.cocinaconcatalina.model.NavigationRoutes
import com.letsgetcactus.cocinaconcatalina.model.enum.AllergenEnum
import com.letsgetcactus.cocinaconcatalina.model.enum.DificultyEnum
import com.letsgetcactus.cocinaconcatalina.model.enum.UnitsTypeEnum
import com.letsgetcactus.cocinaconcatalina.ui.components.ButtonMain
import com.letsgetcactus.cocinaconcatalina.ui.components.ButtonPair
import com.letsgetcactus.cocinaconcatalina.ui.components.FAB
import com.letsgetcactus.cocinaconcatalina.ui.components.RecipeRatingSelector
import com.letsgetcactus.cocinaconcatalina.ui.components.filters.AllergenIconsSelector
import com.letsgetcactus.cocinaconcatalina.ui.theme.CocinaConCatalinaTheme

@Composable
fun AddRecipeScreen(
    onNavigate: (String) -> Unit,
    modifier: Modifier
) {

    var title: String by remember { mutableStateOf("") }

    // Ingredients
    var ingredientName by remember { mutableStateOf("") }
    var quantity by remember { mutableStateOf("") }
    var unit by remember { mutableStateOf(UnitsTypeEnum.GRAM) }
    var listIngredients by remember { (mutableStateOf(listOf<String>())) }

    // Steps
    var steps by remember { mutableStateOf("") }
    var listSteps by remember { (mutableStateOf(listOf<String>())) }

    // Allergens
    var selectedAllergens by remember { mutableStateOf(AllergenEnum.entries.associateWith { false }) }

    val scrollState = rememberScrollState()
    Box() {
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .verticalScroll(scrollState)
                .padding(vertical = 48.dp, horizontal = 16.dp)

        ) {

            Image(
                painter = painterResource(R.drawable.icon),
                contentDescription = stringResource(R.string.ccc_icon),
                modifier = Modifier.align(Alignment.End)
            )

            // Title
            Text(
                text = stringResource(R.string.title),
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.bodyLarge
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextField(
                    value = title,
                    onValueChange = { title = it },
                    label = {
                        Text(
                            text = stringResource(R.string.insert_recipe_name),
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    },
                    shape = MaterialTheme.shapes.small,
                    modifier = Modifier
                        .weight(0.7f)
                        .background(MaterialTheme.colorScheme.background)
                )

                Spacer(Modifier.size(8.dp))

                ButtonMain(
                    buttonText = stringResource(R.string.add),
                    onNavigate = { },
                    modifier = Modifier.fillMaxHeight()
                )
            }

            Spacer(Modifier.size(32.dp))

            // Image selector
            Text(
                text = stringResource(R.string.image),
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.bodyLarge
            )
            ButtonMain(
                buttonText = stringResource(R.string.img_selector),
                onNavigate = { },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.size(32.dp))

            // Allergens
            Text(
                text = stringResource(R.string.allergens),
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.bodyLarge
            )
            AllergenIconsSelector(
                selectedAllergens = selectedAllergens,
                onSelectionChanged = { selectedAllergens = it },
                modifier = Modifier.fillMaxWidth()
            )


            Spacer(Modifier.size(32.dp))

            // Ingredients
            Text(
                text = stringResource(R.string.ingredients),
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.bodyLarge
            )

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    ) {
                    TextField(
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.background)
                            .weight(1f),
                        value = quantity,
                        onValueChange = { quantity = it },
                        label = {
                            Text(
                                text = stringResource(R.string.insert_quantity),
                                style = MaterialTheme.typography.labelSmall,
                            )
                        },

                        )
                    Spacer(Modifier.width(8.dp))

                    DropDownMenuSelector(
                        options = UnitsTypeEnum.values(),
                        selected = unit,
                        onSelect = { unit = it },
                        placeholder = UnitsTypeEnum.GRAM.toString(),
                        modifier=Modifier.weight(1f)
                    )
                }
                Row() {
                    TextField(
                        value = ingredientName,
                        onValueChange = { ingredientName = it },
                        label = {
                            Text(
                                text = stringResource(R.string.insert_ingredient),
                                style = MaterialTheme.typography.labelSmall,
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(0.75f)
                            .background(MaterialTheme.colorScheme.background),

                        )
                }
                Spacer(Modifier.size(16.dp))

                ButtonPair(
                    textLeft = stringResource(R.string.add),
                    textRight = stringResource(R.string.delete_last),
                    onNavigateRight = {},
                    onNavigateLeft = {}
                )
            }

            Spacer(Modifier.size(8.dp))

            Text(
                text = "1Kg Ejemplo a reemplazar\n2 uds Otro ejemplo",
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.background)
                    .shadow(16.dp)
            )


            Spacer(Modifier.size(32.dp))

            // Steps
            Text(
                text = stringResource(R.string.steps),
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.bodyLarge
            )
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Row() {
                    TextField(
                        value = steps,
                        onValueChange = { steps = it },
                        label = {
                            Text(
                                text = stringResource(R.string.insert_step),
                                style = MaterialTheme.typography.labelSmall,
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(0.75f)
                            .background(MaterialTheme.colorScheme.background),

                        )
                }

                Spacer(Modifier.size(16.dp))

                ButtonPair(
                    textLeft = stringResource(R.string.add),
                    textRight = stringResource(R.string.delete_last),
                    onNavigateRight = {},
                    onNavigateLeft = {}
                )

                Row() {
                    Text(
                        text = "1. Ejemplo a reemplazar\n2. Otro ejemplo\n3. Y otro más por qué no?",
                        color = MaterialTheme.colorScheme.onBackground,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.background)
                            .shadow(16.dp)
                    )
                }
            }

            Spacer(Modifier.size(32.dp))

            //Difficulty
            Text(
                text = stringResource(R.string.level_dif),
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.bodyLarge
            )
            DropDownMenuSelector(
                options = DificultyEnum.values(),
                selected = DificultyEnum.EASY,
                onSelect = { },
                placeholder = stringResource(R.string.level_dif),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.size(32.dp))


            //Categories
            Text(
                text = stringResource(R.string.category),
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.bodyLarge
            )
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Row() {
                    TextField(
                        value = steps,
                        onValueChange = { steps = it },
                        label = {
                            Text(
                                text = stringResource(R.string.insert_category),
                                style = MaterialTheme.typography.labelSmall,
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(0.75f)
                            .background(MaterialTheme.colorScheme.background),

                        )
                }

                Spacer(Modifier.size(16.dp))

                ButtonPair(
                    textLeft = stringResource(R.string.add),
                    textRight = stringResource(R.string.delete_last),
                    onNavigateRight = {},
                    onNavigateLeft = {}
                )

                Row() {
                    Text(
                        text = "categoria, categoria, categoria",
                        color = MaterialTheme.colorScheme.onBackground,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.background)
                            .shadow(16.dp)
                    )
                }
            }


        }


        //FAB
        Column(
            modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 24.dp),
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.Bottom
        ) {
            FAB(
                onNavigate = { onNavigate(NavigationRoutes.HOME_SCREEN) }
            )
        }
    }


}


@Preview
@Composable
fun PreviewAddRecipe() {
    CocinaConCatalinaTheme(darkTheme = false) {
        AddRecipeScreen(
            onNavigate = {},
            modifier = Modifier
        )
    }
}
