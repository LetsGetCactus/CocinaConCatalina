package com.letsgetcactus.cocinaconcatalina.ui.screens

import android.util.Log
import android.widget.Toast
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
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.letsgetcactus.cocinaconcatalina.R
import com.letsgetcactus.cocinaconcatalina.ui.NavigationRoutes
import com.letsgetcactus.cocinaconcatalina.ui.components.ButtonGoogle
import com.letsgetcactus.cocinaconcatalina.ui.components.ButtonMain
import com.letsgetcactus.cocinaconcatalina.ui.components.ButtonSecondary
import com.letsgetcactus.cocinaconcatalina.viewmodel.UserViewModel
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    navController: NavController,
    userViewModel: UserViewModel
) {

    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current

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
            shape = MaterialTheme.shapes.small,
            textStyle = MaterialTheme.typography.bodyMedium,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(onNext = {
                focusManager.moveFocus(FocusDirection.Down)
            }),
        )


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
            shape = MaterialTheme.shapes.small,
            textStyle = MaterialTheme.typography.bodyMedium,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = {
                focusManager.clearFocus()
            }),
        )

        Spacer(
            modifier = Modifier.size(16.dp)
        )

        ButtonMain(
            buttonText = stringResource(R.string.login),
            onNavigate = {
                scope.launch {
                    if (email.isBlank() || pass.isBlank()) {
                        Toast.makeText(context, R.string.complete_all, Toast.LENGTH_SHORT).show()
                    } else if (emailError) {
                        Toast.makeText(context, R.string.emailError, Toast.LENGTH_SHORT).show()
                    } else {
                        val success = userViewModel.login(email, pass)
                        if (success) {
                            Toast.makeText(context,"${context.getString(R.string.welcome)} $userViewModel.currentUser.value.name",Toast.LENGTH_SHORT ).show()
                            navController.navigate(NavigationRoutes.HOME_SCREEN) {
                                popUpTo(NavigationRoutes.LOGIN_SCREEN) { inclusive = true }
                            }
                        } else {
                            Toast.makeText(context, R.string.email_pass_incorrect, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.size(8.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()

        ) {

            ButtonGoogle(
                onNavigate = { navController.navigate(NavigationRoutes.HOME_SCREEN) },
                modifier = Modifier.fillMaxWidth()
            )


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
            Spacer(Modifier.size(8.dp))
            Text(
                text = stringResource(R.string.forgot_pass),
                textAlign = TextAlign.End,
                color = MaterialTheme.colorScheme.tertiary,
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.clickable {
                    scope.launch {
                        Log.i("loginScreen"," Requested reset pass ")
                        if(email.isEmpty()){
                            Toast.makeText(context, context.getString(R.string.emailError),Toast.LENGTH_SHORT).show()
                            return@launch
                        }
                        userViewModel.forgotPassword(email, context)
                    }
                }
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
                style = MaterialTheme.typography.labelSmall,
                textAlign = TextAlign.Start
            )
        }
        ButtonSecondary(
            buttonText = stringResource(R.string.register),
            onNavigate = { navController.navigate(NavigationRoutes.REGISTER_SCREEN) },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.size(40.dp))

        Text(
            text = stringResource(R.string.terms),
            color = MaterialTheme.colorScheme.tertiary,
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier.clickable(
                true,
                onClick = { navController.navigate(NavigationRoutes.TERMS_CONDITIONS_SCREEN) }
            )
        )

    }
}