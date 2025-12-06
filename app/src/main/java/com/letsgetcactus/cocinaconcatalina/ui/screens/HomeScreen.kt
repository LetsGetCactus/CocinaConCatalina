package com.letsgetcactus.cocinaconcatalina.ui.screens

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.letsgetcactus.cocinaconcatalina.R
import com.letsgetcactus.cocinaconcatalina.data.searchFilters.Source
import com.letsgetcactus.cocinaconcatalina.ui.NavigationRoutes
import com.letsgetcactus.cocinaconcatalina.ui.components.ButtonRound
import com.letsgetcactus.cocinaconcatalina.viewmodel.UserViewModel

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onNavigate: (String) -> Unit,
    userViewModel: UserViewModel

) {
    val orientation = LocalConfiguration.current
    val isDarkTheme = userViewModel.theme.collectAsState().value


    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        if (orientation.orientation == Configuration.ORIENTATION_PORTRAIT) {
            Image(
                painter = painterResource(if (isDarkTheme == "light") R.drawable.banner_blanco else R.drawable.banner_gris),
                contentDescription = stringResource(R.string.image_description),
                contentScale = ContentScale.FillWidth,
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.size(64.dp))

        ButtonRound(
            buttonText = stringResource(R.string.allRecipes),
            onNavigate = { onNavigate(NavigationRoutes.LIST_RECIPES_HOST_SCREEN+"?source=${Source.ALL.name}")}
        )

        Spacer(modifier = Modifier.size(32.dp))

        ButtonRound(
            buttonText = stringResource(R.string.recipes_five_ingr),
            onNavigate = {
                onNavigate(NavigationRoutes.LIST_RECIPES_HOST_SCREEN + "?source=${Source.FILTERED.name}&filter=less_five_ingredients")

        }
        )

        Spacer(modifier = Modifier.size(32.dp))

        ButtonRound(
            buttonText = stringResource(R.string.recipes_high_rating),
            onNavigate = {
                onNavigate(NavigationRoutes.LIST_RECIPES_HOST_SCREEN+"?source=${Source.FILTERED.name}&filter=highest_ranked")

            }
        )

        Spacer(modifier = Modifier.size(32.dp))

        ButtonRound(
            buttonText = stringResource(R.string.seen_on_tv),
            onNavigate = {
                onNavigate(NavigationRoutes.LIST_RECIPES_HOST_SCREEN+"?source=${Source.FILTERED.name}&filter=seen_on_tv")
            }
        )
    }
}