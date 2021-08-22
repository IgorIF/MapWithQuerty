package com.example.mapwithquerty.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.example.mapwithquerty.Application
import com.example.mapwithquerty.navigation.Screen
import com.example.mapwithquerty.ui.screens.MainScreen
import com.example.mapwithquerty.ui.screens.MapScreen
import com.example.mapwithquerty.ui.screens.PersonScreen
import com.example.mapwithquerty.utils.daggerViewModel

@Composable
fun App() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.MAIN.route) {
        composable(Screen.MAIN.route) {
            val component = (LocalContext.current.applicationContext as Application).appComponent.mainScreenComponent().create()
            MainScreen(component)
        }
        composable(Screen.MAP.route) { MapScreen() }
        composable(Screen.PERSON.route) { PersonScreen() }

    }
}