package com.example.storezaapdemo.model

import com.google.gson.annotations.SerializedName

data class Datum(
    @SerializedName("id")
    val id: Int,
    @SerializedName("image")
    val image: String,
    @SerializedName("p_id")
    val pId: Int
)
