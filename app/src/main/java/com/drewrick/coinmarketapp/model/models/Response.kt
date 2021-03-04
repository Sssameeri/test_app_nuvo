package com.drewrick.coinmarketapp.model.models

import com.google.gson.annotations.SerializedName

data class Response(
    @SerializedName("data")
    val result: List<Currency>,
    val status: Status
)