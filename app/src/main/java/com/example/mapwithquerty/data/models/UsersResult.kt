package com.example.mapwithquerty.data.models

import com.google.gson.annotations.SerializedName

data class UsersResult (
    @SerializedName("results")
    var userList: List<Results>
)