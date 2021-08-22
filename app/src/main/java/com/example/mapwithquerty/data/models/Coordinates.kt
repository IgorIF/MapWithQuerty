package com.example.mapwithquerty.data.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Coordinates(
    @SerializedName("latitude")
    var latitude: String,

    @SerializedName("longitude")
    var longitude: String
) : Serializable
