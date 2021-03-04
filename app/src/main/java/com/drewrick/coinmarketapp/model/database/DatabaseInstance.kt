package com.drewrick.coinmarketapp.model.database

import android.content.Context
import androidx.room.Room

object DatabaseInstance {
    fun provideDatabase(ctx: Context) = Room.databaseBuilder(
        ctx,
        CurrencyDatabase::
        class.java,
        DATABASE_NAME
    ).build()

}