import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.letsgetcactus.cocinaconcatalina.model.enum.DificultyEnum
import com.letsgetcactus.cocinaconcatalina.model.enum.TranslatableEnum

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> DropDownMenuSelector(
    options: Array<T>,
    selected: T?,
    onSelect: (T) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier
) where T : Enum<T>, T : TranslatableEnum {

    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = modifier
    ) {
        TextField(
            value = selected?.let { stringResource(it.enumId) } ?: "",
            onValueChange = {},
            readOnly = true,
            placeholder = {
                Text(
                    placeholder,
                    style = MaterialTheme.typography.labelSmall
                )
            },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            colors = TextFieldDefaults.colors(
                MaterialTheme.colorScheme.onSurface,
                MaterialTheme.colorScheme.onSurface
            ),
            textStyle = MaterialTheme.typography.labelSmall,
            modifier = Modifier.menuAnchor() //Deprecated pero si no no muestra el desplegable

        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = {
                        Text(
                            stringResource(option.enumId),
                            style = MaterialTheme.typography.bodySmall
                        )
                    },
                    onClick = {
                        onSelect(option)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewDropDownMenu() {
    var selected by remember { mutableStateOf<DificultyEnum?>(null) }

    DropDownMenuSelector(
        options = DificultyEnum.values(),
        selected = selected,
        onSelect = { selected = it },
        placeholder = "Choose here"
    )
}
