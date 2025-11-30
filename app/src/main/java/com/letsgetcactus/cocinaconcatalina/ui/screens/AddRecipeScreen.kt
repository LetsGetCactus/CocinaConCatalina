package com.letsgetcactus.cocinaconcatalina.ui.screens

import DropDownMenuSelector
import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.google.android.material.loadingindicator.LoadingIndicator
import com.letsgetcactus.cocinaconcatalina.R
import com.letsgetcactus.cocinaconcatalina.model.Allergen
import com.letsgetcactus.cocinaconcatalina.model.Category
import com.letsgetcactus.cocinaconcatalina.model.Ingredient
import com.letsgetcactus.cocinaconcatalina.model.NavigationRoutes
import com.letsgetcactus.cocinaconcatalina.model.Origin
import com.letsgetcactus.cocinaconcatalina.model.Recipe
import com.letsgetcactus.cocinaconcatalina.model.enum.AllergenEnum
import com.letsgetcactus.cocinaconcatalina.model.enum.DificultyEnum
import com.letsgetcactus.cocinaconcatalina.model.enum.UnitsTypeEnum
import com.letsgetcactus.cocinaconcatalina.ui.components.ButtonMain
import com.letsgetcactus.cocinaconcatalina.ui.components.ButtonPair
import com.letsgetcactus.cocinaconcatalina.ui.components.FAB
import com.letsgetcactus.cocinaconcatalina.ui.components.filters.AllergenIconsSelector
import com.letsgetcactus.cocinaconcatalina.ui.components.filters.ChipSelector
import com.letsgetcactus.cocinaconcatalina.ui.components.filters.SliderSelector
import com.letsgetcactus.cocinaconcatalina.viewmodel.RecipeViewModel
import com.letsgetcactus.cocinaconcatalina.viewmodel.UserViewModel
import kotlinx.coroutines.launch
import java.util.UUID

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AddRecipeScreen(
    onNavigate: (String) -> Unit,
    modifier: Modifier,
    recipeViewModel: RecipeViewModel,
    userViewModel: UserViewModel
) {

    //Check for ADMIN ONLY
    val context= LocalContext.current
    val currentUser by userViewModel.currentUser.collectAsState()
    if (currentUser?.role != "ADMIN") {
        onNavigate(NavigationRoutes.HOME_SCREEN)
        Toast.makeText(context,stringResource(R.string.admin_only),Toast.LENGTH_SHORT).show()
        return
    }

    //State vars
    var title: String by remember { mutableStateOf("") }
    var img: Uri? by remember { mutableStateOf(null) }

    var ingredientName by remember { mutableStateOf("") }
    var quantity by remember { mutableStateOf("") }
    var listIngredients by remember { mutableStateOf(listOf<Ingredient>()) }
    var unit by remember { mutableStateOf(UnitsTypeEnum.GRAM) }

    var steps by remember { mutableStateOf("") }
    var listSteps by remember { mutableStateOf(listOf<String>()) }

    var selectedAllergens by remember { mutableStateOf(AllergenEnum.entries.associateWith { false }) }
    var selectedDifficulty: DificultyEnum? by remember { mutableStateOf(null) }

    var categories by remember { mutableStateOf(listOf<Category>()) }
    var categoryIn by remember {mutableStateOf("")}

    var prepTime by remember { mutableStateOf(0f) }
    var portions by remember { mutableStateOf(0f) }
    var active by remember { mutableStateOf(true) }
    var origin by remember { mutableStateOf(Origin(country = "")) }


    val scrollState = rememberScrollState()
    val coroutineToAddRecipe= rememberCoroutineScope () //Needed to call suspend function addRecipe()

    //Launcher for img
    val launcher= rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) {
        uri -> img = uri
    }


    //UI
    Box() {
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .verticalScroll(scrollState)
                .padding(vertical = 48.dp, horizontal = 16.dp)

        ) {

            //CCC logo
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
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.background)
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
                buttonText = img.toString() ?: stringResource(R.string.img_selector),
                onNavigate = {launcher.launch("image/*") },
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
                            .background(MaterialTheme.colorScheme.background),
//                            .weight(1f),
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
                        options = UnitsTypeEnum.entries.toTypedArray(),
                        selected = unit,
                        onSelect = { unit = it },
                        placeholder = UnitsTypeEnum.GRAM.toString(),
//                        modifier = Modifier.weight(1f)
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
                    onNavigateRight = {
                        if (listIngredients.isNotEmpty()) {
                            listIngredients = listIngredients.dropLast(1)
                        }
                    },
                    onNavigateLeft = {
                        if (ingredientName.isNotBlank() && quantity.isNotBlank()) {
                            listIngredients =listIngredients +  Ingredient(
                                name = ingredientName,
                                quantity = quantity,
                                unit = unit
                            )

                            // cleans the fields
                            ingredientName = ""
                            quantity = ""
                            unit = UnitsTypeEnum.GRAM
                        }
                    }

                )
            }

            Spacer(Modifier.size(8.dp))

            Text(
                text = listIngredients.joinToString("\n") {
                    "${it.quantity} ${it.unit}. ${it.name}"
                },
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.background)

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
//                            .weight(0.75f)
                            .background(MaterialTheme.colorScheme.background),

                        )
                }

                Spacer(Modifier.size(16.dp))

                ButtonPair(
                    textLeft = stringResource(R.string.add),
                    textRight = stringResource(R.string.delete_last),
                    onNavigateRight = {
                        if(listSteps.isNotEmpty()){
                            listSteps= listSteps.dropLast(1)
                        }
                    },
                    onNavigateLeft = {
                        if (steps.isNotBlank()){
                            listSteps = listSteps + steps
                            steps = ""
                        }
                    }
                )

                Row() {
                    Text(
                        text = listSteps.mapIndexed {
                            index, step ->
                            "${index+1}. $step"
                        }.joinToString("\n"),
                        color = MaterialTheme.colorScheme.onBackground,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.background)
                    )
                }
            }

            Spacer(Modifier.size(32.dp))

            //Difficulty
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
                        value = categoryIn,
                        onValueChange = { categoryIn = it },
                        label = {
                            Text(
                                text = stringResource(R.string.insert_category),
                                style = MaterialTheme.typography.labelSmall,
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
//                            .weight(0.75f)
                            .background(MaterialTheme.colorScheme.background),

                        )
                }

                Spacer(Modifier.size(16.dp))

                ButtonPair(
                    textLeft = stringResource(R.string.add),
                    textRight = stringResource(R.string.delete_last),
                    onNavigateRight = {
                        if(categories.isNotEmpty()){
                            categories = categories.dropLast(1)
                        }
                    },
                    onNavigateLeft = {
                        if(categoryIn.isNotBlank()){
                            val newCategory = Category(
                                id= UUID.randomUUID().mostSignificantBits.toInt(),
                                name= categoryIn
                            )
                            categories = categories + newCategory
                            categoryIn= ""
                        }
                    }
                )

                Row() {
                    Text(
                        text = categories.joinToString(",") {it.name},
                        color = MaterialTheme.colorScheme.onBackground,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.background)

                    )
                }
            }

            SliderSelector(
                label = stringResource(R.string.prep_time),
                value = prepTime,
                onValueChange = { prepTime = it },
                valueRange = 5f..180f,
                steps = 175
            )

            SliderSelector(
                label = stringResource(R.string.portions),
                value = portions,
                onValueChange = { portions = it },
                valueRange = 1f..10f,
                steps = 9
            )


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
                onNavigate = {
                    val newRecipe = Recipe(
                        id = UUID.randomUUID().toString(),
                        title = title,
                        ingredientList = listIngredients,
                        steps = listSteps,
                        dificulty = selectedDifficulty,
                        allergenList = selectedAllergens.filter { it.value }
                            .map {
                                Allergen(
                                    name = it.key.name,
                                    img = it.key
                                )
                            },
                        categoryList = categories,
                        prepTime = prepTime.toInt(),
                        portions = portions.toInt(),
                        active = active,
                        origin = origin,
                        img ="",
                        avgRating = 0,
                        video = null
                    )
                    coroutineToAddRecipe.launch {
                        try{
                            recipeViewModel.addRecipe(newRecipe,img)
                            LoadingIndicator(context) //TODO

                            onNavigate(NavigationRoutes.HOME_SCREEN)

                        }catch(e: Exception) {
                            Toast.makeText(context, "${context.getString(R.string.recipe_saved_error)}: $e", Toast.LENGTH_SHORT).show()
                        }
                    }

                }
            )
        }
    }


}
