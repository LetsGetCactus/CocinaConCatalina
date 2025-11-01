package com.letsgetcactus.cocinaconcatalina.model

import com.letsgetcactus.cocinaconcatalina.model.enum.RoleEnum
import java.util.Date

data class User(
    val id: Int,
    var name: String,
    var email: String,
    var password: String,
    val resgisteredInDate: Date,
    var isActive: Boolean,
    val Role: RoleEnum = RoleEnum.USER,
    var favouritesRecipes: List<Int> = emptyList(),
    var modifiedRecipes: List<Recipe> =emptyList()
)
