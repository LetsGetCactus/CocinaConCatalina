package com.letsgetcactus.cocinaconcatalina.database

import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.letsgetcactus.cocinaconcatalina.model.Recipe
import com.letsgetcactus.cocinaconcatalina.model.User
import kotlinx.coroutines.tasks.await

/**
 * This object will manage:
 * - login/logout - Login with Firebase Auth
 * - load/save users with Firestore
 * - register users
 */
object UserRepository {

    private val firebaseAuth = Firebase.auth
    private val usersCollection= Firebase.firestore.collection("users")

    /**
     * Obtains the user from Firestore( val usersCollection) from its Id
     * @param userId stands for the user's id on Firestore
     * @return User object or null, depending if it exists/is previously saved in the DB
     */
    suspend fun getUserById(userId: String): User? {
        return try {
            val userInFirestore= usersCollection.document(userId).get().await()
            userInFirestore.toObject(User::class.java)
        }catch (e: Exception){
            e.printStackTrace()
            null
        }
    }


    /**
     * Logs  the user into de app by FirebaseAuth
     * @param email : user's email
     * @param password: user's password
     * @return User object or null, whether the user is already loggedIn or not
     */
    suspend fun login(email: String, password: String) : User? {
        return  try {
            val result= firebaseAuth.signInWithEmailAndPassword(email,password).await()
            val userUid= result.user?.uid ?: return null

            getUserById(userUid)
        }catch (e: Exception){
            e.printStackTrace()
            null
        }
    }

    /**
     * Logout method for the User to sign out from it's session
     */
    fun logOut(){
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
                registeredInDate = java.time.Instant.now().toString(),
                isActive = true,
                role = "USER",
                favouritesRecipes = emptyList(),
                modifiedRecipes = emptyList()
            )

            usersCollection.document(userUid).set(newUser).await()

            newUser
        } catch (e: Exception) {
            e.printStackTrace()
            Log.i("UserRepository", "Error al registrar ${e}")
            null
        }
    }

    //FavsRecipe's methods to set and get user's favourites recipes
    suspend fun addRecipeToFavourites(userId: String, recipe: Recipe) {
        val fav = usersCollection.document(userId).collection("favouritesRecipes").document(recipe.id)
        fav.set(recipe).await()
    }

    suspend fun removeRecipeFromFavourites(userId: String, recipeId: String) {
        val fav = usersCollection.document(userId).collection("favouritesRecipes").document(recipeId)
        fav.delete().await()
    }

    suspend fun getFavouriteRecipeIds(userId: String): List<String> {
        val listFavs = usersCollection.document(userId).collection("favouritesRecipes").get().await()
        return listFavs.documents.map { it.id }
    }
}