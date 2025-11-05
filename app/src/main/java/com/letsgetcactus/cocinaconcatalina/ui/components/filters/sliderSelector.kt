package com.letsgetcactus.cocinaconcatalina.ui.components.filters

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import com.letsgetcactus.cocinaconcatalina.ui.theme.CocinaConCatalinaTheme

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


@Preview
@Composable
fun PreviewSliderSelector(){
    CocinaConCatalinaTheme(darkTheme = false) {
        SliderSelector(
            label = "Example",
            value = 5f,
            onValueChange = {it},
            valueRange = 1f..15f,
            steps = 9
        )
    }
}
