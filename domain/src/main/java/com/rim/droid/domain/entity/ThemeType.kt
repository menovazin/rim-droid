package com.rim.droid.domain.entity

enum class ThemeType {
    LIGHT,
    DARK,
    SYSTEM;

    fun serialize(): String = name.lowercase()

    companion object {
        fun fromString(text: String?): ThemeType {
            if (text.isNullOrBlank()) return SYSTEM
            return when (text.lowercase()) {
                "light" -> LIGHT
                "dark" -> DARK
                "system" -> SYSTEM
                else -> SYSTEM
            }
        }
    }
}
