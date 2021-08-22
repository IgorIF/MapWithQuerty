package com.example.mapwithquerty.ui.screens

import androidx.compose.runtime.Composable
import com.example.mapwithquerty.ui.viewmodels.MainViewModel


@Composable
fun MainScreen(viewModel: MainViewModel) {


println(viewModel.users.value)



}