package com.drewrick.coinmarketapp.model.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.drewrick.coinmarketapp.model.database.converters.QuoteConverter
import com.drewrick.coinmarketapp.model.database.dao.CurrencyDao
import com.drewrick.coinmarketapp.model.models.Currency

const val DATABASE_NAME = "currency_db"
const val DATABASE_VERSION = 1

@Database(entities = [Currency::class], version = DATABASE_VERSION)
@TypeConverters(QuoteConverter::class)
abstract class CurrencyDatabase : RoomDatabase() {
    abstract fun currencyDao(): CurrencyDao
}