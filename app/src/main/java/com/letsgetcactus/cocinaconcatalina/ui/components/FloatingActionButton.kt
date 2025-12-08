package com.letsgetcactus.cocinaconcatalina.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.letsgetcactus.cocinaconcatalina.R


/**
 * Floating Action Button to be shown on top of the Screens to perform something:
 * - Save a recipe
 * - Accept the Terms and conditions and navigate back
 * - etc
 */
@Composable
fun FAB(
    onNavigate: () -> Unit
) {
    FloatingActionButton(
        onClick = { onNavigate()},
        containerColor = MaterialTheme.colorScheme.secondary,
        contentColor = MaterialTheme.colorScheme.onSecondary,
        modifier = Modifier
            .padding(16.dp)
            .size(72.dp),
        shape = MaterialTheme.shapes.large,

        ) {
        Icon(
            painter = painterResource(R.drawable.save),
            contentDescription = stringResource(R.string.save),
            modifier = Modifier.size(32.dp)
        )
    }
}