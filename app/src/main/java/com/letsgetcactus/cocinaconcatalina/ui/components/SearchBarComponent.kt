package com.letsgetcactus.cocinaconcatalina.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.letsgetcactus.cocinaconcatalina.R
import com.letsgetcactus.cocinaconcatalina.ui.NavigationRoutes
import com.letsgetcactus.cocinaconcatalina.viewmodel.RecipeViewModel
import com.letsgetcactus.cocinaconcatalina.viewmodel.UserViewModel

@Composable
fun SearchBarComponent(
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    onFilterClick: () -> Unit,
    onCloseClick: () -> Unit,
    recipeViewModel: RecipeViewModel,
    userViewModel: UserViewModel,
    navController: NavController
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextField(
            value = searchQuery,
            onValueChange = { onSearchQueryChange(it) },
            leadingIcon = {
                IconButton(onClick = {
                    // Delete previous search filters
                    recipeViewModel.resetFilters()
                    userViewModel.resetFilters()

                    recipeViewModel.search(searchQuery)
                    userViewModel.search(searchQuery)

                    navController.navigate(NavigationRoutes.LIST_RECIPES_HOST_SCREEN + "?source=FILTERED")

                    //Cleans search after searching
                    onSearchQueryChange("")
                }) {
                    Icon(Icons.Default.Search, contentDescription = stringResource(R.string.search))
                }
            },
            trailingIcon = {
                IconButton(onClick = {
                    onSearchQueryChange("")

                    recipeViewModel.search("")
                    userViewModel.search("")

                    onCloseClick()
                }) {
                    Icon(
                        Icons.Default.Close,
                        contentDescription = stringResource(R.string.close)
                    )
                }

            },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .weight(4f)
                .shadow(8.dp),
            textStyle = MaterialTheme.typography.bodySmall,
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(
                onSearch = {

                onSearchQueryChange(searchQuery)
                recipeViewModel.search(searchQuery)
                userViewModel.search(searchQuery)
                navController.navigate(NavigationRoutes.LIST_RECIPES_HOST_SCREEN+"?source=FILTERED")
            })

        )
        Spacer(modifier = Modifier.size(8.dp))

        // Botón de filtros avanzados
        IconButton(
            onClick = onFilterClick
        ) {
            Icon(
                painter = painterResource(R.drawable.filters),
                contentDescription = stringResource(R.string.close),
                modifier = Modifier.size(32.dp),
                tint = Color.Black
            )
        }

    }
}
