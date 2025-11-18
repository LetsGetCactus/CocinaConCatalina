package com.letsgetcactus.cocinaconcatalina.ui

import MenuDrawerComponent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.letsgetcactus.cocinaconcatalina.model.NavigationRoutes
import com.letsgetcactus.cocinaconcatalina.ui.components.bars.BottomBarComposable
import com.letsgetcactus.cocinaconcatalina.ui.screens.AddRecipeScreen
import com.letsgetcactus.cocinaconcatalina.ui.screens.FavouritesScreen
import com.letsgetcactus.cocinaconcatalina.ui.screens.FilterScreen
import com.letsgetcactus.cocinaconcatalina.ui.screens.HomeScreen
import com.letsgetcactus.cocinaconcatalina.ui.screens.ItemRecipeScreen
import com.letsgetcactus.cocinaconcatalina.ui.screens.ListRecipeHostScreen
import com.letsgetcactus.cocinaconcatalina.ui.screens.LoginScreen
import com.letsgetcactus.cocinaconcatalina.ui.screens.ModifyRecipeScreen
import com.letsgetcactus.cocinaconcatalina.ui.screens.RegisterScreen
import com.letsgetcactus.cocinaconcatalina.ui.screens.SplashScreen
import com.letsgetcactus.cocinaconcatalina.ui.screens.TermsAndConditionsScreen
import com.letsgetcactus.cocinaconcatalina.ui.screens.TopBarComposable
import com.letsgetcactus.cocinaconcatalina.viewmodel.RecipeViewModel
import com.letsgetcactus.cocinaconcatalina.viewmodel.UserViewModel
import kotlinx.coroutines.launch


/*
* AppNavigation contains NAvHost which stablishes the possible routes (NavigationRoutes) each screen could go
* also integrates the TopAppBar and BottomAppBar in the screens that need them
*/
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavigation(
    navController: NavHostController,
    startDestination: String
) {

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()


    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            val userViewModel: UserViewModel = viewModel()
            MenuDrawerComponent(
                navController = navController,
                userViewModel = userViewModel,
                modifier = Modifier.width(LocalConfiguration.current.screenWidthDp.dp * 0.7f),
            )

        }
    ) {
        Scaffold(
            topBar = {
                //Not to be shown on these routes
                if (currentRoute != NavigationRoutes.LOGIN_SCREEN &&
                    currentRoute != NavigationRoutes.REGISTER_SCREEN &&
                    currentRoute != NavigationRoutes.TERMS_CONDITIONS_SCREEN &&
                    currentRoute != NavigationRoutes.ADD_RECIPE_SCREEN &&
                    currentRoute != NavigationRoutes.MODIFIED_SCREEN &&
                    currentRoute != NavigationRoutes.FILTER_SCREEN &&
                    currentRoute != NavigationRoutes.SPLASH_SCREEN
                ) {
                    TopBarComposable(
                        navController = navController,
                        onMenu = {
                            coroutineScope.launch {
                                drawerState.open()
                            }
                        },
                        onSearchChanged = {}
                    )
                }
            },

            bottomBar = {
                if (currentRoute != NavigationRoutes.TERMS_CONDITIONS_SCREEN &&
                    currentRoute != NavigationRoutes.LOGIN_SCREEN &&
                    currentRoute != NavigationRoutes.REGISTER_SCREEN &&
                    currentRoute != NavigationRoutes.MODIFIED_SCREEN &&
                    currentRoute != NavigationRoutes.SPLASH_SCREEN
                ) {
                    BottomBarComposable { route ->
                        if (route != currentRoute) {
                            navController.navigate(route) {
                                launchSingleTop = true//Not to recharge the current screen
                                restoreState = true
                            }
                        }
                    }

                }
            }
        ) { innerPadding ->

            val recipeViewModel: RecipeViewModel=viewModel()
            val userViewModel: UserViewModel=viewModel()

            NavHost(
                navController = navController,
                startDestination = NavigationRoutes.SPLASH_SCREEN,

                ) {
                composable(NavigationRoutes.SPLASH_SCREEN) {
                    SplashScreen(navController, userViewModel)
                }
                composable(NavigationRoutes.LOGIN_SCREEN) {
                    LoginScreen(
                        userViewModel = userViewModel,
                        navController = navController
                    )
                }
                composable(NavigationRoutes.REGISTER_SCREEN) {
                    RegisterScreen(
                        navController = navController,
                        userViewModel=userViewModel
                    )
                }
                composable(NavigationRoutes.ADD_RECIPE_SCREEN) {
                    AddRecipeScreen(
                        modifier = Modifier.padding(innerPadding),
                        onNavigate = { route ->
                            navController.navigate(route)

                        },
                        userViewModel= userViewModel,
                        recipeViewModel=recipeViewModel
                    )

                }
                composable(NavigationRoutes.FAVS_SCREEN) {
                    FavouritesScreen(
                        modifier = Modifier.padding(innerPadding),
                        userViewModel = userViewModel,
                        onNavigate = {
                            route ->
                            navController.navigate(route)
                        },
                        navController = navController
                    )
                }
                composable(NavigationRoutes.ITEM_RECIPE_SCREEN) {
                    ItemRecipeScreen(
                        modifier = Modifier.padding(innerPadding),
                        onNavigate = { route -> navController.navigate(route) },
                        navController = navController,
                        userViewModel = userViewModel,
                        recipeViewModel= recipeViewModel,
                    )
                }
                composable(NavigationRoutes.LIST_RECIPES_HOST_SCREEN) {
                    ListRecipeHostScreen(
                        modifier = Modifier.padding(innerPadding),
                        onNavigate = { navController.navigate(NavigationRoutes.ITEM_RECIPE_SCREEN) },
                        userViewModel = userViewModel,
                        recipeViewModel= recipeViewModel,
                    )
                }
                composable(NavigationRoutes.MODIFIED_SCREEN) {
                    ModifyRecipeScreen(
                        userViewModel = userViewModel,
                        recipeViewModel= recipeViewModel,
                        onNavigate = { route -> navController.navigate(route) },
                        navController = navController,
                        onIngredientChange = {}
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
                        modifier = Modifier.padding(innerPadding),
                        onNavigate = { route ->
                            navController.navigate(route)

                        },
                        userViewModel = userViewModel,
                        recipeViewModel= recipeViewModel,
                        )
                }

                composable(NavigationRoutes.FILTER_SCREEN) {
                    FilterScreen(
                        onSearchClick = { },
                        recipeViewModel
                    )
                }

            }
        }
    }
}

