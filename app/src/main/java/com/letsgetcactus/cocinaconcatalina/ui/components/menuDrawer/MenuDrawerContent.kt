import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.letsgetcactus.cocinaconcatalina.R
import com.letsgetcactus.cocinaconcatalina.model.NavigationRoutes
import com.letsgetcactus.cocinaconcatalina.ui.components.menuDrawer.DrawerItem
import com.letsgetcactus.cocinaconcatalina.ui.components.menuDrawer.DrawerSwitchItem
import com.letsgetcactus.cocinaconcatalina.ui.theme.menuDColor
import com.letsgetcactus.cocinaconcatalina.viewmodel.UserViewModel

@Composable
fun MenuDrawerComponent(
    navController: NavController,
    userViewModel: UserViewModel,
    modifier: Modifier = Modifier
) {
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
                onClick = { navController.navigate(NavigationRoutes.HOME_SCREEN) }
            )

            DrawerItem(
                icon = R.drawable.favs,
                label = stringResource(R.string.favs),
                onClick = { navController.navigate(NavigationRoutes.FAVS_SCREEN) }
            )

            DrawerItem(
                icon = R.drawable.food,
                label = stringResource(R.string.modified),
                onClick = {  }
            )
            DrawerItem( //TODO: only admin
                icon = R.drawable.add,
                label = stringResource(R.string.add_recipe),
                onClick = { navController.navigate(NavigationRoutes.ADD_RECIPE_SCREEN) }
            )


            HorizontalDivider(
                modifier = Modifier.padding(top = 16.dp, bottom = 8.dp),
                color = MaterialTheme.colorScheme.onSecondary
            )

            DrawerItem(
                icon = R.drawable.translate,
                label = stringResource(R.string.language),
                onClick = { /* acción de idioma */ }
            )

            DrawerSwitchItem(
                icon = R.drawable.moon,
                label = stringResource(R.string.mode),
                checked = false,
                onCheckedChange = { }
            )


            DrawerItem(
                icon = R.drawable.exit,
                label = stringResource(R.string.close_session),
                onClick = { userViewModel.logOut()
                navController.navigate(NavigationRoutes.LOGIN_SCREEN){
                popUpTo(0)}
                }
            )

            DrawerItem(
                icon = R.drawable.korean_user,
                label = stringResource(R.string.delete_user_data),
                onClick = {
                    userViewModel.currentUser.value?.let {
                        user ->
                    // TODO: agregar eliminación de datos en Firestore
                    userViewModel.logOut()
                    navController.navigate(NavigationRoutes.LOGIN_SCREEN) {
                        popUpTo(0)
                    }
                }}
            )


            HorizontalDivider(
                modifier = Modifier.padding(top = 16.dp, bottom = 8.dp),
                color = MaterialTheme.colorScheme.onSecondary
            )
            DrawerItem(
                icon = R.drawable.contact,
                label = stringResource(R.string.contact),
                onClick = { /* enviar mail */ }
            )

            DrawerItem(
                icon = R.drawable.question,
                label = stringResource(R.string.faq),
                onClick = { /* ir a FAQ */ }
            )
        }
    }
}


