package com.example.mapwithquerty.ui.screens

import androidx.compose.runtime.Composable
import com.example.mapwithquerty.data.models.User
import com.example.mapwithquerty.di.UserScreenComponent

@Composable
fun UserScreen(component: UserScreenComponent, user: User) {
    println(user)
}