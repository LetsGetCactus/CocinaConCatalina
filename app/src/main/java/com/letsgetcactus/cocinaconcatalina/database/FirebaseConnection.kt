package com.letsgetcactus.cocinaconcatalina.database

import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.letsgetcactus.cocinaconcatalina.model.Recipe
import com.letsgetcactus.cocinaconcatalina.database.dto.RecipeDto
import com.letsgetcactus.cocinaconcatalina.database.mapper.toRecipe
import kotlinx.coroutines.tasks.await

/**
 * Singleton to connect with Firebase DB
 * It also uses the dto and mappers to download correctly the types on from the DB into the app
 */
object FirebaseConnection {
    val db = Firebase.firestore

    /**
     * To obtain all asian recipes and translate Firebase's simple types into our data for the app
     */
    suspend fun getAsianOriginalRecipes(): List<Recipe> {
        return try {
            val result = db.collection("asianOriginalRecipes").get().await()

            val dto = result.toObjects(RecipeDto::class.java)
            val listRecipes = dto.map { it.toRecipe() } // This uses the RecipeMapper

            Log.i("FirebaseRepository", "Got ${listRecipes.size} asian recipes")
            listRecipes

        } catch (e: Exception) {
            Log.e("FirebaseRepository", "Error fetching recipes", e)
            emptyList()
        }
    }


}
