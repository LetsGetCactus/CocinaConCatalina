package com.letsgetcactus.cocinaconcatalina.model

/**
 * The client.
 * User of the app and his data
 * @param role is USER by default and it can only be changed accessing directly the DB
 * @param favouritesRecipes is a list of the recipes selected by the user (just the name of them)
 * @param modifiedRecipes is a list of the recipes modified by the user, not available for others
 */
data class User(
    val id: String = "",
    val name: String = "",
    val email: String = "",
    val registeredInDate: String = "",
    val isActive: Boolean = true,
    val role: String = "USER",
    val favouritesRecipes: List<String> = emptyList(),
    val modifiedRecipes: List<String> = emptyList()
)

