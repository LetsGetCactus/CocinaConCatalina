package com.letsgetcactus.cocinaconcatalina.model.enum

/**
 * Base interface for enums that need and ID to translate them to be able to find their string on strings.xml
 * Centralizes the access to Strings based on enum types
 */
interface TranslatableEnum {
    val enumId: Int
}