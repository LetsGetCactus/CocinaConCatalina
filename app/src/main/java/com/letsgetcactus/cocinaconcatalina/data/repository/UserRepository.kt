package com.letsgetcactus.cocinaconcatalina.data.repository

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import com.letsgetcactus.cocinaconcatalina.data.FirebaseConnection
import com.letsgetcactus.cocinaconcatalina.model.Recipe
import com.letsgetcactus.cocinaconcatalina.model.User
import kotlinx.coroutines.tasks.await
import java.time.Instant

/**
 * This object will manage:
 * - login/logout - Login with Firebase Auth
 * - load/save user's profile
 * - register users
 * - manage user's favourite (ID) and modified recipes
 */
object UserRepository {

    private val firebaseAuth : FirebaseAuth = Firebase.auth

    //AUTH
    /**
     * Logs  the user into de app by FirebaseAuth
     * @param email : user's email
     * @param password: user's password
     * @return User object or null, whether the user is already loggedIn or not
     */
    suspend fun login(email: String, password: String): User? {
        return try {
            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            val userUid = result.user?.uid ?: return null
            getUserById(userUid)

        } catch (e: Exception) {
            Log.e("UserRepository", "Login error", e)
            null
        }
    }

    /**
     * Logout method for the User to sign out from it's session
     */
    fun logOut() {
        firebaseAuth.signOut()

    }

    /**
     * Registers a new User in Firestore by FirebaseAuth (generates an uid)
     * @param name: user's name
     * @param email: user's email
     * @param password: user's password that will be hashed to be securely saved into the db
     * @return a boolean describing if the User has been correctly saved or not
     */
    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun register(name: String, email: String, password: String): User? {
        return try {
            val authResult = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            val userUid = authResult.user?.uid ?: return null

            val newUser = User(
                id = userUid,
                name = name,
                email = email,
                password = "",
                registeredInDate = Instant.now().toString(),
                isActive = true,
                role = "USER",
                favouritesRecipes = emptyList(),
                modifiedRecipes = emptyList()
            )

            FirebaseConnection.saveUser(newUser)
            newUser
        } catch (e: Exception) {
            Log.i("UserRepository", "Register error $e")
            null
        }
    }

    /**
     * Gets user's data from FirebaseConnection
     * @param userId Id from the user
     * @return the User itself or null
     */
    suspend fun getUserById(userId: String): User?{
        return FirebaseConnection.getUserById(userId)
    }

    /**
     * To get Firebase's current user
     */
    fun getCurrentFirebaseUser(): FirebaseUser? {
        val user=firebaseAuth.currentUser
        if(user != null) {
            Log.i("UserRepository", "Obtained user from Firebase: ${user}")

            return user
        }else Log.i("UserRepository","Could not load FirebaseUser")
        return null
    }

    //FAVS RECIPES
    /**
     * To add a recipe into the DB (id)
     * @param userId Id from the user to add the recipe to his favourites subcollection
     */
    suspend fun addRecipeToFavourites(userId: String, recipeId: String) {
        FirebaseConnection.addRecipeToUsersFavorites(userId,recipeId)
        Log.i("UserRepository", "Added $recipeId to favs on user $userId")
    }


    /**
     * To remove a recipe from the user's fav DB (id)
     * @param userId Id from the user to remove the recipe from his favourites subcollection
     */
    suspend fun removeRecipeFromFavourites(userId: String, recipeId: String) {
        FirebaseConnection.removeUsersFavouriteRecipe(userId,recipeId)
        Log.i("UserRepository", "Removed ${recipeId }from favs on user $userId ")
    }


    /**
     * Gets all user's favourite Recipes ids and returns them intro Recipe objects
     * @param userId Id from the user to search for the recipes
     * @return a list of Recipes
     */
    suspend fun getAllFavouriteRecipeIds(userId: String): List<Recipe> {
       val favs= FirebaseConnection.getUserFavouriteRecipes(userId).map { it.id }
        Log.i("UserRepository", "Getting ${favs.size} recipes from user $userId")

        val favsToObjectRecipe = favs.mapNotNull {
            recipeId ->
            FirebaseConnection.getRecipeById(userId,recipeId)
        }


        return favsToObjectRecipe
    }


    //MODIFIED RECIPES
    /**
     * Uploads a modified recipe from the user to his subcollection on Firebase
     * @param userId Id from the user
     */
    suspend fun addModifiedRecipe(userId: String, recipe: Recipe){
        FirebaseConnection.saveUserModifiedRecipe(userId,recipe)
        Log.i("UserRepository","Saved ${recipe.title} on user: $userId")
    }


    /**
     * Gets all modified recipes from user
     * @param userId Id from the user to obtains his modified recipes from
     * @return a list of modified recipes by the user
     */
    suspend fun getAllModifiedRecipes(userId: String): List<Recipe>{
       val mods= FirebaseConnection.getUserModifiedRecipes(userId)
        Log.i("UserRepository", "Loaded ${mods.size} modified recipes for $userId")
        return mods
    }

  }