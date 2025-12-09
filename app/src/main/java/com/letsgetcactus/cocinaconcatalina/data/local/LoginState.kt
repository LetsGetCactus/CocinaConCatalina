package com.letsgetcactus.cocinaconcatalina.data.local

import com.letsgetcactus.cocinaconcatalina.model.User

/**
 * States for loging with Google Credentials
 */
sealed class LoginState {
    object Idle : LoginState()
    object Loading : LoginState()
    data class Success(val user: User) : LoginState()
    data class Error(val msg: String) : LoginState()
}