package com.letsgetcactus.cocinaconcatalina.ui.components.filters

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.letsgetcactus.cocinaconcatalina.model.enum.AllergenEnum
import com.letsgetcactus.cocinaconcatalina.ui.theme.CocinaConCatalinaTheme

@Composable
fun AllergenIconsSelector(
    selectedAllergens: Map<AllergenEnum, Boolean>,
    onSelectionChanged: (Map<AllergenEnum, Boolean>) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier.padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        items(AllergenEnum.entries, key = {it.name}) {
            allergen ->

            val isSelected = selectedAllergens[allergen] ?: false

            AllergenItem(
                allergen = allergen,
                isSelected = isSelected,
                onClick = {
                    val updated = selectedAllergens.toMutableMap()
                    updated[allergen] = !isSelected
                    onSelectionChanged(updated)
                }
            )
        }
    }
}

@Composable
fun AllergenItem(
    allergen: AllergenEnum,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(8.dp)
            .clickable(onClick = onClick)
    ) {
        Image(
            painter = painterResource(
                id = if (isSelected) allergen.colorDrawable else allergen.greyDrawable
            ),
            contentDescription = allergen.name,
            modifier = Modifier.size(48.dp)
        )
        Text(
            text = allergen.name.lowercase().replaceFirstChar { it.uppercase() },
            style = MaterialTheme.typography.labelSmall,
            color = if (isSelected)
                MaterialTheme.colorScheme.primary
            else
                MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewAllergensSelector() {
    CocinaConCatalinaTheme {
        var selected by remember {
            mutableStateOf(AllergenEnum.entries.associateWith { false })
        }

        AllergenIconsSelector(
            selectedAllergens = selected,
            onSelectionChanged = { selected = it }
        )
    }
}
