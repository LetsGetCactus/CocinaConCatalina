package com.letsgetcactus.cocinaconcatalina.ui

import MenuDrawerComponent
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.letsgetcactus.cocinaconcatalina.model.NavigationRoutes
import com.letsgetcactus.cocinaconcatalina.ui.components.bars.BottomBarComposable
import com.letsgetcactus.cocinaconcatalina.ui.screens.AddRecipescreen
import com.letsgetcactus.cocinaconcatalina.ui.screens.FavouritesScreen
import com.letsgetcactus.cocinaconcatalina.ui.screens.HomeScreen
import com.letsgetcactus.cocinaconcatalina.ui.screens.ItemRecipeScreen
import com.letsgetcactus.cocinaconcatalina.ui.screens.ListRecipeHostScreen
import com.letsgetcactus.cocinaconcatalina.ui.screens.LoginScreen
import com.letsgetcactus.cocinaconcatalina.ui.screens.ModifyRecipeScreen
import com.letsgetcactus.cocinaconcatalina.ui.screens.RegisterScreen
import com.letsgetcactus.cocinaconcatalina.ui.screens.TermsAndConditionsScreen
import com.letsgetcactus.cocinaconcatalina.ui.screens.TopBarComposable
import com.letsgetcactus.cocinaconcatalina.ui.theme.CocinaConCatalinaTheme
import kotlinx.coroutines.launch


/*
* AppNavigation contains NAvHost which stablishes the possible routes (NavigationRoutes) each screen could go
* also integrates the TopAppBar and BottomAppBar in the screens that need them
*/
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
            MenuDrawerComponent(
                modifier = Modifier.width(LocalConfiguration.current.screenWidthDp.dp * 0.7f),
                onNavigate = { route ->
                    navController.navigate(route)
                    coroutineScope.launch {
                        drawerState.close()
                    }
                }
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
                    currentRoute != NavigationRoutes.MODIFIED_SCREEN
                ) {
                    TopBarComposable(
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
                    currentRoute != NavigationRoutes.MODIFIED_SCREEN
                ) {
                    BottomBarComposable { }
                }
            }
        ) { innerPadding ->

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
                        modifier = Modifier.padding(innerPadding),
                        onNavigate = { route ->
                            navController.navigate(route)

                        })
                }
                composable(NavigationRoutes.ITEM_RECIPE_SCREEN) {
                    ItemRecipeScreen(
                        modifier = Modifier.padding(innerPadding),
                        onNavigate = { route ->
                            navController.navigate(route)

                        }
                    )
                }
                composable(NavigationRoutes.LIST_RECIPES_HOST_SCREEN) {
                    ListRecipeHostScreen(
                        modifier = Modifier.padding(innerPadding),
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
                        modifier = Modifier.padding(innerPadding),
                        onNavigate = { route ->
                            navController.navigate(route)

                        },

                        )
                }

            }
        }
    }
}

@Preview
@Composable
fun PreviewMainScreen() {
    CocinaConCatalinaTheme(darkTheme = false) {
        AppNavigation(
            navController = rememberNavController(),
            startDestination = NavigationRoutes.LOGIN_SCREEN
        )
    }
}

