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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.letsgetcactus.cocinaconcatalina.R
import com.letsgetcactus.cocinaconcatalina.model.NavigationRoutes
import com.letsgetcactus.cocinaconcatalina.ui.components.menuDrawer.DrawerItem
import com.letsgetcactus.cocinaconcatalina.ui.components.menuDrawer.DrawerSwitchItem
import com.letsgetcactus.cocinaconcatalina.ui.theme.CocinaConCatalinaTheme

@Composable
fun MenuDrawerComponent(
    onNavigate: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    CompositionLocalProvider(LocalContentColor provides MaterialTheme.colorScheme.onSecondary) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.secondary)
                .padding(vertical = 48.dp, horizontal = 24.dp)
        ) {
            Text(
                text = stringResource(R.string.app_name),
                color = MaterialTheme.colorScheme.onSecondary,
                style = MaterialTheme.typography.bodyMedium
            )

            HorizontalDivider(modifier = Modifier.padding(top = 16.dp, bottom = 8.dp))

            // Drawer items
            DrawerItem(
                icon = R.drawable.tori_gate,
                label = stringResource(R.string.home),
                onClick = { onNavigate(NavigationRoutes.HOME_SCREEN) }
            )

            DrawerItem(
                icon = R.drawable.favs,
                label = stringResource(R.string.favs),
                onClick = { onNavigate(NavigationRoutes.FAVS_SCREEN) }
            )

            DrawerItem(
                icon = R.drawable.icon,
                label = stringResource(R.string.modified),
                onClick = { onNavigate(NavigationRoutes.MODIFIED_SCREEN) }
            )
            DrawerItem( //TODO: only admin
                icon = R.drawable.icon,
                label = stringResource(R.string.add_recipe),
                onClick = { onNavigate(NavigationRoutes.ADD_RECIPE_SCREEN) }
            )

            HorizontalDivider(
                modifier =Modifier.padding(vertical = 8.dp)
            )

            DrawerItem(
                icon = R.drawable.icon,
                label = stringResource(R.string.language),
                onClick = { /* acción de idioma */ }
            )

            DrawerSwitchItem(
                icon = R.drawable.icon,
                label = stringResource(R.string.mode),
                checked = false,
                onCheckedChange = {  }
            )


            DrawerItem(
                icon = R.drawable.icon,
                label = stringResource(R.string.close_session),
                onClick = { /* cerrar sesión */ }
            )

            DrawerItem(
                icon = R.drawable.icon,
                label = stringResource(R.string.delete_user_data),
                onClick = { /* borrar datos */ }
            )

            HorizontalDivider(
                modifier =Modifier.padding(vertical = 8.dp)
            )

            DrawerItem(
                icon = R.drawable.icon,
                label = stringResource(R.string.contact),
                onClick = { /* enviar mail */ }
            )

            DrawerItem(
                icon = R.drawable.icon,
                label = stringResource(R.string.faq),
                onClick = { /* ir a FAQ */ }
            )
        }
    }
}



@Preview
@Composable
fun PreviewMenuDrawer(){
    CocinaConCatalinaTheme(darkTheme = false) {
        MenuDrawerComponent(onNavigate = {})
    }
}