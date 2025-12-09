import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.navigation.NavController
import com.letsgetcactus.cocinaconcatalina.R
import com.letsgetcactus.cocinaconcatalina.data.searchFilters.Source
import com.letsgetcactus.cocinaconcatalina.ui.NavigationRoutes
import com.letsgetcactus.cocinaconcatalina.ui.components.menuDrawer.DrawerItem
import com.letsgetcactus.cocinaconcatalina.ui.components.menuDrawer.DrawerSwitchItem
import com.letsgetcactus.cocinaconcatalina.ui.theme.menuDColor
import com.letsgetcactus.cocinaconcatalina.viewmodel.UserViewModel
import kotlinx.coroutines.launch

/**
 * Menu drawer: Hidden menu on the left side of the screen
 * Accessible by a 'hamburger' icon on the Top Bar
 * Shows different easy-to-go routes for different things:
 * - Navigate (Home, Favs, Modified recipes, Add a new original asian recipe)
 * - User preferences (change theme, close session, delete account and all user data)
 * - Contact ( mailto:)
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuDrawerComponent(
    navController: NavController,
    userViewModel: UserViewModel,
    modifier: Modifier = Modifier,
    drawerState: DrawerState
) {
    //To close de drawer when navigatin to other screen
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    //Dialogs to pop up when user clicks on delete user
    var deletePopUpDialog by remember { mutableStateOf(false) }


    CompositionLocalProvider(LocalContentColor provides MaterialTheme.colorScheme.onSecondary)
    {

        Column(
            modifier = modifier
                .fillMaxSize()
                .background(menuDColor)
                .padding(vertical = 48.dp, horizontal = 24.dp)
        ) {
            Text(
                text = stringResource(R.string.app_name),
                color = MaterialTheme.colorScheme.onSecondary,
                style = MaterialTheme.typography.bodyMedium
            )

            HorizontalDivider(
                modifier = Modifier.padding(top = 16.dp, bottom = 8.dp),
                color = MaterialTheme.colorScheme.onSecondary
            )

            // Drawer items
            DrawerItem(
                icon = R.drawable.tori_gate,
                label = stringResource(R.string.home),
                onClick = {
                    navController.navigate(NavigationRoutes.HOME_SCREEN)
                    scope.launch { drawerState.close() }
                }
            )

            DrawerItem(
                icon = R.drawable.favs,
                label = stringResource(R.string.favs),
                onClick = {
                    navController.navigate(NavigationRoutes.FAVS_SCREEN)
                    scope.launch { drawerState.close() }
                }
            )

            DrawerItem(
                icon = R.drawable.food,
                label = stringResource(R.string.modified),
                onClick = {
                    navController.navigate(NavigationRoutes.LIST_RECIPES_HOST_SCREEN + "?source=${Source.MODIFIED.name}&filter=")
                    scope.launch { drawerState.close() }
                }
            )
            DrawerItem(
                icon = R.drawable.add,
                label = stringResource(R.string.add_recipe),
                onClick = {
                    navController.navigate(NavigationRoutes.ADD_RECIPE_SCREEN)
                    scope.launch { drawerState.close() }
                }
            )


            HorizontalDivider(
                modifier = Modifier.padding(top = 16.dp, bottom = 8.dp),
                color = MaterialTheme.colorScheme.onSecondary
            )

            DrawerSwitchItem(
                icon = R.drawable.moon,
                label = stringResource(R.string.mode),
                checked = when (userViewModel.theme.collectAsState().value) {
                    "dark" -> true
                    else -> false
                },
                onCheckedChange = { isChecked ->
                    val newTheme = if (isChecked) "dark" else "light"
                    userViewModel.uploadUserTheme(newTheme)

                }
            )


            DrawerItem(
                icon = R.drawable.exit,
                label = stringResource(R.string.close_session),
                onClick = {
                    scope.launch {
                        userViewModel.logOut(context, navController)
                        drawerState.close()
                    }
                }

        )

        DrawerItem(
            icon = R.drawable.korean_user,
            label = stringResource(R.string.delete_user_data),
            onClick = { deletePopUpDialog = true }

        )


        HorizontalDivider(
            modifier = Modifier.padding(top = 16.dp, bottom = 8.dp),
            color = MaterialTheme.colorScheme.onSecondary
        )
        DrawerItem(
            icon = R.drawable.contact,
            label = stringResource(R.string.contact),
            onClick = {
                val intent = Intent(Intent.ACTION_SENDTO).apply {
                    data = "mailto:".toUri()
                    putExtra(
                        Intent.EXTRA_EMAIL,
                        arrayOf(context.getString(R.string.email_info_letsgetcactus))
                    )
                    putExtra(
                        Intent.EXTRA_SUBJECT,
                        context.getString(R.string.email_subject)
                    )
                    putExtra(
                        Intent.EXTRA_TEXT,
                        context.getString(R.string.email_message)
                    )
                }

                context.startActivity(intent)
            }

        )

        if (deletePopUpDialog) {
            AlertDialog(
                onDismissRequest = { deletePopUpDialog = false },

                title = {
                    Text(
                        text = stringResource(R.string.delete_account_title),
                        style = MaterialTheme.typography.titleMedium
                    )
                },

                text = {
                    Text(
                        text = stringResource(R.string.delete_account_message),
                        style = MaterialTheme.typography.bodyMedium
                    )
                },

                confirmButton = {
                    TextButton(
                        onClick = {
                            deletePopUpDialog = false

                            // Deletes user data
                            userViewModel.deleteUser(
                                onSuccess = {
                                    navController.navigate(NavigationRoutes.LOGIN_SCREEN) {
                                        popUpTo(0)
                                    }
                                    scope.launch { drawerState.close() }
                                },
                                context
                            )
                        }
                    ) {
                        Text(stringResource(R.string.continueWith))
                    }
                },

                dismissButton = {
                    TextButton(
                        onClick = { deletePopUpDialog = false }
                    ) {
                        Text(stringResource(R.string.cancel))
                    }
                }
            )
        }
    }
}
}
