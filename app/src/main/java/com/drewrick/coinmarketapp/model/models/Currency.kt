package com.drewrick.coinmarketapp.model.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.drewrick.coinmarketapp.model.database.converters.QuoteConverter

@Entity(tableName = "currency")
data class Currency(
    val circulating_supply: Double,
    val cmc_rank: Int,
    val date_added: String,
    @PrimaryKey
    val id: Int,
    val last_updated: String,
    val max_supply: Double,
    val name: String,
    val num_market_pairs: Int,
    @TypeConverters(QuoteConverter::class)
    val quote: Quote,
    val slug: String,
    val symbol: String,
    val total_supply: Double
)