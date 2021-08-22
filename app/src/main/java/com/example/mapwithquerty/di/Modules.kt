package com.example.mapwithquerty.di

import com.example.mapwithquerty.BuildConfig
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
object RetrofitModule {

    @Singleton
    @Provides
    fun getRetrofit(): Retrofit = Retrofit.Builder().baseUrl(BuildConfig.API_URL).build()

}

@Module(subcomponents = [MainScreenComponent::class])
object SubcomponentsModule