package com.letsgetcactus.cocinaconcatalina.ui.components.filters

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.letsgetcactus.cocinaconcatalina.ui.theme.CocinaConCatalinaTheme

@Composable
fun ChipSelector(
    modifier: Modifier = Modifier,
    title: String? = null,
    options: List<String>,
    selectedOptions: Set<String>,
    onSelectionChanged: (String) -> Unit,
    singleSelection: Boolean = false,
    ) {
    Column(modifier = modifier) {
        title?.let {
            Text(
                text = it,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(Modifier.height(8.dp))
        }

        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            options.forEach { option ->
                val isSelected = selectedOptions.contains(option)
                FilterChip(
                    selected = isSelected,
                    onClick = {
                        val newSelection = when {
                            singleSelection -> setOf(option)
                            isSelected -> selectedOptions - option
                            else -> selectedOptions + option
                        }
                        onSelectionChanged(option)
                    },
                    label = {
                        Text(text= option,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                            },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = MaterialTheme.colorScheme.primary,
                        selectedLabelColor = MaterialTheme.colorScheme.onPrimary,
                        containerColor = MaterialTheme.colorScheme.surface,
                        labelColor = MaterialTheme.colorScheme.onSurface
                    )
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewChip() {
    CocinaConCatalinaTheme(darkTheme = false) {
        //TODO: Ejemplo
        var selectedPlato: Set<String> by remember { mutableStateOf(emptySet()) }

        Column {
            ChipSelector(
                title = "Plato",
                options = listOf("Entrante", "Sopa", "Principal", "Postre"),
                selectedOptions = selectedPlato,
                onSelectionChanged = { selectedPlato }


            )
        }
    }
}