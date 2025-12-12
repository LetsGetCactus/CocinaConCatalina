package com.letsgetcactus.cocinaconcatalina.ui.components.bars

import android.content.Intent
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import com.letsgetcactus.cocinaconcatalina.R
import com.letsgetcactus.cocinaconcatalina.data.util.TimerScheduler
import com.letsgetcactus.cocinaconcatalina.ui.NavigationRoutes

/**
 * Composable for the bar at the bottom of the Screen
 * It contains:
 * - Spotify
 * - Timer
 * - Favs
 * - Home
 *
 * @param onNavigate function to navigate in between Screens
 */
@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun BottomBarComposable(
    onNavigate: (String) -> Unit,
) {

    val context = LocalContext.current

    //For the AlerDialog for Timer to be shown or not
    var timerDialog by remember { mutableStateOf(false) }
    var minutes by remember { mutableStateOf("") }


    BottomAppBar(
        modifier = Modifier.fillMaxWidth(),
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Spotify
            BottomBarItem(
                icon = R.drawable.spotify,
                label = stringResource(R.string.spotify),
                onClick = {
                    val intent = Intent(Intent.ACTION_VIEW, "spotify:".toUri())
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

                    val packageManager = context.packageManager

                    if (intent.resolveActivity(packageManager) != null) {
                        context.startActivity(intent)
                    } else {
                        val playStoreIntent = Intent(
                            Intent.ACTION_VIEW,
                            "market://details?id=com.spotify.music".toUri()
                        )
                        playStoreIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        context.startActivity(playStoreIntent)
                    }
                }
            )

            // Timer
            BottomBarItem(
                icon = R.drawable.timer,
                label = stringResource(R.string.timer),
                onClick = {
                    timerDialog = true
                }
            )


            // Favs
            BottomBarItem(
                icon = R.drawable.favs,
                label = stringResource(R.string.favs),
                onClick = { onNavigate(NavigationRoutes.FAVS_SCREEN) }
            )

            // Home
            BottomBarItem(
                icon = R.drawable.tori_gate,
                label = stringResource(R.string.home),
                onClick = { onNavigate(NavigationRoutes.HOME_SCREEN) }
            )
        }

        if (timerDialog) {
            AlertDialog(
                onDismissRequest = { timerDialog = false },
                title = { Text(stringResource(R.string.set_timer)) },
                text = {
                    Column {
                        TextField(
                            value = minutes,
                            onValueChange = { minutes = it },
                            placeholder = { Text(stringResource(R.string.set_minutes)) },
                            singleLine = true
                        )
                    }
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            val m = minutes.toIntOrNull() ?: 0
                            if(m == 0){
                                Toast.makeText(context, context.getString(R.string.timer_not_set) ,Toast.LENGTH_SHORT).show()
                            }
                            if (m > 0) {
                                TimerScheduler.scheduleTimer(
                                    context.applicationContext,
                                    m
                                )
                            }
                            timerDialog = false
                            Toast.makeText(context,"${context.getString(R.string.timer_set)} $m ${context.getString(R.string.minutes)}", Toast.LENGTH_SHORT).show()
                        }
                    ) {
                        Text(stringResource(R.string.ok))
                    }
                },
                dismissButton = {
                    TextButton(onClick = { timerDialog = false }) {
                        Text(stringResource(R.string.cancel))
                    }
                }
            )
        }
    }
}


/**
 * Individual element of the Bottom bar
 * Icon + Text, both clickable
 */
@Composable
private fun BottomBarItem(
    icon: Int,
    label: String,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .clickable { onClick() }
            .padding(vertical = 6.dp),
        verticalArrangement = Arrangement.spacedBy(2.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(icon),
            contentDescription = label,
            modifier = Modifier
                .padding(bottom = 2.dp)
                .size(32.dp)
        )
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onPrimary
        )
    }

}
