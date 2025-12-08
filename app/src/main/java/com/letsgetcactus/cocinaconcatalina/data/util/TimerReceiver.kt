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
 *
 */
class TimerReceiver : BroadcastReceiver() {
    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    override fun onReceive(context: Context, intent: Intent?) {

        val player = MediaPlayer.create(context, R.raw.gong)
        player?.start()

        val message = context.getString(R.string.time_over)

        // Handles the Notification (NotificationUtil manages NotificationCompat.Builder)
        val notification = NotificationUtil.buildTimerNotification(context, message)
        NotificationManagerCompat.from(context).notify((System.currentTimeMillis() % Int.MAX_VALUE).toInt(), notification)

        player?.setOnCompletionListener {
            it.release()
        }
    }
}
