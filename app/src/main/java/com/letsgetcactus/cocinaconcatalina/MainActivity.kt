package com.letsgetcactus.cocinaconcatalina

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.letsgetcactus.cocinaconcatalina.model.NavigationRoutes
import com.letsgetcactus.cocinaconcatalina.ui.AppNavigation
import com.letsgetcactus.cocinaconcatalina.ui.theme.CocinaConCatalinaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CocinaConCatalinaTheme {
                val navController = rememberNavController()
                AppNavigation(
                    navController=navController,
                    startDestination= NavigationRoutes.LOGIN_SCREEN
                )
            }
        }
    }
}

