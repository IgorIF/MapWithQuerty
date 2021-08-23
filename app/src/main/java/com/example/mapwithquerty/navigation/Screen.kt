package com.example.mapwithquerty.navigation

enum class Screen(val route: String, val parameter: String? = null) {
    MAIN("main"),
    USER("user", parameter = "user")
}