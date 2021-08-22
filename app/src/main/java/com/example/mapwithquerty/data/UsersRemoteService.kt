package com.example.mapwithquerty.data

import com.example.mapwithquerty.data.models.UsersResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface UsersRemoteService {

    @GET(".")
    fun getUsers(@Query("results") size: Int): Call<UsersResult>

}