package com.letsgetcactus.cocinaconcatalina.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.letsgetcactus.cocinaconcatalina.data.repository.RecipeRepository

class RecipeViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return RecipeViewModel(RecipeRepository) as T
    }
}
