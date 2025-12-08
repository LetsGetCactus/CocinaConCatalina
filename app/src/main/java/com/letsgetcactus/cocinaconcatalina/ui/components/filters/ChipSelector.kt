package com.letsgetcactus.cocinaconcatalina.ui.components.filters

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Selection buttons to filter the recipes shown by their origin
 */
@Composable
fun ChipSelector(
    modifier: Modifier = Modifier,
    title: String? = null,
    options: List<String>,
    selectedOptions: Set<String>,
    onSelectionChanged: (Set<String>) -> Unit,
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
                val isSelected = option in selectedOptions
                FilterChip(
                    selected = isSelected,
                    onClick = {
                        val newSelection = when {
                           singleSelection && isSelected -> emptySet()
                            singleSelection -> setOf(option)
                            isSelected -> selectedOptions - option
                            else -> selectedOptions + option
                        }
                        onSelectionChanged(newSelection)

                    },
                    label = {
                        Text(text= option,
                            style = MaterialTheme.typography.labelSmall,
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
