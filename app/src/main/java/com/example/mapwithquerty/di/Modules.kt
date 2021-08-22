package com.example.mapwithquerty.di

import com.example.mapwithquerty.BuildConfig
import com.example.mapwithquerty.data.UsersRemoteService
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
object RetrofitModule {

    @Singleton
    @Provides
    fun getRetrofit(gsonConverterFactory: GsonConverterFactory): Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.API_URL)
        .addConverterFactory(gsonConverterFactory)
        .build()

    @Singleton
    @Provides
    fun getConverterFactory(gson: Gson) : GsonConverterFactory = GsonConverterFactory.create(gson)

    @Singleton
    @Provides
    fun getGson(): Gson = GsonBuilder().create()

    @Singleton
    @Provides
    fun getPersonRetrofitService(retrofit: Retrofit): UsersRemoteService = retrofit.create(UsersRemoteService::class.java)

}

@Module(subcomponents = [MainScreenComponent::class])
object SubcomponentsModule