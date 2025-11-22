package com.letsgetcactus.cocinaconcatalina.data.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Stores locally the necessary data to persist the user's preferences and session for:
 * - userId
 * - language
 * - theme
 */

private val Context.dataStore by preferencesDataStore(name = "user_prefs")

class DataStoreManagment(private val context: Context){

    //What we will store from the User
    private val USER_ID = stringPreferencesKey("user_id")
    private val USER_LANG= stringPreferencesKey("user_lang")
    private val USER_THEME= stringPreferencesKey("user_theme")

    //Getters
    val userIdFlow: Flow<String?> = context.dataStore.data.map {
        preferences -> preferences[USER_ID]
    }
    val userLangFlow: Flow<String?> = context.dataStore.data.map {
        preferences -> preferences[USER_LANG]
    }
    val userThemeFlow: Flow<String?> = context.dataStore.data.map {
        preferences -> preferences[USER_THEME]
    }

    //Setters
    suspend fun saveUserId(userId: String){
        context.dataStore.edit {
            prefs ->
            prefs[USER_ID] = userId
        }
    }
    suspend fun saveUserLang(language:String){
        context.dataStore.edit {
            prefs ->
            prefs[USER_LANG]= language
        }
    }
    suspend fun saveUserTheme(theme: String){
        context.dataStore.edit {
            prefs ->
            prefs[USER_THEME]= theme
        }
    }


    //To clear them all
    suspend fun clearAllUserData(){
        context.dataStore.edit { it.clear() }
    }
}