package com.drewrick.coinmarketapp.model.repository

import com.drewrick.coinmarketapp.model.models.Currency
import io.reactivex.Observable
import io.reactivex.Single

interface Repository {

    fun getDataFromNetwork(): Single<List<Currency>>

    fun insertDataIntoDatabase(data: List<Currency>)

    fun getDataFromDatabase(): Observable<List<Currency>>

    fun updateDataInDatabase()
}