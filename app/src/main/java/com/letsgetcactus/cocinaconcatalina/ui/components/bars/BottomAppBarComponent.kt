package com.letsgetcactus.cocinaconcatalina.ui.components.bars

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import com.letsgetcactus.cocinaconcatalina.model.NavigationRoutes
import com.letsgetcactus.cocinaconcatalina.ui.theme.CocinaConCatalinaTheme

@Composable
fun BottomBarComposable(
    onNavigate: (String) -> Unit
) {
    BottomAppBar(
        modifier = Modifier.fillMaxWidth(),
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary,
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ){
        //Spotify
        Column(
            //TODO: Añadir Spoty = .clickable
            modifier = Modifier
                .weight(1f)
                .padding(8.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally

            ) {
            Icon(
                painter = painterResource(R.drawable.spotify),
                contentDescription = stringResource(R.string.spotify),
                modifier = Modifier.weight(1f)

            )
            Text(
                modifier = Modifier.weight(1f),
                text = stringResource(R.string.spotify),
                style = MaterialTheme.typography.labelSmall,
            )
        }

        //Favs
        Column(
            modifier = Modifier
                .clickable { onNavigate(NavigationRoutes.FAVS_SCREEN) }
                .weight(1f)
                .padding(8.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painter = painterResource(R.drawable.favs),
                contentDescription = stringResource(R.string.favs),
                modifier = Modifier.weight(1f)
            )
            Text(
                modifier = Modifier.weight(1f),
                text = stringResource(R.string.favs),
                style = MaterialTheme.typography.labelSmall
            )
        }

        //Home
        Column(
            modifier = Modifier
                .clickable { onNavigate(NavigationRoutes.HOME_SCREEN) }
                .weight(1f)
                .padding(8.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painter = painterResource(R.drawable.tori_gate),
                contentDescription = stringResource(R.string.home),
                modifier = Modifier.weight(1f)
            )
            Text(
                modifier = Modifier.weight(1f),
                text = stringResource(R.string.home),
                style = MaterialTheme.typography.labelSmall
            )
        }
    }
}}

@Preview
@Composable
fun PreviewMainScreen() {
    CocinaConCatalinaTheme(darkTheme = false) {
        BottomBarComposable(
            onNavigate = {},
        )
    }
}