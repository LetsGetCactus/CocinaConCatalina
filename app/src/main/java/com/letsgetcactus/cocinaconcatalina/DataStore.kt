package com.letsgetcactus.cocinaconcatalina

import android.app.Application
import androidx.datastore.preferences.preferencesDataStore

//This val will be available everywhere
val Application.dataStore by preferencesDataStore("user_prefs")

/**
 * This class get an all-app-accessible DataStore obj for users session persistence
 */
class DataStore: Application(){
    override fun onCreate() {
        super.onCreate()
    }
}