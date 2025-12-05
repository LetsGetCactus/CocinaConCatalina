package com.letsgetcactus.cocinaconcatalina.viewmodel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.letsgetcactus.cocinaconcatalina.R
import com.letsgetcactus.cocinaconcatalina.data.mapper.OriginMapper
import com.letsgetcactus.cocinaconcatalina.data.repository.UserRepository
import com.letsgetcactus.cocinaconcatalina.data.repository.UserSessionRepository
import com.letsgetcactus.cocinaconcatalina.data.searchFilters.RecipeFiltersEngine
import com.letsgetcactus.cocinaconcatalina.data.searchFilters.RecipeSearchFilters
import com.letsgetcactus.cocinaconcatalina.model.Recipe
import com.letsgetcactus.cocinaconcatalina.model.User
import com.letsgetcactus.cocinaconcatalina.model.enum.AllergenEnum
import com.letsgetcactus.cocinaconcatalina.model.enum.DificultyEnum
import com.letsgetcactus.cocinaconcatalina.model.enum.DishTypeEnum
import com.letsgetcactus.cocinaconcatalina.model.enum.OriginEnum
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * Controls login(), logout() and register()
 * Keeps the user's session (persistency)
 * Exposes Flows so the Composable can observe the user's state
 */
class UserViewModel(
    private val userRepo: UserRepository,
    private val userSessionRepo: UserSessionRepository
) : ViewModel() {


    //Preferences from DataStore
    private val _userId = MutableStateFlow<String?>(null)
    val userId: StateFlow<String?> = _userId

    private val _language = MutableStateFlow<String>("es")
    val language: StateFlow<String> = _language

    private val _theme = MutableStateFlow<String>("system")
    val theme: StateFlow<String> = _theme

    //user's session
    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn

    //Current user
    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser = _currentUser.asStateFlow()

    //For mod and favs Recipes
    private val _modifiedRecipes = MutableStateFlow<List<Recipe>>(emptyList())
    val modifiedRecipes = _modifiedRecipes.asStateFlow()

    private val _favouriteRecipes = MutableStateFlow<List<Recipe>>(emptyList())
    val favouriteRecipe = _favouriteRecipes.asStateFlow()

    // Favs filtered by chip
    private val _filteredFavourites = MutableStateFlow<List<Recipe>>(emptyList())
    val filteredFavourites = _filteredFavourites.asStateFlow()

    // Both user recipes (favs + mod)
    private val _userRecipes = MutableStateFlow<List<Recipe>>(emptyList())
    val userRecipes = _userRecipes.asStateFlow()

    // Filters and search
    private val _filteredUserRecipes = MutableStateFlow<List<Recipe>>(emptyList())
    val filteredUserRecipes = _filteredUserRecipes.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    // Active filters (all grouped together)
    private val _activeFilter = MutableStateFlow(RecipeSearchFilters())
    val activeFilter = _activeFilter.asStateFlow()

    //when the user select a recipe to be shown on itemrecipescreen
    private val _selectedRecipe = MutableStateFlow<Recipe?>(null)
    val selectedRecipe: StateFlow<Recipe?> = _selectedRecipe.asStateFlow()

    init {
        restoreSessionFromDataStore()

    }

    /**
     * For the user to persist. Loads the User and his favs and mod recipes
     * @param userId Id from the user to persist
     */
    suspend fun setUser(userId: String?) {
        if (userId != null) {
            val user = UserRepository.getUserById(userId)

            _currentUser.value = user
            loadFavourites()
            loadModified()

        } else {
            clearAllUserData()
        }
    }

    /**
     * Empties all data from the UserViewModel
     */
    private fun clearAllUserData() {
        _modifiedRecipes.value = emptyList()
        _favouriteRecipes.value = emptyList()
        _userRecipes.value = emptyList()
        _filteredUserRecipes.value = emptyList()
        _activeFilter.value = RecipeSearchFilters()
        _searchQuery.value = ""
        _currentUser.value = null
        _userId.value = null
    }


    /**
     * Login method to user UserRepository
     * @param email from the User
     * @param password from the User
     */
    suspend fun login(email: String, password: String): Boolean {

        val user = UserRepository.login(email, password)

        return if (user != null) {
            userSessionRepo.saveUserIdData(user.id)
            _currentUser.value = user
            _isLoggedIn.value = true
            loadFavourites()
            loadModified()

            true
        } else false
    }


    /**
     * When a user forgets his password
     * @param email to send the restore password
     * @param context for the Toast
     */
    suspend fun forgotPassword(email: String, context: Context) {
        if (email.isBlank()) {
            Toast.makeText(context, context.getString(R.string.emailError), Toast.LENGTH_SHORT)
                .show()
            return
        }
        val result = userRepo.handleForgetPassword(email)

        Log.i("UserViewModel", " Requested reset pass to URepo , status= $result")
        if (result) Toast.makeText(
            context,
            context.getString(R.string.chek_inbox),
            Toast.LENGTH_SHORT
        ).show()
        else Toast.makeText(
            context,
            context.getString(R.string.error_sending_to_inbox),
            Toast.LENGTH_SHORT
        ).show()


    }

    /**
     * Registers an user on Firestore by Firebase Authentication
     * @param name: user's name
     * @param email: user's email, it has to be unique
     * @param password: user's password
     * @return boolean whether the user has being successfully registered or not
     */
    suspend fun register(name: String, email: String, password: String): Boolean {
        val newUser = UserRepository.register(name, email, password)

        return if (newUser != null) {
            userSessionRepo.saveUserIdData(newUser.id)
            _currentUser.value = newUser
            _isLoggedIn.value = true

            true
        } else false
    }

    /**
     * Reads and restores user's session from the data saved in DataStore
     */
    private fun restoreSessionFromDataStore() {
        viewModelScope.launch {

            userSessionRepo.userIdFlow.collectLatest { id ->
                _userId.value = id
                _isLoggedIn.value = id != null

                if (id != null) {
                    val activeSessionInFirebaseAuth = userRepo.getCurrentFirebaseUser()
                    Log.i(
                        "UserViewModel",
                        "activeSessionInFirebaseAuth= $activeSessionInFirebaseAuth"
                    )
                    if (activeSessionInFirebaseAuth == null) {
                        //wait to retry
                        delay(400)
                        activeSessionInFirebaseAuth
                        Log.i(
                            "UserViewModel",
                            "activeSessionInFirebaseAuth retry= $activeSessionInFirebaseAuth"
                        )
                        if (activeSessionInFirebaseAuth == null)
                            userSessionRepo.clearUserSession()
                        clearAllUserData()
                        Log.i("UserViewModel", "cleared all user data")
                        _isLoggedIn.value = false
                    } else {
                        setUser(activeSessionInFirebaseAuth.uid)
                        Log.i(
                            "UserViewModel",
                            "activeSessionInFirebaseAuth= ${activeSessionInFirebaseAuth.uid}"
                        )
                    }
                } else setUser(id)
                Log.i("UserViewModel", "user obtained correctly")
            }
        }
        viewModelScope.launch {
            userSessionRepo.userLangFlow.collectLatest { lang ->
                if (lang != null) _language.value = lang
            }
        }

        viewModelScope.launch {
            userSessionRepo.userThemeFlow.collectLatest { theme ->
                if (theme != null) _theme.value = theme
            }
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
            userSessionRepo.clearUserSession()

            _userId.value = null
            _isLoggedIn.value = false
        }
    }

    /**
     * To call for deleting user's data in the whole app
     * @return True if user was correctly deleted, False if not
     */
    fun deleteUser(onSuccess: (Boolean) -> Unit = {}) {
        val userId = _currentUser.value?.id ?: run {
            onSuccess(false)
            return
        }

        viewModelScope.launch {
            try {
                val deleted = userRepo.deleteUserCompletely(userId)
                if (deleted) {
                    userRepo.logOut()
                    userSessionRepo.clearUserSession()

                    clearAllUserData()
                    _isLoggedIn.value = false

                    onSuccess(true)
                } else {
                    onSuccess(false)
                }
            } catch (e: Exception) {
                Log.e("UserViewModel", "Error deleting user", e)
                onSuccess(false)
            }
        }

    }

    //Updates for lang and theme
    /**
     * Saves a new language for the user
     * @param lang user's new selected language for the app
     */
    fun updateUserLanguage(lang: String) {
        viewModelScope.launch {
            userSessionRepo.saveLangData(lang)
            _language.value = lang
        }
    }

    /**
     * Saves a new theme for the user
     * @param theme selected by the user to be applied on the app
     */
    fun uploadUserTheme(theme: String) {
        viewModelScope.launch {
            userSessionRepo.saveThemeData(theme)
            _theme.value = theme
        }
    }

    //Methods for FAVs:
    /**
     * Loads user favourites subcollection
     * Gets the Recipe's object in the user's fav subcollection
     * Updates the StateFlow
     */
    suspend fun loadFavourites() {
        val user = _currentUser.value ?: return

        val favs = UserRepository.getAllFavouriteRecipeIds(user.id)
        _favouriteRecipes.value = favs.sortedBy { it.title }
        Log.i("UserViewModel", "Loaded ${favs.size} recipes from favourites: \n $favs")
    }


    /**
     * Updates a Recipe to user's favourites list
     * - If it exists: it is removed
     * - If it does not exist: it is saved
     * @param recipe to change from list
     */
    fun changeFavourite(recipe: Recipe) {
        val user = _currentUser.value ?: return

        viewModelScope.launch {
            val currentFavs = _favouriteRecipes.value.toMutableList()

            if (isFavourite(recipe.id)) {
                UserRepository.removeRecipeFromFavourites(user.id, recipe.id)
                currentFavs.removeAll { it.id == recipe.id }
                Log.i("UserViewModel", "Removed from favourites")
            } else {
                UserRepository.addRecipeToFavourites(user.id, recipe.id)
                currentFavs.add(recipe)
                Log.i("UserViewModel", "Saved in favourites")
            }

            _favouriteRecipes.value = currentFavs //Updates StateFLow
            allTogetherUserRecipes() //deberia ser loadFavorites?
        }


    }

    /**
     * Checks whether a recipe is in favs or not
     */
    fun isFavourite(recipeId: String): Boolean {
        return _favouriteRecipes.value.any { it.id == recipeId }
    }

    /**
     * For applying the filter by chips on FavouriteScreen
     * @param origin for the recipes to have for being shown
     */
    fun filterByChipOnFavourites(origin: OriginEnum?) {
        _filteredFavourites.value = if (origin == null) {
            _favouriteRecipes.value
        } else {
            _favouriteRecipes.value.filter { recipe ->
                val recipeOriginEnum = OriginMapper.mapOriginToEnum(recipe.origin.country)
                recipeOriginEnum == origin
            }
        }
    }

    //Methods for Modified
    /**
     * To upload a new modified recipe to user's subcollection
     * @param recipe for the new modified Recipe to save
     */
    suspend fun saveModifiedRecipe(recipe: Recipe) {
        val user = _currentUser.value?.id ?: return
        userRepo.addModifiedRecipe(user, recipe)

        //actualizamos ListRecipe sin duplicar
        val currentList = _modifiedRecipes.value.toMutableList()
        val index = currentList.indexOfFirst { it.id == recipe.id }

        if (index >= 0) {
            // Reemplazar receta existente
            currentList[index] = recipe
        } else {
            // Añadir nueva receta
            currentList.add(recipe)
        }

        _modifiedRecipes.value = currentList
        loadModified()

    }


    /**
     * To get all modified recipes from user's subcollection
     */
    private suspend fun loadModified() {
        val user = _currentUser.value?.id ?: return

        val modifiedRecipes = UserRepository.getAllModifiedRecipes(user)
        _modifiedRecipes.value = modifiedRecipes.sortedBy { it.title }
        Log.i("UserViewModel", "Loaded ${modifiedRecipes.size} recipes from modifiedRecipes")

    }

    /**
     * To select a favourite (favCard) recipe and show it on itemRecipeScreen
     */
    fun selectRecipe(recipe: Recipe) {
        _selectedRecipe.value = recipe
        Log.i("UserViewModel", "Selected recipe: ${recipe.title}")
    }

    /**
     * To delete a modified recipe
     * @param recipeId Id from the recipe to be deleted
     * @param userId Id from the owner of the modified recipe
     * @return boolean
     */
    suspend fun deleteModified(recipeId: String, userId: String?): Boolean {
        return try {
            if (!userId.isNullOrEmpty()) {
                userRepo.deleteModifiedRecipe(recipeId, userId)
                loadModified()
                true
            } else false
        } catch (e: Exception) {
            throw e
        }


    }


    //Both favs and modified
    private fun allTogetherUserRecipes() {
        val allUserRecipes = (_modifiedRecipes.value + _favouriteRecipes.value)
            .distinctBy { it.id }
            .sortedBy { it.title }

        _userRecipes.value = allUserRecipes
        filterRecipes()
        Log.i("UserViewModel", "Loaded ${allUserRecipes.size} recipes from users subcollection")
    }


//Filtering & Search. uses RecipeFiltersEngine
    /**
     * Established the filters to use on the FilterRecipes() method
     */
    fun setFilters(
        origin: OriginEnum?,
        dishType: DishTypeEnum?,
        difficulty: DificultyEnum?,
        prepTime: Int?,
        maxIngredients: Int?,
        rating: Int?,
        allergens: List<AllergenEnum>
    ) {
        _activeFilter.value = RecipeSearchFilters(
            origin = origin,
            dishType = dishType,
            difficulty = difficulty,
            prepTime = prepTime,
            maxIngredients = maxIngredients,
            rating = rating,
            allergens = allergens,
            query = ""
        )

        _searchQuery.value = ""
        filterRecipes()
        Log.i("UserViewModel", "Set filters in search")
    }

    /**
     * Sets the filters from setFilters() and looks for Recipes with them
     */
    private fun filterRecipes() {
        val result = RecipeFiltersEngine.applyFilters(
            recipes = _userRecipes.value,
            filter = _activeFilter.value
        ).sortedBy { it.title }

        _filteredUserRecipes.value = result
    }

    /**
     * For the SearchBar to find data
     * @query data to be searched for
     */
    fun search(query: String) {
        if (query.isNotBlank()) {
            _activeFilter.value = RecipeSearchFilters(query = query)
        } else {
            _activeFilter.value = RecipeSearchFilters()
        }

        _searchQuery.value = query
        filterRecipes()
    }

    /**
     * Resets/clears RecipeViewModel private filter vals to null
     */
    fun resetFilters() {
        _activeFilter.value = RecipeSearchFilters()
        _searchQuery.value = ""
        filterRecipes()
        Log.i("UserViewModel", "Reseting search filters")
    }


}