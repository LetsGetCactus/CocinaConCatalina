package com.letsgetcactus.cocinaconcatalina.ui.screens// Archivo: app/src/main/java/com/letsgetcactus/cocinaconcatarina2/ui/screens/RegisterScreen.kt

import android.os.Build
import android.util.Patterns
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.letsgetcactus.cocinaconcatalina.R
import com.letsgetcactus.cocinaconcatalina.model.NavigationRoutes
import com.letsgetcactus.cocinaconcatalina.ui.components.ButtonGoogle
import com.letsgetcactus.cocinaconcatalina.ui.components.ButtonMain
import com.letsgetcactus.cocinaconcatalina.ui.components.ButtonSecondary
import com.letsgetcactus.cocinaconcatalina.viewmodel.UserViewModel
import kotlinx.coroutines.launch


/**
 * Validates an email
 * @param email to be validated or not
 */
fun isValidEmail(email: String): Boolean {
    return Patterns.EMAIL_ADDRESS.matcher(email).matches()
}

/**
 * Screen to register a new user of the app
 */
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RegisterScreen(
    navController: NavController,
    userViewModel: UserViewModel
) {


    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    // States for the textFields
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var pass by remember { mutableStateOf("") }
    var confirmPass by remember { mutableStateOf("") }

    //Checkbox state
    var checkedTerms by remember { mutableStateOf(false) }

    //States for possible errors on mail or password
    var emailError by remember { mutableStateOf(false) }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(48.dp, 40.dp),
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


        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = checkedTerms,
                onCheckedChange = { checkedTerms = it },
                colors = CheckboxDefaults.colors(
                    checkedColor = MaterialTheme.colorScheme.primary,
                    checkmarkColor = MaterialTheme.colorScheme.onPrimary
                ),
            )
            Text(
                text = stringResource(R.string.termsAndConditions),
                modifier = Modifier
                    .clickable { navController.navigate(NavigationRoutes.TERMS_CONDITIONS_SCREEN) }
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onBackground

            )
        }

        Spacer(Modifier.height(24.dp))

        //Buttons:Back and Register
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ButtonMain(
                buttonText = stringResource(R.string.back),
                onNavigate = { navController.navigate(NavigationRoutes.LOGIN_SCREEN) },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.size(8.dp))

            ButtonSecondary(
                buttonText = stringResource(R.string.register),
                modifier = Modifier.fillMaxWidth(),
                onNavigate = {
                    scope.launch {
                        if (name.isBlank() || email.isBlank() || pass.isBlank() || confirmPass.isBlank()) {
                            Toast.makeText(context, R.string.complete_all, Toast.LENGTH_SHORT)
                                .show()
                            return@launch

                        } else if (pass != confirmPass) {
                            Toast.makeText(context, R.string.pass_not_matching, Toast.LENGTH_SHORT)
                                .show()
                            return@launch

                        } else if (!isValidEmail(email)) {
                            Toast.makeText(context, R.string.emailError, Toast.LENGTH_SHORT).show()
                            return@launch

                        } else if (!checkedTerms) {
                            Toast.makeText(context, R.string.not_accepted_terms, Toast.LENGTH_SHORT)
                                .show()
                            return@launch
                        } else {

                            val success = userViewModel.register(name, email, pass)
                            if (success) {
                                navController.navigate(NavigationRoutes.HOME_SCREEN) {
                                    popUpTo(NavigationRoutes.REGISTER_SCREEN) { inclusive = true }
                                }
                                Toast.makeText(
                                    context,
                                    "${context.getString(R.string.welcome)} $name",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                Toast.makeText(
                                    context,
                                    context.getString(R.string.email_pass_incorrect),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                }
            )



            Spacer(Modifier.height(36.dp))

            ButtonGoogle( //TODO
                onNavigate = { },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }

}

