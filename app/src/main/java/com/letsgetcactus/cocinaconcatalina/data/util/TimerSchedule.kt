package com.letsgetcactus.cocinaconcatalina.data.util

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings
import android.util.Log

/**
 *
 */
object TimerScheduler {


    /**
     * Programs an AlarmManager that will trigger TimerReceiver in 'minutes'.
     */
    fun scheduleTimer(context: Context, minutes: Int) {
        val alarmManager = context.getSystemService(AlarmManager::class.java) as AlarmManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val canSchedule = alarmManager.canScheduleExactAlarms()

            if (!canSchedule) {
                Log.e("TIMER", "No permitido SCHEDULE_EXACT_ALARM")

                val intent = Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                context.startActivity(intent)

                return
            }
        }

        val triggerTime = System.currentTimeMillis() + minutes * 60 * 1000L

        val intent = Intent(context, TimerReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        try {
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                triggerTime,
                pendingIntent
            )
        } catch (e: SecurityException) {
            Log.e("TIMER", "SCHEDULE_EXACT_ALARM no permitido", e)
        }
    }
}
