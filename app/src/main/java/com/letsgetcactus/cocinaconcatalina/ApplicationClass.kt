package com.letsgetcactus.cocinaconcatalina

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager


/**
 * Application class - Class that only runs once on the apps init
 * Defines a global context for the whole up.
 * (needed for the timer to persist changes, going to background,etc)
 */
class ApplicationClass : Application(){
    override fun onCreate() {
        super.onCreate()

        // Creates a notification channel - for the timer
        val channel = NotificationChannel(
            "timer_channel",
            "timer",
            NotificationManager.IMPORTANCE_HIGH
        )
        val manager = getSystemService(NotificationManager::class.java)
        manager.createNotificationChannel(channel)
    }
}

