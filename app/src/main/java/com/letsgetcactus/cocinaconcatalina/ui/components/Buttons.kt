package com.letsgetcactus.cocinaconcatalina.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.letsgetcactus.cocinaconcatalina.R
import com.letsgetcactus.cocinaconcatalina.ui.theme.CocinaConCatalinaTheme

@Composable
fun ButtonMain(
    buttonText: String,
    onNavigate: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = { onNavigate() },
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        ),
        shape = MaterialTheme.shapes.small,
    ) {
        Text(
            text = buttonText,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
}

@Composable
fun ButtonSecondary(
    buttonText: String,
    onNavigate: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = { onNavigate() },
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.secondary,
            contentColor = MaterialTheme.colorScheme.onSecondary
        ),
        shape = MaterialTheme.shapes.small,
    ) {
        Text(
            text = buttonText,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSecondary
        )
    }
}


@Composable
fun ButtonGoogle(
    onNavigate: () -> Unit,
    modifier: Modifier
) {
    Box(
        modifier = modifier.fillMaxWidth()
    ) {

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(8.dp),
            onClick = {//TODO: save with Google
                onNavigate()
            },
            shape = MaterialTheme.shapes.small,
            colors = ButtonDefaults.buttonColors(
                MaterialTheme.colorScheme.surface,
                MaterialTheme.colorScheme.onSurface
            )
        ) {
            Text(
                text = stringResource(R.string.googleRegister),
                modifier = Modifier.padding(start = 16.dp),
                style = MaterialTheme.typography.bodySmall
            )
        }


        Image(
            painter = painterResource(R.drawable.google),
            contentDescription = stringResource(R.string.googleImg),
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(start = 8.dp)
                .size(24.dp),
            contentScale = ContentScale.Fit
        )
    }
}

@Composable
fun ButtonRound(
    buttonText: String,
    onNavigate: () -> Unit
) {
    Button(
        onClick = { onNavigate() },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 64.dp),
        shape = MaterialTheme.shapes.large,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        )
    ) {
        Text(
            text = buttonText,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}


@Preview
@Composable
fun PreviewButtons() {
    CocinaConCatalinaTheme(darkTheme = false) {
        Column {
            ButtonMain(
                buttonText = "Accept",
                onNavigate = { },
                modifier = Modifier.fillMaxWidth()
            )
            ButtonSecondary(
                buttonText = "Cancel",
                onNavigate = {},
                modifier = Modifier.fillMaxWidth(),
            )
            ButtonGoogle(
                onNavigate = {},
                modifier = Modifier.fillMaxWidth()
            )
            ButtonRound(
                buttonText = "Recipes",
                onNavigate = {}
            )
        }
    }
}