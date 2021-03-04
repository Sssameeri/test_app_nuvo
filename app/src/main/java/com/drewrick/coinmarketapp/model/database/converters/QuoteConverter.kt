package com.drewrick.coinmarketapp.model.database.converters

import androidx.room.TypeConverter
import com.drewrick.coinmarketapp.model.models.Quote
import com.google.gson.Gson


class QuoteConverter {

    @TypeConverter
    fun quoteToString(quote: Quote): String {
        return Gson().toJson(quote)
    }

    @TypeConverter
    fun stringToQuote(quote: String): Quote {
        return Gson().fromJson(quote, Quote::class.java)
    }

}