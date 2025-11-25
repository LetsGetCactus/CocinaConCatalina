package com.letsgetcactus.cocinaconcatalina.data.repository

import com.letsgetcactus.cocinaconcatalina.data.local.DataStoreManagment
import kotlinx.coroutines.flow.Flow

/**
 * Mediates between DataStore and the ViewModel to obtain user's session persistency
 * - calls DataStoreManagment for the data on it
 * - exposes simple functions to the UserViewModel
 */
class UserSessionRepository (
    private val dataStore: DataStoreManagment
){
    //DataStore user data flows
    val userIdFlow: Flow<String?> = dataStore.userIdFlow
    val userLangFlow: Flow<String?> = dataStore.userLangFlow
    val userThemeFlow: Flow<String?> = dataStore.userThemeFlow

    /**
     * Saves the data from the user (id) into DataStoreManagment
     */
    suspend fun saveUserIdData(id: String){
        dataStore.saveUserId(id)
    }
    /**
     * Saves the data form the language of the app on the user's session and stores it into DataStoreManagment
     */
    suspend fun saveLangData(language: String){
        dataStore.saveUserLang(language)
    }
    /**
     * Saves the user's preferred theme into DataStoreManagment
     */
    suspend fun saveThemeData(theme:String){
        dataStore.saveUserTheme(theme)
    }

    /**
     * Clears all the data from DataStore
     */
    suspend fun clearUserSession(){
        dataStore.clearAllUserData()
    }
}