package com.letsgetcactus.cocinaconcatalina

import android.Manifest
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.collectAsState
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.google.firebase.FirebaseApp
import com.google.firebase.appcheck.FirebaseAppCheck
import com.google.firebase.appcheck.debug.DebugAppCheckProviderFactory
import com.letsgetcactus.cocinaconcatalina.data.local.DataStoreManagment
import com.letsgetcactus.cocinaconcatalina.data.repository.UserSessionRepository
import com.letsgetcactus.cocinaconcatalina.ui.AppNavigation
import com.letsgetcactus.cocinaconcatalina.ui.NavigationRoutes
import com.letsgetcactus.cocinaconcatalina.ui.theme.CocinaConCatalinaTheme
import com.letsgetcactus.cocinaconcatalina.viewmodel.RecipeViewModel
import com.letsgetcactus.cocinaconcatalina.viewmodel.RecipeViewModelFactory
import com.letsgetcactus.cocinaconcatalina.viewmodel.UserViewModel
import com.letsgetcactus.cocinaconcatalina.viewmodel.UserViewModelFactory


/*
 * Copyright (C) 2025 Catarina Otero Sieiro
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details:
 * https://www.gnu.org/licenses/gpl-3.0.txt
 *
 *
 */

class MainActivity : ComponentActivity() {


    //Permission for POST_NOTIFIC
    private val requestNotificationPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->

        }
    //NotificationManagaer for  the whole Activity
    private lateinit var notificationManager: NotificationManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //FIREBASE
        //Init Firebase
        FirebaseApp.initializeApp(this)
        //App Check Provider for Firebase
        val firebaseAppCheck = FirebaseAppCheck.getInstance()
        firebaseAppCheck.installAppCheckProviderFactory(
          //PARA PROD:  PlayIntegrityAppCheckProviderFactory.getInstance()
            DebugAppCheckProviderFactory.getInstance()
        )

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                != PackageManager.PERMISSION_GRANTED
            ) {
                requestNotificationPermission.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
        //NOTIF.MANAGER init
        notificationManager= getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        enableEdgeToEdge()

        val dataStore= DataStoreManagment(applicationContext)
        val userSessionRepo = UserSessionRepository(dataStore)

        setContent {
            val userViewModel : UserViewModel= viewModel( factory = UserViewModelFactory(userSessionRepo))
            val recipeViewModel: RecipeViewModel=viewModel(factory = RecipeViewModelFactory())

            CocinaConCatalinaTheme(
                darkTheme =
                    when (userViewModel.theme.collectAsState().value) {
                        "light" -> false
                        "dark" -> true
                        else -> isSystemInDarkTheme() // "system"
                    }
            ) {
                val navController = rememberNavController()

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

