package com.letsgetcactus.cocinaconcatalina.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import com.letsgetcactus.cocinaconcatalina.R
import com.letsgetcactus.cocinaconcatalina.model.NavigationRoutes
import com.letsgetcactus.cocinaconcatalina.ui.theme.CocinaConCatalinaTheme

@Composable
fun SearchBarComponent(
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    onFilterClick: () -> Unit,
    onCloseClick: () -> Unit
) {
   Column (
        modifier = Modifier.fillMaxWidth()
    ) {
    TextField(
        value = searchQuery,
        onValueChange = { onSearchQueryChange(it) },
        placeholder = { Text("Buscar categoría") },
        leadingIcon = {
            Icon(Icons.Default.Search, contentDescription = stringResource(R.string.search))
        },
        trailingIcon = {
            if(searchQuery.isNotEmpty()) {
                IconButton(onClick = onCloseClick) {
                    Icon(Icons.Default.Close, contentDescription = stringResource(R.string.close))
                }
            }
        },
        singleLine = true,
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        textStyle = MaterialTheme.typography.bodyMedium
    )
       Spacer(modifier = Modifier.width(16.dp))

       // Botón de filtros avanzados
       ButtonSecondary(
           buttonText = stringResource(R.string.advanced_filters),
           onNavigate = { onFilterClick()},
           modifier = Modifier.align(Alignment.CenterHorizontally)

       )
    }
}


@Preview
@Composable
fun PreviewSearchBar(){
    CocinaConCatalinaTheme(darkTheme = false) {
        SearchBarComponent(
            searchQuery = "Ramen",
            onSearchQueryChange = {it},
            onFilterClick = {},
            onCloseClick = {}
        )
    }
}