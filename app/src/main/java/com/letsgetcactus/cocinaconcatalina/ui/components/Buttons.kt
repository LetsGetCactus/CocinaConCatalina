package com.letsgetcactus.cocinaconcatalina.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.letsgetcactus.cocinaconcatalina.R
import com.letsgetcactus.cocinaconcatalina.viewmodel.UserViewModel

/**
 * Button in Red
 */
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

/**
 * Button in dark red
 */
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

/**
 * Button for Google login o registration using Google Identity Services for Android (GIS-Android)
 * with Credential Manager and Google Token Credential
 * @param onNavigate callback for when the signing is successful
 * @param userViewModel to persist the user's session on DataStoreManagment
 */
@Composable
fun ButtonGoogle(
    onNavigate: () -> Unit,
    modifier: Modifier,
    userViewModel: UserViewModel
) {

val context= LocalContext.current

    Box(
        modifier = modifier.fillMaxWidth()
    ) {

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(8.dp),
            onClick = { userViewModel.logInWithGoogle(context) },
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

/**
 * Round button
 */
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
            style = MaterialTheme.typography.bodyMedium
        )
    }
}


/**
 * Pair of two buttons (red and dark red)
 */
@Composable
fun ButtonPair(
    textRight: String,
    textLeft: String,
    onNavigateRight: () -> Unit,
    onNavigateLeft: () -> Unit

) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly

    ) {
        ButtonMain(
            buttonText = textLeft,
            onNavigate = { onNavigateLeft()},
            modifier = Modifier.weight(1f)
        )

        Spacer(Modifier.size(24.dp))
        ButtonSecondary(
            buttonText =textRight,
            onNavigate = { onNavigateRight()},
            modifier = Modifier.weight(1f)
        )
    }
}
