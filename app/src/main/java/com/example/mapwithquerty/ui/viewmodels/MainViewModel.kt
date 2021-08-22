package com.example.mapwithquerty.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.example.mapwithquerty.data.PersonsRepository
import com.example.mapwithquerty.di.MainScreenScope
import javax.inject.Inject

@MainScreenScope
class MainViewModel @Inject constructor(
    val personsRepository: PersonsRepository
): ViewModel() {
}