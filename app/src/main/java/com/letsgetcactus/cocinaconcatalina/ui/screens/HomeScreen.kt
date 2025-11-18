package com.letsgetcactus.cocinaconcatalina.ui.screens

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.letsgetcactus.cocinaconcatalina.R
import com.letsgetcactus.cocinaconcatalina.model.NavigationRoutes
import com.letsgetcactus.cocinaconcatalina.ui.components.ButtonRound
import com.letsgetcactus.cocinaconcatalina.ui.theme.CocinaConCatalinaTheme
import com.letsgetcactus.cocinaconcatalina.viewmodel.RecipeViewModel
import com.letsgetcactus.cocinaconcatalina.viewmodel.UserViewModel

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onNavigate: (String) -> Unit,
    userViewModel: UserViewModel,
    recipeViewModel: RecipeViewModel
) {
    val orientation = LocalConfiguration.current
    val isLight = !isSystemInDarkTheme()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        if (orientation.orientation == Configuration.ORIENTATION_PORTRAIT) {
            Image(
                painter = painterResource(if (isLight) R.drawable.banner_blanco else R.drawable.banner_gris),
                contentDescription = stringResource(R.string.image_description),
                contentScale = ContentScale.FillWidth,
                modifier = Modifier.fillMaxWidth() // 👈 aquí usamos Modifier, no modifier
            )
        }

        Spacer(modifier = Modifier.size(64.dp)) // 👈 igual aquí

        ButtonRound(
            buttonText = stringResource(R.string.allRecipes),
            onNavigate = { onNavigate(NavigationRoutes.LIST_RECIPES_HOST_SCREEN)}
        )

        Spacer(modifier = Modifier.size(32.dp))

        ButtonRound(
            buttonText = stringResource(R.string.sweetRecipes),
            onNavigate = { onNavigate(NavigationRoutes.LIST_RECIPES_HOST_SCREEN)}
        )

        Spacer(modifier = Modifier.size(32.dp))

        ButtonRound(
            buttonText = stringResource(R.string.savouryRecipes),
            onNavigate = { onNavigate(NavigationRoutes.LIST_RECIPES_HOST_SCREEN)}
        )
    }
}