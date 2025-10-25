package com.letsgetcactus.cocinaconcatalina.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.FloatingActionButton
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
fun TermsAndConditionsScreen(
    onNavigate: (String) -> Unit
){

    //TODO: Crear terms and conditions con: plitica de privacidad, coockies, aviso legal,etc
    //TODO: traducirlo!! Boton para select idioma?
    
    val scrollState = rememberScrollState()


    Box() {
        Column(
            modifier = Modifier.fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(horizontal = 16.dp, vertical = 24.dp)
                .verticalScroll(scrollState)
        ) {
            Image(
                painterResource(R.drawable.icon),
                contentDescription = stringResource(R.string.ccc_icon),
                modifier=Modifier.align(Alignment.CenterHorizontally))
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
            FloatingActionButton(
                onClick = { /*TODO: Guardar cambios */
                    onNavigate(NavigationRoutes.REGISTER_SCREEN)},
                containerColor = MaterialTheme.colorScheme.secondary,
                contentColor = MaterialTheme.colorScheme.onSecondary,
                modifier = Modifier
                    .padding(16.dp)
                    .size(72.dp),
                shape = MaterialTheme.shapes.large,

                ) {
                Icon(
                    painter = painterResource(R.drawable.tori_gate),
                    contentDescription = stringResource(R.string.termsAndConditions),
                    modifier = Modifier.size(32.dp)
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewTermsAndConditions(){
    CocinaConCatalinaTheme(darkTheme = false) {
        TermsAndConditionsScreen(onNavigate = {})
    }
}