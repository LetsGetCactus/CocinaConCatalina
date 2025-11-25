package com.letsgetcactus.cocinaconcatalina.ui.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.request.ImageRequest
import com.letsgetcactus.cocinaconcatalina.R
import com.letsgetcactus.cocinaconcatalina.model.NavigationRoutes
import com.letsgetcactus.cocinaconcatalina.ui.theme.White
import com.letsgetcactus.cocinaconcatalina.viewmodel.UserViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.yield


@Composable
fun SplashScreen(
    navController: NavController,
    userViewModel: UserViewModel
) {
    val isLoggedIn by userViewModel.isLoggedIn.collectAsState()


    LaunchedEffect(isLoggedIn,) {
        delay(3000)

        if (isLoggedIn) {
            Log.i("SplashScreen","Persisting user: ${userViewModel.currentUser.value}")
            navController.navigate(NavigationRoutes.HOME_SCREEN) {
                popUpTo(NavigationRoutes.SPLASH_SCREEN) { inclusive = true }
            }
        } else {
            navController.navigate(NavigationRoutes.LOGIN_SCREEN) {
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
            imageLoader = ImageLoader.Builder(LocalContext.current)
                .components {
                    add(GifDecoder.Factory())
                }
                .build()
        )
    }

}

