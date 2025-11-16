package com.letsgetcactus.cocinaconcatalina.database

import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
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
            val userInFirestore= usersCollection.firestore.document(userId).get().await()
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
            val userId= result.user?.uid ?: return null

            getUserById(userId)
        }catch (e: Exception){
            e.printStackTrace()
            null
        }
    }

    /**
     * Logout method for the User to sign out from it's session
     *
     */
    fun logOut(){
        firebaseAuth.signOut()
    }

    /**
     * Registers a new User in Firestore
     * @param user: User object to save into Firestore
     * @param password: user's password that will be hashed to be securely saved into the db
     * @return a boolean describing if the User has been correctly saved or not
     */
    suspend fun register(user:User, password: String): Boolean{
        return try {
            val authResult = firebaseAuth.createUserWithEmailAndPassword(user.email, password).await()
            val userId = authResult.user?.uid ?: return false

            val newUser = user.copy(id = userId)

            usersCollection.document(userId).set(newUser).await()

            true
        }catch (e: Exception){
            e.printStackTrace()
            false

        }
    }
}