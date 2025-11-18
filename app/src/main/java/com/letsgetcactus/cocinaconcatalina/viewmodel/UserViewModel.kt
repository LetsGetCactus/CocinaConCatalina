package com.letsgetcactus.cocinaconcatalina.viewmodel

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.letsgetcactus.cocinaconcatalina.dataStore
import com.letsgetcactus.cocinaconcatalina.database.RecipeRepository
import com.letsgetcactus.cocinaconcatalina.database.UserRepository
import com.letsgetcactus.cocinaconcatalina.model.Recipe
import com.letsgetcactus.cocinaconcatalina.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class UserViewModel(app: Application) : AndroidViewModel(app) {

    private val context = app

    //Preferences-keys from DataStore
    private val USER_ID = stringPreferencesKey("user_id")

    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser = _currentUser.asStateFlow()

    //For favs Recipes
    private val _favouriteRecipes= MutableStateFlow<List<Recipe>>(emptyList())
    val favouriteRecipe= _favouriteRecipes.asStateFlow()

    init {
        loadUserFromDataStore()
    }

    /**
     * If DataStore has an user, it gives it back from Firestore
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
                        loadFavourites()
                    } else {
                        _currentUser.value = null
                        _favouriteRecipes.value = emptyList()
                    }
                }
        }
    }

    /**
     * Login method to user UserRepository
     * @param email from the User
     * @param password from the User
     */
    suspend fun login(email: String, password: String): Boolean {

        val user = UserRepository.login(email, password)

        if (user != null) {
            saveInDataStore(user)

            _currentUser.value = user
            return true

        } else {
            return false
        }
    }


    /**
     * Registers an user on Firestore by Firebase Authentication
     * @param name: user's name
     * @param email: user's email, it has to be unique
     * @param password: user's password
     * @return boolean whether the user has being succesfully registered or not
     */
    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun register(name: String, email: String, password: String): Boolean {
        val newUser = UserRepository.register(name, email, password)
        if (newUser != null) {
            saveInDataStore(newUser)

            _currentUser.value = newUser
            return true
        } else {
            return false
        }
    }

    /**
     * Saves/updates the user in session on DataStore.kt for persistence
     * @param user User logged in the app
     */
    private suspend fun saveInDataStore(user: User) {
        context.dataStore.edit { prefs ->
            prefs[USER_ID] = user.id
        }
    }


    /**
     * Clears the users data stored on:
     *  - dataStore
     *  - currentUser (ViewModel)
     *  and gets the user off on UserRepository logout()
     */
    fun logOut() {
        viewModelScope.launch {
            UserRepository.logOut()

            context.dataStore.edit {
                it.clear()
            }

            _currentUser.value = null

        }
    }

    //Methods for FAVs:
    /**
     * Loads user favourites subcollection
     * Gets the Recipe's object in the user's fav subcollection
     * Updates the StateFlow
     */
    fun loadFavourites(){
        val user= _currentUser.value ?: return

        viewModelScope.launch {
            val favs= UserRepository.getFavouriteRecipeIds(user.id)

            val favRecipes = favs.mapNotNull {
                recipeId ->
                RecipeRepository.getRecipeById(recipeId)
            }

            _favouriteRecipes.value = favRecipes

      }

    }

    /**
     * Updates a Recipe to user's favourites list
     * - If it exists: it is removed
     * - If it does not exist: it is saved
     * @param recipe to change from list
     */
    fun changeFavourite(recipe: Recipe){
        val user=_currentUser.value ?: return

        viewModelScope.launch {
            val currentFavs= _favouriteRecipes.value.toMutableList()

            if(isFavourite(recipe.id)){
                UserRepository.removeRecipeFromFavourites(user.id, recipe.id)
                currentFavs.removeAll {it.id == recipe.id}
            }else{
                UserRepository.addRecipeToFavourites(user.id, recipe)
                currentFavs.add(recipe)
            }

            _favouriteRecipes.value=currentFavs //Updates StateFLow
        }
    }

    /**
     * Checks whether a recipe is in favs or not
     */
    fun isFavourite(recipeId: String): Boolean{
        return _favouriteRecipes.value.any { it.id == recipeId }
    }

}