package com.example.mapwithquerty.data

import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PersonsRemoteDataSource @Inject constructor(
    val retrofit: Retrofit
) {
}