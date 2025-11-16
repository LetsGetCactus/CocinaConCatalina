package com.letsgetcactus.cocinaconcatalina.viewmodel

import android.app.Application
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.letsgetcactus.cocinaconcatalina.dataStore
import com.letsgetcactus.cocinaconcatalina.database.UserRepository
import com.letsgetcactus.cocinaconcatalina.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class UserViewModel (app: Application) : AndroidViewModel(app) {

    private val context = app

    //Preferences-keys from DataStore
    private val USER_ID = stringPreferencesKey("user_id")
    private val USER_NAME = stringPreferencesKey("user_name")
    private val USER_EMAIL = stringPreferencesKey("user_email")


    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser = _currentUser.asStateFlow()

    init {
        loadUserFromDataStore()
    }

    /**
     * If DataStore.kt has a user, it gives it back from Firestore
     */
    private fun loadUserFromDataStore() {
        viewModelScope.launch {
            context.dataStore.data.map { prefs ->
                prefs[USER_ID]
            }
                .collect { userId ->
                    if (userId != null) {
                        val userInDataStore = UserRepository.getUserById(userId)
                        _currentUser.value = userInDataStore
                    }
                }
        }
    }

    /**
     * Login method to user UserRepository
     * @param email from the User
     * @param password from the User
     */
    fun login(email: String, password: String): Boolean {
        var result = false
        viewModelScope.launch {
            val user = UserRepository.login(email, password)
            if (user != null) {
                saveInDataStore(user)
                _currentUser.value = user
                result = true
            }
        }
        return result
    }


    /**
     * Saves/updates the user in session on DataStore.kt for persistence
     * @param user User logged in the app
     */
    private suspend fun saveInDataStore(user: User){
        context.dataStore.edit {
            prefs ->
            prefs[USER_ID] = user.id
            prefs[USER_NAME] = user.name
            prefs[USER_EMAIL] = user.email
        }
    }


    /**
     * Clears the users data stored on:
     *  - dataStore
     *  - currentUser (ViewModel)
     *  and gets the user off on UserRepository logout()
     */
    fun logOut(){
        viewModelScope.launch {
            UserRepository.logOut()

            context.dataStore.edit {
                it.clear()
            }

            _currentUser.value=null

        }
    }

}