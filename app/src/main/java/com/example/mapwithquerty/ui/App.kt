package com.example.mapwithquerty.ui

import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavOptions
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import com.example.mapwithquerty.Application
import com.example.mapwithquerty.data.models.User
import com.example.mapwithquerty.navigation.Screen
import com.example.mapwithquerty.ui.screens.MainScreen
import com.example.mapwithquerty.ui.screens.UserScreen
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

@Composable
fun App() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.MAIN.route) {
        composable(Screen.MAIN.route) { backStackEntry ->
            val component = (LocalContext.current.applicationContext as Application).appComponent.mainScreenComponent().create()
            MainScreen(component) {
                val userJson = toJson(component.getGson(), it)
                backStackEntry.arguments = Bundle().apply { putSerializable(Screen.USER.parameter, it) }
                navController.navigate(Screen.USER.route)
            }
        }
        composable(
            route = Screen.USER.route,
            arguments = listOf(navArgument(Screen.USER.parameter!!) { type = NavType.SerializableType(User::class.java)})
        ) {
            val component = (LocalContext.current.applicationContext as Application).appComponent.userScreenComponent().create()
            val user = navController.previousBackStackEntry?.arguments?.getSerializable(Screen.USER.parameter)

            user?.let {
                UserScreen(component, user as User)
            }
        }
    }
}

fun fromJson(gson: Gson, json: String): User {
    val type = object : TypeToken<User>() {}.type
    return gson.fromJson(json, type)
}

fun toJson(gson: Gson, user: User): String {
    val type = object : TypeToken<User>() {}.type
    return gson.toJson(user, type)
}