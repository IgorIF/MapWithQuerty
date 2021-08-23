package com.example.mapwithquerty.ui

import android.os.Bundle
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.navArgument
import com.example.mapwithquerty.Application
import com.example.mapwithquerty.data.models.User
import com.example.mapwithquerty.navigation.Screen
import com.example.mapwithquerty.ui.screens.MainScreen
import com.example.mapwithquerty.ui.screens.UserScreen
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

@ExperimentalAnimationApi
@Composable
fun App() {
    val navController = rememberAnimatedNavController()

    AnimatedNavHost(navController = navController, startDestination = Screen.MAIN.route) {
        composable(
            route = Screen.MAIN.route,
            enterTransition = null,
            exitTransition = null,
            popEnterTransition = null,
            popExitTransition = null
        ) { backStackEntry ->
            val component = (LocalContext.current.applicationContext as Application).appComponent.mainScreenComponent().create()
            MainScreen(component) {
                backStackEntry.arguments = Bundle().apply { putSerializable(Screen.USER.parameter, it) }
                navController.navigate(Screen.USER.route)
            }
        }
        composable(
            route = Screen.USER.route,
            arguments = listOf(navArgument(Screen.USER.parameter!!) { type = NavType.SerializableType(User::class.java)}),
        ) {
            val component = (LocalContext.current.applicationContext as Application).appComponent.userScreenComponent().create()
            val user = navController.previousBackStackEntry?.arguments?.getSerializable(Screen.USER.parameter)

            user?.let {
                UserScreen(component, user as User)
            }
        }
    }
}