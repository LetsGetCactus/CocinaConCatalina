package com.letsgetcactus.cocinaconcatalina

import android.app.Application
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.media.AudioAttributes
import androidx.core.net.toUri


/**
 * Application class - Class that only runs once on the apps init
 * Defines a global context for the whole up.
 * (needed for the timer to persist changes, going to background,etc)
 */
class ApplicationClass : Application() {
    override fun onCreate() {
        super.onCreate()

        //To volume app the sound of the alarm
        val soundUri = "android.resource://${packageName}/${R.raw.gong}".toUri()

        val attributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_ALARM)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()

        // Creates a notification channel - for the timer
        val channel = NotificationChannel(
            "timer_channel",
            "timer",
            NotificationManager.IMPORTANCE_HIGH

        ).apply {
            lockscreenVisibility = Notification.VISIBILITY_PUBLIC
            setSound(soundUri, attributes)
        }
        val manager = getSystemService(NotificationManager::class.java)
        manager.createNotificationChannel(channel)

    }
}

