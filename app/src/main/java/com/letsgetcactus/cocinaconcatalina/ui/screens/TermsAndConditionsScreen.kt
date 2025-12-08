package com.letsgetcactus.cocinaconcatalina.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.letsgetcactus.cocinaconcatalina.R
import com.letsgetcactus.cocinaconcatalina.ui.NavigationRoutes
import com.letsgetcactus.cocinaconcatalina.ui.components.FAB

/**
 * Terms,conditions, and policy Screen for the user to be obligatory accepted to enjoy the app
 */
@Composable
fun TermsAndConditionsScreen(
    onNavigate: (String) -> Unit
){

    val scrollState = rememberScrollState()


    Box() {
        Column(
            modifier = Modifier.fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(horizontal = 24.dp, vertical = 32.dp)
                .verticalScroll(scrollState)
        ) {
            Image(
                painterResource(R.drawable.icon),
                contentDescription = stringResource(R.string.ccc_icon),
                modifier=Modifier.align(Alignment.CenterHorizontally)
                    .padding(bottom = 16.dp))
            Text(
            text = stringResource(R.string.termsAndConditionsExplained),
                style = MaterialTheme.typography.bodySmall
            )
        }

        //FAB
        Column(
            Modifier.fillMaxSize()
                .padding(horizontal= 24.dp, vertical =48.dp),
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.Bottom
        ) {
            FAB(
                onNavigate = {
                    onNavigate(NavigationRoutes.REGISTER_SCREEN)}
            )
        }
    }
}
