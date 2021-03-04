package com.drewrick.coinmarketapp.worker

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.drewrick.coinmarketapp.BuildConfig
import com.drewrick.coinmarketapp.model.database.DatabaseInstance
import com.drewrick.coinmarketapp.model.database.dao.CurrencyDao
import com.drewrick.coinmarketapp.model.models.Currency
import com.drewrick.coinmarketapp.model.networking.NetworkAPI
import com.drewrick.coinmarketapp.model.networking.RetrofitInstance
import io.reactivex.disposables.CompositeDisposable

class NetworkWorker(ctx: Context, params: WorkerParameters) : Worker(ctx, params) {

    private val networkApi: NetworkAPI = RetrofitInstance.provideNetworkAPI()
    private val currencyDao: CurrencyDao =
        DatabaseInstance.provideDatabase(ctx.applicationContext).currencyDao()
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()


    override fun doWork(): Result {
        return makeNetworkRequest()
    }

    private fun makeNetworkRequest(): Result {
        compositeDisposable.add(networkApi
            .getInfo(BuildConfig.API_KEY)
            .map { response ->
                response.result
            }
            .doOnSuccess {
                insertIntoDatabase(it)
            }
            .subscribe({
                Log.d(NetworkWorker::class.simpleName, "Success")
            }, {
                Log.d(NetworkWorker::class.simpleName, it.message.toString())
            })
        )
        return Result.success()
    }

    private fun insertIntoDatabase(data: List<Currency>) {
        compositeDisposable.add(
            currencyDao
                .insert(data)
                .doOnComplete {
                    compositeDisposable.dispose()
                }
                .subscribe({
                    Log.d(NetworkWorker::class.simpleName, "Inserted")
                }, {
                    Log.d(NetworkWorker::class.simpleName, it.message.toString())
                })
        )
    }

}