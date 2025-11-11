package com.letsgetcactus.cocinaconcatalina.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.letsgetcactus.cocinaconcatalina.R
import com.letsgetcactus.cocinaconcatalina.ui.theme.CocinaConCatalinaTheme

@Composable
fun BackStackButton(
    navController: NavController
) {

    val darkMode = isSystemInDarkTheme()

    Box(
        Modifier.clickable { navController.popBackStack() }
    ) {
        Icon(
            painter = painterResource(
                if (!darkMode) R.drawable.back else R.drawable.back_white
            ),
            contentDescription = stringResource(R.string.back),
            modifier = Modifier.size(32.dp)

        )
    }
}

@Preview
@Composable
fun PreviewBackButton(){
    CocinaConCatalinaTheme(darkTheme = false) {
        BackStackButton(
            navController = rememberNavController()
        )
    }
}
