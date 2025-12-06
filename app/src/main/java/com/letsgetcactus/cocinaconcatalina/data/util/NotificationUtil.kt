package com.letsgetcactus.cocinaconcatalina.data.util

import android.app.Notification
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import androidx.core.app.NotificationCompat
import com.letsgetcactus.cocinaconcatalina.MainActivity
import com.letsgetcactus.cocinaconcatalina.R

object NotificationUtil {

    fun buildTimerNotification(context: Context, message: String): Notification {
        // Intent to open the app when clicking on th notification
        val openAppIntent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingOpen = PendingIntent.getActivity(
            context,
            0,
            openAppIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
            ?: RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)


        return NotificationCompat.Builder(context, "timer_channel") //Same Id from APPCLASS
            .setSmallIcon(R.drawable.timer_white)
            .setContentTitle("RING RING!!\uD83D\uDD14")
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setSound(alarmSound)
            .setContentIntent(pendingOpen)
            .build()
    }
}
