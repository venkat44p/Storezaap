package com.example.storezaapdemo.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class SliderImage {
    @SerializedName("users")
    @Expose
    val users: List<String>? = null

    @SerializedName("error")
    @Expose
    val error: String? = null

    @SerializedName("brand")
    @Expose
    val brand: List<String>? = null
}
