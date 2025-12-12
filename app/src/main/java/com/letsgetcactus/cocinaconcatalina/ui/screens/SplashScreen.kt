package com.letsgetcactus.cocinaconcatalina.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.decode.GifDecoder
import coil.request.ImageRequest
import com.letsgetcactus.cocinaconcatalina.R
import com.letsgetcactus.cocinaconcatalina.ui.NavigationRoutes
import com.letsgetcactus.cocinaconcatalina.ui.theme.White
import com.letsgetcactus.cocinaconcatalina.viewmodel.UserViewModel
import kotlinx.coroutines.delay

/**
 * First Screen to be shown while the app starts
 * It includes a jumping icon gif that will stay till the user's data and recipes are correctly loaded
 */
@Composable
fun SplashScreen(
    navController: NavController,
    userViewModel: UserViewModel

) {
    val context= LocalContext.current

    //State for the user and recipes
    val state by userViewModel.state.collectAsState()

    LaunchedEffect(state) {
        delay(3000)

        if (state.error != null || !state.isReady) {
            navController.navigate(NavigationRoutes.LOGIN_SCREEN) {
                popUpTo(NavigationRoutes.SPLASH_SCREEN) { inclusive = true }
            }
        } else if (state.isReady){
            navController.navigate(NavigationRoutes.HOME_SCREEN) {
                popUpTo(NavigationRoutes.SPLASH_SCREEN) { inclusive = true }
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize()
            .background(White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(R.raw.icon_gif)
                .build(),
            contentDescription = stringResource(R.string.ccc_icon),
            imageLoader = ImageLoader.Builder(context)
                .components {
                    add(GifDecoder.Factory())
                }
                .build()
        )
    }

}

