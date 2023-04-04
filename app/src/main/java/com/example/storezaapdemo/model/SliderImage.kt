package com.example.storezaapdemo.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class SliderImage {
    @SerializedName("users")
    @Expose
    var users: List<String>? = null

    @SerializedName("error")
    @Expose
    var error: String? = null
}
