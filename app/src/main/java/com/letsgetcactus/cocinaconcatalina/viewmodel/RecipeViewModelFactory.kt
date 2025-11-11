package com.letsgetcactus.cocinaconcatalina.viewmodel
//
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.ViewModelProvider
//
//class RecipeViewModelFactory(private val userId: String) :
//    ViewModelProvider.Factory {
//
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        if (modelClass.isAssignableFrom(RecipeViewModel::class.java)) {
//            return RecipeViewModel(userId) as T
//        }
//        throw IllegalArgumentException("Unknown ViewModel class")
//    }
//}
