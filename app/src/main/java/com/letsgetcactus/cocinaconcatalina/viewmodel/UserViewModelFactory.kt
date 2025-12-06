package com.letsgetcactus.cocinaconcatalina.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.letsgetcactus.cocinaconcatalina.data.repository.UserRepository
import com.letsgetcactus.cocinaconcatalina.data.repository.UserSessionRepository

class UserViewModelFactory(
    private val userSessionRepo: UserSessionRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
            return UserViewModel(
                UserRepository,
                userSessionRepo
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
