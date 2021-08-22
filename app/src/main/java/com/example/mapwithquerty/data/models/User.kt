package com.example.mapwithquerty.data.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class User (
    @SerializedName("gender")
    var gender: String,

    @SerializedName("name")
    var name: Name,

    @SerializedName("location")
    var location: Location,

    @SerializedName("email")
    var email: String,

    @SerializedName("dob")
    var dob: Dob,

    @SerializedName("picture")
    var picture: Picture

    ): Serializable