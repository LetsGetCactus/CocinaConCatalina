package com.letsgetcactus.cocinaconcatalina.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.letsgetcactus.cocinaconcatalina.R
import com.letsgetcactus.cocinaconcatalina.ui.theme.CocinaConCatalinaTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarComposable(
    onMenu: () -> Unit,
    onSearchChanged: (String) -> Unit
) {

    var isSearchActive by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }

    TopAppBar(
        colors = topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary,

            ),
        navigationIcon = {
            Icon(
                painter = painterResource(R.drawable.menu_drawer),
                contentDescription = stringResource(R.string.menu_drawer),
                modifier = Modifier
                    .padding(16.dp)
                    .clickable { onMenu() }
                    .size(48.dp)

            )
        },

        title = {
            if (!isSearchActive) {
                Text(
                    text = stringResource(R.string.app_name),
                    style = MaterialTheme.typography.titleLarge
                )
            } else {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = {
                        searchQuery = it
                        onSearchChanged(it.text)
                    },

                    placeholder = {
                        Text(
                            text = stringResource(R.string.search_bar),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .background(
                            color = MaterialTheme.colorScheme.surface,
                            shape = MaterialTheme.shapes.large
                        ),
                    shape = MaterialTheme.shapes.large,
                    singleLine = true,
                    textStyle = MaterialTheme.typography.bodyMedium,
                    trailingIcon = {
                        IconButton(onClick = {
                            if (searchQuery != null) {
                                searchQuery = TextFieldValue("")
                            } else {
                                isSearchActive = false
                            }
                        }) {
                            Icon(
                                painter = painterResource(R.drawable.search),
                                contentDescription = stringResource(R.string.close)
                            )
                        }
                    }
                )
            }
        },
        actions = {
            if (!isSearchActive) {
                Icon(
                    painter = painterResource(R.drawable.search),
                    contentDescription = stringResource(R.string.search),
                    modifier = Modifier
                        .padding(end = 16.dp)
                        .size(40.dp)
                        .clickable { isSearchActive = true }
                )
            } else {
                Icon(
                    painter = painterResource(R.drawable.search),
                    contentDescription = stringResource(R.string.close),
                    modifier = Modifier
                        .size(40.dp)
                        .padding(end = 16.dp)
                        .clickable {
                            isSearchActive = false
                            searchQuery = TextFieldValue("")
                        }
                )
            }
        }
    )
}


@Preview
@Composable
fun PreviewTopAppBarComponent() {
    CocinaConCatalinaTheme(darkTheme = false) {
        TopBarComposable(
            onMenu = {},
            onSearchChanged = {}
        )
    }
}