package com.drewrick.coinmarketapp.model.models

data class USD(
    val last_updated: String,
    val market_cap: Double,
    val percent_change_1h: Double,
    val percent_change_24h: Double,
    val percent_change_30d: Double,
    val percent_change_7d: Double,
    val price: Double,
)