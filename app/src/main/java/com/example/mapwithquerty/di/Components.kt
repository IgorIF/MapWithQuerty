package com.example.mapwithquerty.di

import com.example.mapwithquerty.ui.viewmodels.MainViewModel
import com.example.mapwithquerty.ui.viewmodels.UserViewModel
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import dagger.Component
import dagger.Subcomponent
import javax.inject.Singleton

@Singleton
@Component(modules = [RetrofitModule::class, SubcomponentsModule::class])
interface ApplicationComponent {
    fun mainScreenComponent(): MainScreenComponent.Factory
    fun userScreenComponent(): UserScreenComponent.Factory
}

@MainScreenScope
@Subcomponent
interface MainScreenComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): MainScreenComponent
    }

    fun getViewModel(): MainViewModel
    fun getPicasso(): Picasso
    fun getGson(): Gson
}

@UserScreenScope
@Subcomponent
interface UserScreenComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): UserScreenComponent
    }

    fun getViewModel(): UserViewModel
    fun getPicasso(): Picasso
    fun getGson(): Gson
}




