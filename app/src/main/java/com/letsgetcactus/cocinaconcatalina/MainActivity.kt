package com.letsgetcactus.cocinaconcatalina

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.google.firebase.FirebaseApp
import com.google.firebase.appcheck.FirebaseAppCheck
import com.google.firebase.appcheck.debug.DebugAppCheckProviderFactory
import com.google.firebase.appcheck.playintegrity.PlayIntegrityAppCheckProviderFactory
import com.letsgetcactus.cocinaconcatalina.data.local.DataStoreManagment
import com.letsgetcactus.cocinaconcatalina.data.repository.UserSessionRepository
import com.letsgetcactus.cocinaconcatalina.model.NavigationRoutes
import com.letsgetcactus.cocinaconcatalina.ui.AppNavigation
import com.letsgetcactus.cocinaconcatalina.ui.theme.CocinaConCatalinaTheme
import com.letsgetcactus.cocinaconcatalina.viewmodel.RecipeViewModel
import com.letsgetcactus.cocinaconcatalina.viewmodel.RecipeViewModelFactory
import com.letsgetcactus.cocinaconcatalina.viewmodel.UserViewModel
import com.letsgetcactus.cocinaconcatalina.viewmodel.UserViewModelFactory

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Init Firebase
        FirebaseApp.initializeApp(this)
        //App Check Provider for Firebase
        val firebaseAppCheck = FirebaseAppCheck.getInstance()
        firebaseAppCheck.installAppCheckProviderFactory(
          //PARA PROD:  PlayIntegrityAppCheckProviderFactory.getInstance()
            DebugAppCheckProviderFactory.getInstance()
        )
        enableEdgeToEdge()

        val dataStore= DataStoreManagment(applicationContext)
        val userSessionRepo = UserSessionRepository(dataStore)

        setContent {
            CocinaConCatalinaTheme {
                val navController = rememberNavController()

                val userViewModel : UserViewModel= viewModel( factory = UserViewModelFactory(userSessionRepo))
                val recipeViewModel: RecipeViewModel=viewModel(factory = RecipeViewModelFactory())

                AppNavigation(
                    navController=navController,
                    startDestination= NavigationRoutes.SPLASH_SCREEN,
                    userViewModel= userViewModel,
                    recipeViewModel = recipeViewModel
                )

            }
        }
    }
}

