package com.letsgetcactus.cocinaconcatalina.ui.screens// Archivo: app/src/main/java/com/letsgetcactus/cocinaconcatarina2/ui/screens/RegisterScreen.kt

import android.util.Patterns
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.letsgetcactus.cocinaconcatalina.R
import com.letsgetcactus.cocinaconcatalina.ui.theme.CocinaConCatalinaTheme


/**
 * Validates an email
 *
 * @param email to be validated or not
 */
fun isValidEmail(email: String): Boolean {
    return Patterns.EMAIL_ADDRESS.matcher(email).matches()
}

/**
 * Screen to register a new user of the app
 *
 * @param onLogin Action to go back to login screen
 * @param onRegister action to register a new user when clicking the button
 * @param loginPass password given in the LoginScreen to be shown on the textfield now
 * @param loginEmail email given on LoginScreen to be shown
 */
@Composable
fun RegisterScreen(
    loginEmail: String,
    loginPass: String,
    onRegister: (String, String) -> Unit,
    onLogin: () -> Unit,


    ) {
    // States for the textFields
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf(loginEmail) }
    var pass by remember { mutableStateOf(loginPass) }
    var confirmPass by remember { mutableStateOf("") }


    //States for possible errors on mail or password
    var emailError by remember { mutableStateOf(false) }
    var passError by remember { mutableStateOf(false) }



    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(48.dp,40.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center


        ) {
        // Image
        Image(
            painter = painterResource(id = R.drawable.icon),
            contentDescription = stringResource(R.string.ccc_icon),
            modifier = Modifier
                .size(96.dp)
                .align(Alignment.Start)
                .shadow(
                    elevation = 16.dp,
                    shape = CircleShape,

                    )

        )

        //Register text
        Text(
            text = stringResource(R.string.register_title),
            modifier = Modifier
                .align(Alignment.Start)
                .padding(bottom = 24.dp),
            style = MaterialTheme.typography.displayLarge,
            color = MaterialTheme.colorScheme.tertiary

        )
        // Name
        TextField(
            value = name,
            onValueChange = { name = it },
            label = {
                Text(
                    text = stringResource(R.string.insert_name),
                    style = MaterialTheme.typography.labelSmall,
                )
            },
            placeholder = {
                Text(
                    text = stringResource(R.string.insert_name),
                    style = MaterialTheme.typography.bodySmall,
                )
            },

            modifier = Modifier
                .shadow(8.dp)
                .fillMaxWidth()
                .padding(bottom = 8.dp),

            colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.surface,
                unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                focusedTextColor = MaterialTheme.colorScheme.onBackground,
                unfocusedTextColor = MaterialTheme.colorScheme.onBackground,
            ),
            shape = MaterialTheme.shapes.small

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
                    style = MaterialTheme.typography.bodySmall,
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
                focusedTextColor = MaterialTheme.colorScheme.onBackground,
                unfocusedTextColor = MaterialTheme.colorScheme.onBackground,
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
                    style = MaterialTheme.typography.bodySmall,
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
                focusedTextColor = MaterialTheme.colorScheme.onBackground,
                unfocusedTextColor = MaterialTheme.colorScheme.onBackground,
            ),
            shape = MaterialTheme.shapes.small
        )


        // Confirm password
        TextField(
            value = confirmPass,
            onValueChange = { confirmPass = it },
            label = {
                Text(
                    text = stringResource(R.string.confirm_pass),
                    style = MaterialTheme.typography.labelSmall,
                )
            },
            placeholder = {
                Text(
                    text = stringResource(R.string.confirm_pass),
                    style = MaterialTheme.typography.bodySmall,
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .shadow(8.dp),
            visualTransformation = PasswordVisualTransformation(),
            isError = pass != confirmPass && confirmPass.isNotEmpty(),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.surface,
                unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                focusedTextColor = MaterialTheme.colorScheme.onBackground,
                unfocusedTextColor = MaterialTheme.colorScheme.onBackground,
            ),
            shape = MaterialTheme.shapes.small
        )
        if (pass != confirmPass && confirmPass.isNotEmpty()) {
            Text(
                text = "Las contraseñas no coinciden",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }


        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = false,
                onCheckedChange = { it },
                colors = CheckboxDefaults.colors(
                    checkedColor = MaterialTheme.colorScheme.primary,
                    checkmarkColor = MaterialTheme.colorScheme.onPrimary
                ),
            )
            Text(
                text = stringResource(R.string.termsAndConditions),
                modifier = Modifier
                    .clickable { }
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onBackground

            )
        }

        Spacer(Modifier.height(24.dp))

        //Action buttons: Back and Register
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(8.dp),
                onClick = onLogin,
                shape = MaterialTheme.shapes.small,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )

            ) {
                Text(
                    text = stringResource(R.string.back),
                    style = MaterialTheme.typography.labelLarge
                )
            }
            Spacer(modifier=Modifier.size(8.dp))
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(8.dp),
                onClick = {
                    if (isValidEmail(email) && pass == confirmPass) {
                        onRegister(email, pass)
                    } else {
                        emailError = !isValidEmail(email)
                        passError = pass != confirmPass
                    }
                },
                shape = MaterialTheme.shapes.small,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondary,
                    contentColor = MaterialTheme.colorScheme.onSecondary
                )


            ) {
                Text(
                    text = stringResource(R.string.register),
                    style = MaterialTheme.typography.labelLarge
                )
            }
            Spacer(Modifier.height(36.dp))

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
    }

}


@Preview(showBackground = true)
@Composable
fun PreviewRegisterScreen() {
    CocinaConCatalinaTheme(darkTheme = false) {
    RegisterScreen(
        onRegister = { _, _ -> },
        onLogin = { },
        loginEmail = "",
        loginPass = ""
    )
    }

}