package com.letsgetcactus.cocinaconcatalina.data.repository

import com.letsgetcactus.cocinaconcatalina.data.local.DataStoreManagment
import kotlinx.coroutines.flow.Flow

/**
 * Mediates between DataStore and the ViewModel to obtain user's session persistency
 * - calls DataStoreManagement for the data on it
 * - exposes simple functions to the UserViewModel
 */
class UserSessionRepository (
    private val dataStore: DataStoreManagment
){
    //DataStore user data flows
    val userIdFlow: Flow<String?> = dataStore.userIdFlow
    val userThemeFlow: Flow<String?> = dataStore.userThemeFlow

    /**
     * Saves the data from the user (id) into DataStoreManagement
     */
    suspend fun saveUserIdData(id: String){
        dataStore.saveUserId(id)
    }


    /**
     * Saves the user's preferred theme into DataStoreManagement
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