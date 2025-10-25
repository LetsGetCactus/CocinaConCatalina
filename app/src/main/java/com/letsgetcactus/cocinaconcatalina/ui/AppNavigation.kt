package com.letsgetcactus.cocinaconcatalina.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.letsgetcactus.cocinaconcatalina.model.NavigationRoutes
import com.letsgetcactus.cocinaconcatalina.ui.screens.AddRecipescreen
import com.letsgetcactus.cocinaconcatalina.ui.screens.FavouritesScreen
import com.letsgetcactus.cocinaconcatalina.ui.screens.HomeScreen
import com.letsgetcactus.cocinaconcatalina.ui.screens.ItemRecipeScreen
import com.letsgetcactus.cocinaconcatalina.ui.screens.ListRecipeHostScreen
import com.letsgetcactus.cocinaconcatalina.ui.screens.LoginScreen
import com.letsgetcactus.cocinaconcatalina.ui.screens.ModifyRecipeScreen
import com.letsgetcactus.cocinaconcatalina.ui.screens.RegisterScreen
import com.letsgetcactus.cocinaconcatalina.ui.screens.TermsAndConditionsScreen
import com.letsgetcactus.cocinaconcatalina.ui.theme.CocinaConCatalinaTheme


/*
* AppNavigation contains NAvHost which stablishes the possible routes (NavigationRoutes) each screen could go
*/
@Composable
fun AppNavigation(
    navController: NavHostController,
    startDestination: String
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(NavigationRoutes.LOGIN_SCREEN) {
            LoginScreen(
                onNavigate = { route ->
                    navController.navigate(route)

                }
            )
        }
        composable(NavigationRoutes.REGISTER_SCREEN) {
            RegisterScreen(
                onNavigate = { route ->
                    navController.navigate(route)

                })
        }
        composable(NavigationRoutes.ADD_RECIPE_SCREEN) {
            AddRecipescreen(
                onNavigate = { route ->
                    navController.navigate(route)

                })
        }
        composable(NavigationRoutes.FAVS_SCREEN) {
            FavouritesScreen(
                onNavigate = { route ->
                    navController.navigate(route)

                })
        }
        composable(NavigationRoutes.ITEM_RECIPE_SCREEN) {
            ItemRecipeScreen(
                onNavigate = { route ->
                    navController.navigate(route)

                }
            )
        }
        composable(NavigationRoutes.LIST_RECIPES_HOST_SCREEN) {
            ListRecipeHostScreen(
                onNavigate = { route ->
                    navController.navigate(route)

                }
            )
        }
        composable(NavigationRoutes.MODIFIED_SCREEN) {
            ModifyRecipeScreen(
                onNavigate = { route ->
                    navController.navigate(route)

                }
            )
        }
        composable(NavigationRoutes.TERMS_CONDITIONS_SCREEN) {
            TermsAndConditionsScreen(
                onNavigate = { route ->
                    navController.navigate(route)
                }

            )
        }
        composable(NavigationRoutes.HOME_SCREEN) {
            HomeScreen(
                onNavigate = { route ->
                    navController.navigate(route)

                },

                )
        }

    }
}

