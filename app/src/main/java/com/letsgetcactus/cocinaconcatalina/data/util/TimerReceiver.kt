package com.letsgetcactus.cocinaconcatalina.data.util

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import androidx.annotation.RequiresPermission
import androidx.core.app.NotificationManagerCompat
import com.letsgetcactus.cocinaconcatalina.R


/**
 * This class will automatically execute itself when the AlarmManager schedules an alarm
 * It will ring a sound and show a notification with a message
 */
class TimerReceiver : BroadcastReceiver() {
    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    override fun onReceive(context: Context, intent: Intent?) {

        //gong sound for the alarm
        val player = MediaPlayer.create(context, R.raw.gong)
        player?.start()

        val message = context.getString(R.string.time_over)

        // Builds the Notification
        val notification = NotificationUtil.buildTimerNotification(context, message)
        NotificationManagerCompat.from(context).notify((System.currentTimeMillis() % Int.MAX_VALUE).toInt(), notification)

        //Frees the player
        player?.setOnCompletionListener {
            it.release()
        }
    }
}
