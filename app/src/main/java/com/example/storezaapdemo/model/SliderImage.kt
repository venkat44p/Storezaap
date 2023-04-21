package com.example.storezaapdemo.model

import com.google.gson.annotations.SerializedName

data class SliderImage(
    @SerializedName("status")
    val status: String,
    @SerializedName("data")
    val data: List<Datum>
)
