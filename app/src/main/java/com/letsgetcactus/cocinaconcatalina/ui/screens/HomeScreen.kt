package com.letsgetcactus.cocinaconcatalina.ui.screens

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.letsgetcactus.cocinaconcatalina.R
import com.letsgetcactus.cocinaconcatalina.model.NavigationRoutes

@Composable
fun HomeScreen(
    onNavigate: (String) -> Unit
) {

    val orientation = LocalConfiguration.current
    val isLight = !isSystemInDarkTheme()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        if (orientation.orientation == Configuration.ORIENTATION_PORTRAIT) {
            Image(
                painter = painterResource(if (isLight) R.drawable.banner_blanco else R.drawable.banner_gris),
                contentDescription = stringResource(R.string.image_description),
                contentScale = ContentScale.FillWidth,
                modifier = Modifier.fillMaxWidth()

            )
        }

        Spacer(modifier = Modifier.size(64.dp))

        Button(
            onClick = {onNavigate(NavigationRoutes.LIST_RECIPES_HOST_SCREEN)},
            modifier = Modifier.fillMaxWidth()
                .padding(horizontal = 64.dp),
            shape = MaterialTheme.shapes.large,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            )
            ) {
            Text(
                text = stringResource(R.string.allRecipes),
                style = MaterialTheme.typography.bodyLarge
            )
        }
        Spacer(modifier = Modifier.size(32.dp))

        Button(
            onClick = {onNavigate(NavigationRoutes.LIST_RECIPES_HOST_SCREEN)},
            modifier = Modifier.fillMaxWidth()
                .padding(horizontal = 64.dp),
            shape = MaterialTheme.shapes.large,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            )
        ) {
            Text(
                text = stringResource(R.string.sweetRecipes),
                style = MaterialTheme.typography.bodyLarge
            )
        }
        Spacer(modifier = Modifier.size(32.dp))

        Button(
            onClick = {onNavigate(NavigationRoutes.LIST_RECIPES_HOST_SCREEN)},
            modifier = Modifier.fillMaxWidth()
                .padding(horizontal = 64.dp),
            shape = MaterialTheme.shapes.large,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            )
        ) {
            Text(
                text = stringResource(R.string.savouryRecipes),
                style = MaterialTheme.typography.bodyLarge
            )
        }

    }
}

/*
@Composable
@Preview
fun PreviewHomeScreen() {
    CocinaConCatalinaTheme(darkTheme = false) {
        HomeScreen()
    }
}*/