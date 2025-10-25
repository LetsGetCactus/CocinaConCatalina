package com.letsgetcactus.cocinaconcatalina.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.letsgetcactus.cocinaconcatalina.R
import com.letsgetcactus.cocinaconcatalina.model.enum.Allergen
import com.letsgetcactus.cocinaconcatalina.model.Ingredient
import com.letsgetcactus.cocinaconcatalina.model.NavigationRoutes
import com.letsgetcactus.cocinaconcatalina.model.enum.UnitsType
import com.letsgetcactus.cocinaconcatalina.ui.theme.CocinaConCatalinaTheme

@Composable
fun ModifyRecipeScreen(

    onNavigate: (String) -> Unit,

    //TODO: Examples
    ingredientList: List<Ingredient> = listOf(
        Ingredient("Harina", "100", UnitsType.GRAM),
        Ingredient("Leche", "200", UnitsType.MILLILITER)
    ),
    onIngredientChange: (Ingredient) -> Unit = {},
    stepList: List<String> = listOf(
        "1. Cocer el huevo 10 minutos",
        "2. Pelar tomates y cortarlos en trozos del tamaño de un guisante",
        "3. Coger todo y hacer algo que simule que cocinas mientras pides comida a just eat"
    )
) {

    val scrollState = rememberScrollState()
    val selectedAllergens = remember { mutableStateMapOf<Allergen, Boolean>() }
    Box {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .verticalScroll(scrollState)
                .padding(32.dp)
        ) {
            Text(
                text = "Modificar receta",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.primary
            )


            // Image
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 200.dp)
            ) {
                Image(
                    painter = painterResource(R.drawable.recipe),
                    contentDescription = stringResource(R.string.recipe_not_found),
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
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                ModifyAlergensList(selectedAllergens)
            }

            Spacer(modifier = Modifier.size(24.dp))

            // Ingredients
            Text(
                text = stringResource(R.string.ingredients),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.primary
            )
            ModifyItemIngredients(
                ingredientList = ingredientList,
                onIngredientChange = onIngredientChange
            )

            Spacer(Modifier.size(24.dp))

            //Steps
            Text(
                text = stringResource(R.string.steps),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.primary
            )

            Column() {
                stepList.forEach { step ->
                    Row(

                    ) {
                        Text(
                            text = step,
                            color = MaterialTheme.colorScheme.onBackground

                        )
                    }
                }
            }
        }
        //FAB
        Column(
            Modifier.fillMaxSize()
                .padding(horizontal= 24.dp, vertical =48.dp),
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.Bottom
        ) {
            FloatingActionButton(
                onClick = { //TODO: Guardar cambios
                    onNavigate(NavigationRoutes.ITEM_RECIPE_SCREEN) },
                containerColor = MaterialTheme.colorScheme.secondary,
                contentColor = MaterialTheme.colorScheme.onSecondary,
                modifier = Modifier
                    .padding(16.dp)
                    .size(72.dp),
                shape = MaterialTheme.shapes.large,

                ) {
                Icon(
                    painter = painterResource(R.drawable.save),
                    contentDescription = stringResource(R.string.save),
                    modifier = Modifier.size(32.dp)
                )
            }
        }
    }

}

@Composable
fun ModifyItemIngredients(
    ingredientList: List<Ingredient>,
    onIngredientChange: (Ingredient) -> Unit
) {
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

                    modifier = Modifier.weight(0.25f),
                    shape = MaterialTheme.shapes.small,
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = MaterialTheme.colorScheme.surface,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                        focusedTextColor = MaterialTheme.colorScheme.onSurface,
                        unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                    ),

                    )

                Spacer(Modifier.width(8.dp))

                // Choose unit from selector
                UnitSelector(
                    selectedUnit = ingredient.unit,
                    onUnitSelected = { newUnit ->
                        onIngredientChange(ingredient.copy(unit = newUnit))
                    },
                    modifier = Modifier.weight(0.25f),

                    )

                Spacer(Modifier.width(8.dp))

                // Ingredient name (read only)
                Text(
                    text = ingredient.name,
                    modifier = Modifier.weight(0.5f)
                )
            }
        }
    }

}

@Composable
fun ModifyAlergensList(selectedAllergens: MutableMap<Allergen, Boolean>) {

    Allergen.entries.forEach { allergen ->
        val isSelected = selectedAllergens[allergen] ?: false

        Image(
            painter = painterResource(
                id = if (isSelected) allergen.colorDrawable else allergen.greyDrawable
            ),
            contentDescription = stringResource(allergen.labelRes),
            modifier = Modifier
                .size(48.dp)
                .clickable { selectedAllergens[allergen] = !isSelected }
        )
    }
}

@Composable
fun UnitSelector(
    selectedUnit: UnitsType,
    onUnitSelected: (UnitsType) -> Unit,
    modifier: Modifier = Modifier,

    ) {
    var expanded = remember { false }

    Box(modifier = modifier) {
        TextField(
            value = stringResource(selectedUnit.unitToDisplay),
            onValueChange = {},
            readOnly = true,
            modifier = Modifier.clickable { expanded = !expanded },
            shape = MaterialTheme.shapes.small,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.surface,
                unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                focusedTextColor = MaterialTheme.colorScheme.onSurface,
                unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
            ),
        )
        androidx.compose.material3.DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            UnitsType.entries.forEach { unit ->
                androidx.compose.material3.DropdownMenuItem(
                    text = { Text(stringResource(unit.unitToDisplay)) },
                    onClick = {
                        onUnitSelected(unit)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewModifyRecipeScreen() {
    CocinaConCatalinaTheme(darkTheme = false) {
        ModifyRecipeScreen(onNavigate = {})
    }
}
