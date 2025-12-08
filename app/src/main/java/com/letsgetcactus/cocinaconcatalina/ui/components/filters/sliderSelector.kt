package com.letsgetcactus.cocinaconcatalina.ui.components.filters

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Selection bar to select from a range of numbers
 */
@Composable
fun SliderSelector(
    label: String,
    value: Float,
    onValueChange: (Float) -> Unit,
    valueRange: ClosedFloatingPointRange<Float> = 0f..10f,
    steps: Int = 0
) {
    Column(
        modifier = Modifier.fillMaxWidth().padding(16.dp)
    ) {

        Text(
            text = "$label: ${value.toInt()}",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onTertiary
        )

        Spacer(modifier = Modifier.height(8.dp))


        Slider(
            value = value,
            onValueChange = onValueChange,
            valueRange = valueRange,
            steps = steps
        )
    }
}


