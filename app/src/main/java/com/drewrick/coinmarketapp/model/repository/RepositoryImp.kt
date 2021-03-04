package com.drewrick.coinmarketapp.model.repository

import android.util.Log
import com.drewrick.coinmarketapp.BuildConfig
import com.drewrick.coinmarketapp.model.database.dao.CurrencyDao
import com.drewrick.coinmarketapp.model.models.Currency
import com.drewrick.coinmarketapp.model.networking.NetworkAPI
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class RepositoryImp(
    val networkApi: NetworkAPI,
    val currencyDao: CurrencyDao,
    val compositeDisposable: CompositeDisposable
) : Repository {

    private val TAG = "TAG"

    override fun getDataFromNetwork(): Single<List<Currency>> {
        return networkApi
            .getInfo(BuildConfig.API_KEY)
            .map { response ->
                response.result
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSuccess {
                insertDataIntoDatabase(it)
            }
    }

    override fun insertDataIntoDatabase(data: List<Currency>) {
        compositeDisposable.add(
            currencyDao
                .insert(data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Log.d(TAG, "Inserted")
                }, {
                    Log.d(TAG, it.message.toString())
                })
        )
    }

    override fun getDataFromDatabase(): Observable<List<Currency>> {
        return currencyDao
            .getData()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun updateDataInDatabase() {
        TODO("Not yet implemented")
    }
}