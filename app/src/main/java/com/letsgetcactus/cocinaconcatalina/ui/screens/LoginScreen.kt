package com.letsgetcactus.cocinaconcatalina.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.letsgetcactus.cocinaconcatalina.R
import com.letsgetcactus.cocinaconcatalina.ui.theme.Beis
import com.letsgetcactus.cocinaconcatalina.ui.theme.CocinaConCatalinaTheme
import com.letsgetcactus.cocinaconcatalina.ui.theme.Red

@Composable
fun LoginScreen() {

    //States for the Text fields (email and password)
    var email by remember { mutableStateOf("") }
    var pass by remember { mutableStateOf("") }
    var emailError by remember { mutableStateOf(false) }


    //UI components
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(48.dp, 40.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center


    ) {
        Image(
            painter = painterResource(R.drawable.icon),
            contentDescription = stringResource(R.string.ccc_icon),
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .size(160.dp)
                .padding(16.dp),

            )

        Text(
            text = stringResource(R.string.welcome),
            modifier = Modifier
                .fillMaxWidth(),
            color = MaterialTheme.colorScheme.tertiary,
            style = MaterialTheme.typography.displayLarge
        )

        Spacer(
            modifier = Modifier.size(24.dp)
        )
        // Email
        TextField(
            value = email,
            onValueChange = {
                email = it
                emailError = !isValidEmail(it) && it.isNotEmpty()
            },
            label = {
                Text(
                    text = stringResource(R.string.email),
                    style = MaterialTheme.typography.labelSmall,
                )
            },
            placeholder = {
                Text(
                    text = stringResource(R.string.email),
                    style = MaterialTheme.typography.labelLarge,
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
                .shadow(8.dp),
            isError = emailError,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.surface,
                unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                focusedTextColor = MaterialTheme.colorScheme.onSurface,
                unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
            ),
            shape = MaterialTheme.shapes.small
        )
        if (emailError) {
            Text(
                text = "Formato de email incorrecto",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )

        }


        // Password
        TextField(
            value = pass,
            onValueChange = { pass = it },
            label = {
                Text(
                    text = stringResource(R.string.pass),
                    style = MaterialTheme.typography.labelSmall,
                )
            },
            placeholder = {
                Text(
                    text = stringResource(R.string.pass),
                    style = MaterialTheme.typography.labelLarge,
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .shadow(8.dp)
                .padding(bottom = 8.dp),
            visualTransformation = PasswordVisualTransformation(),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.surface,
                unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                focusedTextColor = MaterialTheme.colorScheme.onSurface,
                unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
            ),
            shape = MaterialTheme.shapes.small
        )

        Spacer(
            modifier = Modifier.size(16.dp)
        )

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(8.dp),
            onClick = { },
            shape = MaterialTheme.shapes.small,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            )

        ) {
            Text(
                text = stringResource(R.string.login),
                style = MaterialTheme.typography.labelLarge
            )
        }
        Spacer(modifier = Modifier.size(8.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()

        ) {

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(8.dp),
                onClick = {},
                shape = MaterialTheme.shapes.small,
                colors = ButtonDefaults.buttonColors(
                    MaterialTheme.colorScheme.secondary,
                    MaterialTheme.colorScheme.onSecondary
                )
            ) {
                Text(
                    text = stringResource(R.string.googleRegister),
                    modifier = Modifier.padding(start = 16.dp),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSecondary
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
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            Text(
                text = stringResource(R.string.forgot_pass),
                textAlign = TextAlign.End,
                color = MaterialTheme.colorScheme.tertiary,
                style = MaterialTheme.typography.bodySmall,

                )
        }
        Spacer(Modifier.height(40.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {
            Text(
                text = stringResource(R.string.new_here),
                color = MaterialTheme.colorScheme.tertiary,
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Start
            )
        }
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(8.dp),
            onClick = { },
            shape = MaterialTheme.shapes.small,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onSecondary
            )


        ) {
            Text(
                text = stringResource(R.string.register),
                style = MaterialTheme.typography.labelLarge
            )
        }

        Spacer(modifier = Modifier.size(40.dp))

        Text(
            text = stringResource(R.string.terms),
            color = MaterialTheme.colorScheme.tertiary,
            style = MaterialTheme.typography.labelSmall
        )

    }
}

@Composable
@Preview
fun PreviewLogin() {
    CocinaConCatalinaTheme(darkTheme = false) {
        LoginScreen()
    }
}