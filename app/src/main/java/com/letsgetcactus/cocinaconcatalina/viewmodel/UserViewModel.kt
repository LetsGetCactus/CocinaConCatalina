package com.letsgetcactus.cocinaconcatalina.viewmodel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.letsgetcactus.cocinaconcatalina.R
import com.letsgetcactus.cocinaconcatalina.data.local.LoginState
import com.letsgetcactus.cocinaconcatalina.data.mapper.OriginMapper
import com.letsgetcactus.cocinaconcatalina.data.repository.UserRepository
import com.letsgetcactus.cocinaconcatalina.data.repository.UserSessionRepository
import com.letsgetcactus.cocinaconcatalina.data.searchFilters.RecipeFiltersEngine
import com.letsgetcactus.cocinaconcatalina.data.searchFilters.RecipeSearchFilters
import com.letsgetcactus.cocinaconcatalina.model.Recipe
import com.letsgetcactus.cocinaconcatalina.model.User
import com.letsgetcactus.cocinaconcatalina.model.enum.OriginEnum
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Controls login(), logout() and register()
 * Keeps the user's session (persistency)
 * Exposes Flows so the Composable can observe the user's state
 */
class UserViewModel(
    private val userRepo: UserRepository,
    private val userSessionRepo: UserSessionRepository,
    private val recipeViewModel: RecipeViewModel
) : ViewModel() {



    //Preferences from DataStore
    private val _userId = MutableStateFlow<String?>(null)
    val userId: StateFlow<String?> = _userId

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

    //For Google
    private val _googleLoginState = MutableStateFlow<LoginState>(LoginState.Idle)
    val googleLoginState: StateFlow<LoginState> = _googleLoginState

    //For loading user's whole data
    private val _state = MutableStateFlow(UserState())
    val state: StateFlow<UserState> = _state

    init {
        restoreSession()
    }

    /**
     * Restores session if there's user's data in DataStoreManager
     */
    private fun restoreSession() {
        viewModelScope.launch {
            // Collects DataStore userId (UserSessionRepository exposes flows)
            userSessionRepo.userIdFlow.collect { id ->
                if (id.isNullOrEmpty()) {
                    // No session saved -> logged out
                    _state.value = UserState(isLoading = false)
                } else {
                    loadUserAndRecipesOrdered(id)
                }
            }
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
     fun login(email: String, password: String) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)

            try {
                val user = withContext(Dispatchers.IO) {
                    userRepo.login(email.trim(), password)
                }

                if (user == null) {
                    _state.value = UserState(
                        isLoading = false,
                        error = "Usuario o contraseña incorrectos"
                    )
                    return@launch
                }
                userSessionRepo.saveUserIdData(user.id)
                loadUserAndRecipesOrdered(user.id)
            } catch (e: Exception) {
                Log.e("UserViewModel", "login error", e)
                _state.value = UserState(isLoading = false, error = "Error al iniciar sesión")
            }
        }
    }

    /**
     * To log in or register with a Google account
     */
    fun logInWithGoogle(context: Context) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)

            try {
                val user = withContext(Dispatchers.IO) {
                    userRepo.loginWithGoogle(context)
                }

                if (user == null) {
                    _state.value = UserState(
                        isLoading = false,
                        error = "Error iniciando sesión con Google"
                    )
                    return@launch
                }

                userSessionRepo.saveUserIdData(user.id)
                loadUserAndRecipesOrdered(user.id)
            } catch (e: Exception) {
                Log.e("UserViewModel", "Google login error", e)
                _state.value = UserState(isLoading = false, error = "Error login Google")
            }
        }
    }


    /**
     * When a user forgets his password
     * @param email to send the restore password
     * @param context for the Toast
     */
     fun forgotPassword(email: String, context: Context) {
        if (email.isBlank()) {
            Toast.makeText(context, context.getString(R.string.emailError), Toast.LENGTH_SHORT)
                .show()
            return
        }
        viewModelScope.launch {
            val result = userRepo.handleForgetPassword(email)


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

    }

    /**
     * Registers an user on Firestore by Firebase Authentication
     * @param name: user's name
     * @param email: user's email, it has to be unique
     * @param password: user's password
     * @return boolean whether the user has being successfully registered or not
     */
     fun register(name: String, email: String, password: String) {
        viewModelScope.launch {
            val newUser = UserRepository.register(name, email, password)


            if (newUser != null) {
                userSessionRepo.saveUserIdData(newUser.id)
                _currentUser.value = newUser
                _isLoggedIn.value = true


            }
        }
    }

    /**
     * Loads data in this order:
     * - user
     * - favourite recipes
     * - modified recipes
     * - asian original recipes
     * - sets UserState to ready
     */
    private fun loadUserAndRecipesOrdered(userId: String) {
        viewModelScope.launch {
            // mark loading
            _state.value = UserState(isLoading = true)

            try {
                // 1) Load user
                val user = withContext(Dispatchers.IO) { userRepo.getUserById(userId) }
                if (user == null) {
                    // invalid saved session: clear
                    userSessionRepo.clearUserSession()
                    _state.value = UserState(isLoading = false, error = "Usuario no encontrado")
                    return@launch
                }
                _currentUser.value =user

                // 2) Load favourites
                val favs = withContext(Dispatchers.IO) { userRepo.getAllFavouriteRecipeIds(userId) }
                _favouriteRecipes.value =favs.sortedBy { it.title }

                // 3) Load modified
                val mods = withContext(Dispatchers.IO) { userRepo.getAllModifiedRecipes(userId) }
                _modifiedRecipes.value= mods.sortedBy { it.title }

                // 4) Load original recipes (single collection)
                // We flag originalRecipesLoaded when done (view models that rely on original recipes can read recipeRepo or their own viewmodel)
                withContext(Dispatchers.IO) {
                recipeViewModel.loadAsianOgRecipes() // we don't store them here necessarily, but we ensure they are loaded
                allTogetherUserRecipes()
                }

                // 5) Update state: user + favourites + modified + originals loaded flag
                _state.value = UserState(
                    isLoading = false,
                    user = user,
                    favouriteRecipes = _favouriteRecipes.value,
                    modifiedRecipes = _modifiedRecipes.value,
                    originalRecipesLoaded = true,
                    error = null
                )

            } catch (e: Exception) {
                Log.e("UserViewModel", "Error loading user and recipes", e)
                _state.value = UserState(isLoading = false, error = "Error cargando datos")
            }
        }
    }



    /**
     * Clears the users data stored on:
     *  - dataStore
     *  - currentUser (ViewModel)
     *  and gets the user off on UserRepository logout()
     */
    fun logout(context: Context) {
        viewModelScope.launch {
            try {
                userRepo.logOut(context)
            } catch (e: Exception) {
                Log.e("UserViewModel", "logout error", e)
            } finally {
                userSessionRepo.clearUserSession()
                clearAllUserData()
                _isLoggedIn.value=false
                _state.value = UserState(isLoading = false)
            }
        }
    }

    /**
     * To call for deleting user's data in the whole app
     * @return True if user was correctly deleted, False if not
     */
    fun deleteUser(onSuccess: (Boolean) -> Unit = {}, context: Context) {
        val userId = _currentUser.value?.id ?: run {
            onSuccess(false)
            return
        }

        viewModelScope.launch {
            try {
                val deleted = userRepo.deleteUserCompletely(userId)
                if (deleted) {
                    userRepo.logOut(context)
                    userSessionRepo.clearUserSession()

                    clearAllUserData()
                    _isLoggedIn.value = false

                    onSuccess(true)
                } else {
                    onSuccess(false)
                }
            } catch (e: Exception) {
                Log.e("UserViewModel", "Error deleting user $userId")
                onSuccess(false)
            }
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

            } else {
                UserRepository.addRecipeToFavourites(user.id, recipe.id)
                currentFavs.add(recipe)

            }

            _favouriteRecipes.value = currentFavs //Updates StateFLow
            allTogetherUserRecipes()
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
        _favouriteRecipes.value.filter { recipe ->
            val recipeOriginEnum = OriginMapper.mapOriginToEnum(recipe.origin.country)
            recipeOriginEnum == origin
        }

    }

    //Methods for Modified
    /**
     * To upload a new modified recipe to user's subcollection
     * @param recipe for the new modified Recipe to save
     */
     fun saveModifiedRecipe(recipe: Recipe) {
        viewModelScope.launch {
            val user = _currentUser.value?.id ?: return@launch
            userRepo.addModifiedRecipe(user, recipe)

            val currentList = _modifiedRecipes.value.toMutableList()
            val index = currentList.indexOfFirst { it.id == recipe.id }

            if (index >= 0) {
                currentList[index] = recipe
            } else {
                currentList.add(recipe)
            }

            _modifiedRecipes.value = currentList
            allTogetherUserRecipes()
        }
    }


    /**
     * To get all modified recipes from user's subcollection
     */
    private fun loadModified() {
        viewModelScope.launch {
            val user = _currentUser.value?.id ?: return@launch

            val modifiedRecipes = UserRepository.getAllModifiedRecipes(user)
            _modifiedRecipes.value = modifiedRecipes.sortedBy { it.title }
        }

    }

    /**
     * To select a favourite (favCard) recipe and show it on itemRecipeScreen
     */
    fun selectRecipe(recipe: Recipe) {
        _selectedRecipe.value = recipe

    }

    /**
     * To delete a modified recipe
     * @param recipeId Id from the recipe to be deleted
     * @param userId Id from the owner of the modified recipe
     * @return boolean
     */
     fun deleteModified(recipeId: String, userId: String?) {


        viewModelScope.launch {
            try {
                if (!userId.isNullOrEmpty()) {
                    userRepo.deleteModifiedRecipe(recipeId, userId)
                    loadModified()

                }
            } catch (e: Exception) {
                throw  e
            }

        }
    }


    //Both favs and modified
    private fun allTogetherUserRecipes() {
        val allUserRecipes = (_modifiedRecipes.value + _favouriteRecipes.value)
            .distinctBy { it.id }
            .sortedBy { it.title }

        _userRecipes.value = allUserRecipes
        filterRecipes()
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

    }

    /**
     * To rate a recipe on ItemRecipeScreen
     * It sums to the rating and shows de average rating from all the votes
     * @param id from the recipe who got the vote
     * @param rating int for the rating received
     */
    fun rateRecipe(id: String, rating: Int, userId: String) {
        viewModelScope.launch {
            userRepo.rateRecipe(id, rating, userId)
            loadModified()
        }

    }
}