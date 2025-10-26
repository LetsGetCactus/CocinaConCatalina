import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.letsgetcactus.cocinaconcatalina.R
import com.letsgetcactus.cocinaconcatalina.ui.theme.CocinaConCatalinaTheme

@Composable
fun MenuDrawerComponent(
    onNavigate: (String) -> Unit,
    modifier: Modifier = Modifier
){
    //TODO: iconos sse ven en negro?
    //TODO: editar navegacion/onClick
    Column(
        modifier= modifier.fillMaxSize()
            .background(MaterialTheme.colorScheme.secondary)
            .padding(vertical = 48.dp, horizontal = 24.dp)
    ) {
        Text(
            text= stringResource(R.string.app_name),
            color = MaterialTheme.colorScheme.onSecondary,
            style = MaterialTheme.typography.bodyLarge

        )

        HorizontalDivider(
            modifier = Modifier.padding(top= 16.dp)
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(8.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.tori_gate),
                contentDescription = stringResource(R.string.home),
                modifier= Modifier.size(48.dp)
                    .padding(end = 8.dp)
            )
            Text(
                text = stringResource(R.string.home),
                color = MaterialTheme.colorScheme.onSecondary,
                style = MaterialTheme.typography.bodySmall
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(8.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.favs),
                contentDescription = stringResource(R.string.favs),
                modifier= Modifier.size(48.dp)
                    .padding(end = 8.dp)
            )
            Text(
                text = stringResource(R.string.favs),
                color = MaterialTheme.colorScheme.onSecondary,
                style = MaterialTheme.typography.bodySmall
            )
        }

        Row(//TODO: icon
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(8.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.icon),
                contentDescription = stringResource(R.string.modified),
                modifier= Modifier.size(48.dp)
                    .padding(end = 8.dp)
            )
            Text(
                text = stringResource(R.string.modified),
                color = MaterialTheme.colorScheme.onSecondary,
                style = MaterialTheme.typography.bodySmall
            )
        }


        Row(//TODO: icon
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(8.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.icon),
                contentDescription = stringResource(R.string.config),
                modifier= Modifier.size(48.dp)
                    .padding(end = 8.dp)
            )
            Text(
                text = stringResource(R.string.config),
                color = MaterialTheme.colorScheme.onSecondary,
                style = MaterialTheme.typography.bodySmall
            )
        }

        HorizontalDivider()

        Row(//TODO:icon
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(8.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.icon),
                contentDescription = stringResource(R.string.language),
                modifier= Modifier.size(48.dp)
                    .padding(end = 8.dp)
            )
            Text(
                text = stringResource(R.string.language),
                color = MaterialTheme.colorScheme.onSecondary,
                style = MaterialTheme.typography.bodySmall
            )
        }

        Row(//TODO: icon y funcionalidad switch cambio modo oscuro
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(8.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.icon),
                contentDescription = stringResource(R.string.home),
                modifier= Modifier.size(48.dp)
                    .padding(end = 8.dp)
            )
            Text(
                    text = stringResource(R.string.mode),
            color = MaterialTheme.colorScheme.onSecondary,
                style = MaterialTheme.typography.bodySmall
            )
            Switch(
                checked = false,
                onCheckedChange ={},
                enabled = true,
                modifier = Modifier.padding(start=8.dp),
                colors = SwitchDefaults.colors(
                    checkedThumbColor = MaterialTheme.colorScheme.onSecondary,
                    checkedTrackColor =  MaterialTheme.colorScheme.secondary,
                    uncheckedThumbColor =  MaterialTheme.colorScheme.primary,
                    uncheckedTrackColor =  MaterialTheme.colorScheme.onPrimary,
                )
            )
        }

        Row(//TODO:icon
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(8.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.icon),
                contentDescription = stringResource(R.string.close_session),
                modifier= Modifier.size(48.dp)
                    .padding(end = 8.dp)
            )
            Text(
                text = stringResource(R.string.close_session),
                color = MaterialTheme.colorScheme.onSecondary,
                style = MaterialTheme.typography.bodySmall
            )
        }

        Row(//TODO:icon
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(8.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.icon),
                contentDescription = stringResource(R.string.delete_user_data),
                modifier= Modifier.size(48.dp)
                    .padding(end = 8.dp)
            )
            Text(
                text = stringResource(R.string.delete_user_data),
                color = MaterialTheme.colorScheme.onSecondary,
                style = MaterialTheme.typography.bodySmall
            )
        }

        HorizontalDivider()

        Row(//TODO: getionar: enviar mail a info.letsgetcactus.com
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(8.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.icon),
                contentDescription = stringResource(R.string.contact),
                modifier= Modifier.size(48.dp)
                    .padding(end = 8.dp)
            )
            Text(
                text = stringResource(R.string.contact),
                color = MaterialTheme.colorScheme.onSecondary,
                style = MaterialTheme.typography.bodySmall
            )
        }

        Row( //TODO: falta screen
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(8.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.icon),
                contentDescription = stringResource(R.string.faq),
                modifier= Modifier.size(48.dp)
                    .padding(end = 8.dp)
            )
            Text(
                text = stringResource(R.string.faq),
                color = MaterialTheme.colorScheme.onSecondary,
                style = MaterialTheme.typography.bodySmall
            )
        }

    }




}



@Preview
@Composable
fun PreviewMenuDrawer(){
    CocinaConCatalinaTheme(darkTheme = false) {
        MenuDrawerComponent(modifier = Modifier, onNavigate = {})
    }
}