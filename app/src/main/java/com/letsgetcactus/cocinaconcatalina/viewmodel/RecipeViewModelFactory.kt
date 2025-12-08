package com.letsgetcactus.cocinaconcatalina.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.letsgetcactus.cocinaconcatalina.data.repository.RecipeRepository

/**
 * Factory for the RecipeViewModel to be able to create instances of the RecipeViewModel
 * As it needs a RecipeRepository on it's constructor, we'll need the Factory
 */
class RecipeViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return RecipeViewModel(RecipeRepository) as T
    }
}
