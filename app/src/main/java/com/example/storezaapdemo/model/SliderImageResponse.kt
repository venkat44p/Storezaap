package com.example.storezaapdemo.model

import com.google.gson.annotations.SerializedName

data class SliderImageResponse(
    @SerializedName("slider") val slider: List<String>,
    @SerializedName("error") val error: String?,
    @SerializedName("brand") val brand: List<String>
)
