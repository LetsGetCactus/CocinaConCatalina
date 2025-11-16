package com.letsgetcactus.cocinaconcatalina.model

import com.letsgetcactus.cocinaconcatalina.model.enum.RoleEnum
import java.util.Date

data class User(
    val id: String = "",
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val registeredInDate: String = "",
    val isActive: Boolean = true,
    val role: String = "USER",
    val favouritesRecipes: List<String> = emptyList(),
    val modifiedRecipes: List<String> = emptyList()
)

