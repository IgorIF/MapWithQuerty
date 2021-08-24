package com.example.mapwithquerty.data.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Coordinates(
    @SerializedName("latitude")
    var latitude: Double,

    @SerializedName("longitude")
    var longitude: Double
) : Serializable
