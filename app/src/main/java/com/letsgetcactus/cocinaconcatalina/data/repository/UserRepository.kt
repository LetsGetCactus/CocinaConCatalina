package com.letsgetcactus.cocinaconcatalina.data.repository

import android.content.Context
import android.util.Log
import androidx.credentials.ClearCredentialStateRequest
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialException
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import com.letsgetcactus.cocinaconcatalina.R
import com.letsgetcactus.cocinaconcatalina.data.FirebaseConnection
import com.letsgetcactus.cocinaconcatalina.model.Recipe
import com.letsgetcactus.cocinaconcatalina.model.User
import kotlinx.coroutines.tasks.await
import java.time.Instant


/**
 * This object will  interact between the UserViewModel and Firebase,
 * also will manage:
 * - login/logout - Login with Firebase Auth
 * - load/save user's profile
 * - register users
 * - manage user's favourite(ID) and modified recipes
 */
object UserRepository {

    private val firebaseAuth: FirebaseAuth = Firebase.auth

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
     * To log/register with Google Credentials
     */
    suspend fun loginWithGoogle(context: Context): User? {
        return try {
            val credentialManager = CredentialManager.create(context)

            val googleIdOption = GetGoogleIdOption.Builder()
                .setServerClientId(context.getString(R.string.default_web_client_id))
                .setFilterByAuthorizedAccounts(false)
                .setAutoSelectEnabled(false)
                .build()


            val request = GetCredentialRequest.Builder()
                .addCredentialOption(googleIdOption)
                .build()


            val response = credentialManager.getCredential(
                context = context,
                request = request
            )

            val credential = response.credential


            val googleCredential =
                if (credential is CustomCredential &&
                    credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
                ) {
                    GoogleIdTokenCredential.createFrom(credential.data)
                } else {
                    Log.e("UserRepository", "Unexpected credential type: ${credential.type}")
                    return null
                }

            val idToken = googleCredential.idToken
            val firebaseCredential = GoogleAuthProvider.getCredential(idToken, null)

            val authResult = firebaseAuth.signInWithCredential(firebaseCredential).await()
            val firebaseUser = authResult.user ?: return null
            val userId = firebaseUser.uid

            val user = getUserById(userId)

            if (user != null) {
                user
            } else {
                val newUser = User(
                    id = userId,
                    name = firebaseUser.displayName ?: "unknown",
                    email = firebaseUser.email ?: "unknown",
                    registeredInDate = Instant.now().toString(),
                    isActive = true,
                    role = "USER",
                    favouritesRecipes = emptyList(),
                    modifiedRecipes = emptyList()
                )
                FirebaseConnection.saveUser(newUser)
                newUser
            }

        } catch (e: GetCredentialException) {
            Log.e("UserRepository", "Credential Manager error", e)
            null
        } catch (e: Exception) {
            Log.e("UserRepository", "Google login error", e)
            null
        }
    }


    /**
     * Logout method for the User to sign out from it's session
     */
    suspend fun logOut(context: Context) {
        firebaseAuth.signOut()
        try {
            val credentialManager = CredentialManager.create(context)
            val clearRequest = ClearCredentialStateRequest()
            credentialManager.clearCredentialState(clearRequest)


        } catch (e: Exception) {
            Log.e("UserRepository", "Error clearing Google credentials", e)
        }

    }

    /**
     * To get helo from Firebase Auth whenever the user forgets his password
     * @param email from the user to get the help
     * @return true or false whether the user could obtain a new password or not
     */
    suspend fun handleForgetPassword(email: String): Boolean{
        val result= FirebaseConnection.getUserForgottenPassword(email)

        return result
    }

    /**
     * Registers a new User in Firestore by FirebaseAuth (generates an uid)
     * @param name: user's name
     * @param email: user's email
     * @param password: user's password that will be hashed to be securely saved into the db
     * @return a boolean describing if the User has been correctly saved or not
     */
    suspend fun register(name: String, email: String, password: String): User? {
        return try {
            val authResult = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            val userUid = authResult.user?.uid ?: return null

            val newUser = User(
                id = userUid,
                name = name,
                email = email,
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
    suspend fun getUserById(userId: String): User? {
        return FirebaseConnection.getUserById(userId)
    }

    /**
     * To get Firebase's current user
     */
    fun getCurrentFirebaseUser(): FirebaseUser? {
        val user = firebaseAuth.currentUser
        if (user != null) {


            return user
        }
        return null
    }

    /**
     * Calls FirebaseConnection to delete all user data
     * @return True if it deleted all data, false if  not
     */
    suspend fun deleteUserCompletely(userId: String): Boolean{
        return try {
            FirebaseConnection.deleteUserAccount(userId)

            true
        }catch (e: Exception){
            Log.i("UserRepository","Could not delete all user's data from user $userId",e)
            false

        }
    }

    //FAVS RECIPES
    /**
     * To add a recipe into the DB (id)
     * @param userId Id from the user to add the recipe to his favourites subcollection
     */
    suspend fun addRecipeToFavourites(userId: String, recipeId: String) {
        FirebaseConnection.addRecipeToUsersFavorites(userId, recipeId)

    }


    /**
     * To remove a recipe from the user's fav DB (id)
     * @param userId Id from the user to remove the recipe from his favourites subcollection
     */
    suspend fun removeRecipeFromFavourites(userId: String, recipeId: String) {
        FirebaseConnection.removeUsersFavouriteRecipe(userId, recipeId)

    }


    /**
     * Gets all user's favourite Recipes ids and returns them intro Recipe objects
     * @param userId Id from the user to search for the recipes
     * @return a list of Recipes
     */
    suspend fun getAllFavouriteRecipeIds(userId: String): List<Recipe> {
        val favs = FirebaseConnection.getUserFavouriteRecipes(userId).map { it.id }


        val favsToObjectRecipe = favs.mapNotNull { recipeId ->
            FirebaseConnection.getRecipeById(userId, recipeId)
        }


        return favsToObjectRecipe
    }


    //MODIFIED RECIPES
    /**
     * Uploads a modified recipe from the user to his subcollection on Firebase
     * @param userId Id from the user
     */
    suspend fun addModifiedRecipe(userId: String, recipe: Recipe) {
        //Add (Mod) to title
        val modTitle = if (recipe.title.trim()
                .endsWith("(Mod)", ignoreCase = true)
        ) recipe.title else recipe.title + "(Mod)"
        recipe.title = modTitle

        FirebaseConnection.addModifiedRecipe(userId, recipe)

    }


    /**
     * Gets all modified recipes from user
     * @param userId Id from the user to obtains his modified recipes from
     * @return a list of modified recipes by the user
     */
    suspend fun getAllModifiedRecipes(userId: String): List<Recipe> {
        val mods = FirebaseConnection.getUserModifiedRecipes(userId)

        return mods
    }

    /**
     * Removes a modified recipe from user's subcollection
     * @param recipeId Id from the recipe to be deleted
     * @param userId Id from the user that wants to remove his recipe
     * @return a bool whether the recipe was correctly deleted or not
     */
    suspend fun deleteModifiedRecipe(recipeId: String, userId: String){
        FirebaseConnection.removeModifiedFromUser(recipeId,userId)
    }

    /**
     * To rate a recipe on ItemRecipeScreen
     * It sums to the rating and shows de average rating from all the votes
     * @param recipeId from the recipe who got the vote
     * @param rating int for the rating received
     */
    suspend fun rateRecipe(recipeId: String, rating: Int,userId: String ){
        FirebaseConnection.rateRecipe(recipeId,rating,userId)
    }
}