package com.drewrick.coinmarketapp.model.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.drewrick.coinmarketapp.model.models.Currency
import io.reactivex.Completable
import io.reactivex.Observable

@Dao
interface CurrencyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(data: ArrayList<Currency>): Completable

    @Query("SELECT * FROM currency")
    fun getData(): Observable<List<Currency>>
}