package com.example.mapwithquerty.di

import com.example.mapwithquerty.ui.viewmodels.MainViewModel
import dagger.Component
import dagger.Subcomponent
import javax.inject.Singleton

@Singleton
@Component(modules = [RetrofitModule::class, SubcomponentsModule::class])
interface ApplicationComponent {
    fun mainScreenComponent(): MainScreenComponent.Factory
}

@MainScreenScope
@Subcomponent
interface MainScreenComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): MainScreenComponent
    }

    fun getViewModel(): MainViewModel
}
