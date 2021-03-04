package com.drewrick.coinmarketapp.model.models

import com.google.gson.annotations.SerializedName

data class Response(
    @SerializedName("data")
    val result: ArrayList<Currency>,
    val status: Status
)