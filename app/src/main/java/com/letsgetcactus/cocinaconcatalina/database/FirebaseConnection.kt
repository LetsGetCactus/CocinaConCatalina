package com.letsgetcactus.cocinaconcatalina.model.database

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.functions.FirebaseFunctions
import com.letsgetcactus.cocinaconcatalina.database.dto.RecipeDto
import com.letsgetcactus.cocinaconcatalina.model.Recipe
import com.letsgetcactus.cocinaconcatalina.model.database.mapper.toMap
import com.letsgetcactus.cocinaconcatalina.model.database.mapper.toRecipe
import kotlinx.coroutines.tasks.await
import java.util.Locale
import kotlin.collections.map


/**
 * Singleton to connect with Firebase DB
 * It also uses the dto and mappers to download correctly the types on from the DB into the app
 */
object FirebaseConnection {

    /**
     * To obtain all asian recipes and translate Firebase's simple types into our data for the app
     */
    suspend fun getAsianOriginalRecipes(): List<Recipe> {
        return try {
            val result = Firebase.firestore.collection("asianOriginalRecipes").get().await()
            val dto = result.toObjects(RecipeDto::class.java)

            // Get the apps language
            // If it's not determined, use system instead , if system not supported, use english
            val supportedLanguages= listOf("es","en","gl")
            var language= Locale.getDefault().language
            if(language !in supportedLanguages) language="en"

            //Passes languade to mapper
            val listRecipes = dto.map { it.toRecipe(language) } // This uses the RecipeMapper


            Log.i("FirebaseConnection", "Got ${listRecipes.size} recipes in $language")
            listRecipes

        } catch (e: Exception) {
            Log.e("FirebaseRepository", "Error fetching recipes", e)
            emptyList()
        }
    }


    /**
     * To upload to Firebase a recipe
     * Google Cloud Traslation API will translate it
     */
    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun uploadRecipeAndTranslations(recipe: Recipe, originalLanguage: String? = null): Boolean {
        return try {
            val functions = FirebaseFunctions.getInstance("europe-southwest1")


            // Si no se pasa idiomaOriginal, lo detectamos del dispositivo
            val language = originalLanguage ?: Locale.getDefault().language

            val data = mapOf(
                "receta" to recipe.toMap(),
                "idiomaOriginal" to language
            )

            // Llamada a la Cloud Function onCall
            val result = functions
                .getHttpsCallable("traducirReceta")
                .call(data)
                .await()


            // result.data viene de la función Node, que devuelve { success: true/false, receta: ... }
            val response = result.data as? Map<*, *>
            val success = response?.get("success") as? Boolean?: false

            if (!success) {
                Log.e("FirebaseConnection", "Cloud Function traductor devolvió error: ${response?.get("error")}")
            } else {
                Log.i("FirebaseConnection", "Receta traducida y guardada por la función")
            }

            success
        } catch (e: Exception) {
            Log.e("FirebaseConnection", "Error uploading/translating recipe: ${e.message}", e)
            false
        }
    }
}
