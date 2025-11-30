package com.letsgetcactus.cocinaconcatalina.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.letsgetcactus.cocinaconcatalina.R
import com.letsgetcactus.cocinaconcatalina.ui.NavigationRoutes
import com.letsgetcactus.cocinaconcatalina.ui.components.SearchBarComponent


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarComposable(
    navController: NavController,
    onMenu: () -> Unit,
    onSearchChanged: (String) -> Unit,


    ) {
    var isSearchActive by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }

    TopAppBar(
        colors = topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary
        ),
        navigationIcon = {
            Icon(
                painter = painterResource(R.drawable.menu_drawer),
                contentDescription = stringResource(R.string.menu_drawer),
                modifier = Modifier
                    .padding(horizontal = 8.dp, vertical = 16.dp)
                    .size(32.dp)
                    .clickable { onMenu() }
            )
        },
        title = {
            if (!isSearchActive) {
                Text(
                    text = stringResource(R.string.app_name),
                    style = MaterialTheme.typography.titleLarge
                )
            } else {
                SearchBarComponent(
                    searchQuery = searchQuery,
                    onSearchQueryChange = { query ->
                        searchQuery = query
                        onSearchChanged(query) //This will decide which VMod to use on each screen
                    },
                    onFilterClick = { navController.navigate(NavigationRoutes.FILTER_SCREEN) },
                    onCloseClick = {
                        searchQuery = ""
                        isSearchActive = false
                        onSearchChanged("")
                    }
                )
            }
        },
        actions = {
            if (!isSearchActive) {
                Icon(
                    painter = painterResource(R.drawable.search),
                    contentDescription = stringResource(R.string.search),
                    modifier = Modifier
                        .size(40.dp)
                        .padding(end = 16.dp)
                        .clickable { isSearchActive = true },
                    tint = Color.Black
                )
            }
        }
    )
}
