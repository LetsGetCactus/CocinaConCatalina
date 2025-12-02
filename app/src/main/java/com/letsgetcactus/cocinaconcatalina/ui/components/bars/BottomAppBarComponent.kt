package com.letsgetcactus.cocinaconcatalina.ui.components.bars

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.letsgetcactus.cocinaconcatalina.R
import com.letsgetcactus.cocinaconcatalina.ui.NavigationRoutes
import com.letsgetcactus.cocinaconcatalina.ui.theme.CocinaConCatalinaTheme

@Composable
fun BottomBarComposable(
    onNavigate: (String) -> Unit,
) {
    BottomAppBar(
        modifier = Modifier.fillMaxWidth(),
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Spotify
            BottomBarItem(
                icon = R.drawable.spotify,
                label = stringResource(R.string.spotify),
                onClick = { onNavigate(NavigationRoutes.LIST_RECIPES_HOST_SCREEN) }
            )

            // Favs
            BottomBarItem(
                icon = R.drawable.favs,
                label = stringResource(R.string.favs),
                onClick = { onNavigate(NavigationRoutes.FAVS_SCREEN) }
            )

            // Home
            BottomBarItem(
                icon = R.drawable.tori_gate,
                label = stringResource(R.string.home),
                onClick = { onNavigate(NavigationRoutes.HOME_SCREEN) }
            )
        }
    }
}

@Composable
private fun BottomBarItem(
    icon: Int,
    label: String,
    onClick: () -> Unit
) {
     Column(
        modifier = Modifier
            .clickable { onClick() }
            .padding(vertical = 6.dp),
        verticalArrangement = Arrangement.spacedBy(2.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(icon),
            contentDescription = label,
            modifier = Modifier.padding(bottom = 2.dp)
                .size(32.dp)
        )
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
}

@Preview
@Composable
fun PreviewMainScreen() {
    CocinaConCatalinaTheme(darkTheme = false) {
        BottomBarComposable(
            onNavigate = {},
        )
    }
}