package com.drewrick.coinmarketapp.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.work.*
import com.drewrick.coinmarketapp.model.database.DatabaseInstance
import com.drewrick.coinmarketapp.model.database.dao.CurrencyDao
import com.drewrick.coinmarketapp.model.models.Currency
import com.drewrick.coinmarketapp.model.networking.NetworkAPI
import com.drewrick.coinmarketapp.model.networking.RetrofitInstance
import com.drewrick.coinmarketapp.model.repository.Repository
import com.drewrick.coinmarketapp.model.repository.RepositoryImp
import com.drewrick.coinmarketapp.worker.NetworkWorker
import io.reactivex.disposables.CompositeDisposable

class CurrencyViewModel(application: Application) :
    AndroidViewModel(application) {

    private var compositeDisposable: CompositeDisposable = CompositeDisposable()
    private var networkAPI: NetworkAPI = RetrofitInstance.provideNetworkAPI()
    private var currencyDao: CurrencyDao =
        DatabaseInstance.provideDatabase(application).currencyDao()

    private val currencyList: MutableLiveData<List<Currency>> by lazy {
        MutableLiveData<List<Currency>>()
    }

    private val mutableCurrency = MutableLiveData<Currency>()
    val selectedCurrency: LiveData<Currency> get() = mutableCurrency

    fun selectedCurrency(currency: Currency) {
        mutableCurrency.value = currency
    }

    private val repository: Repository = RepositoryImp(
        networkAPI,
        currencyDao,
        compositeDisposable
    )

    fun makeNetworkRequest(): LiveData<WorkInfo> {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val networkRequestWork = OneTimeWorkRequestBuilder<NetworkWorker>()
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(getApplication())
            .enqueue(networkRequestWork)

        return WorkManager.getInstance(getApplication())
            .getWorkInfoByIdLiveData(networkRequestWork.id)
    }

    fun getCurrencyFromDatabase(): LiveData<List<Currency>> {
        compositeDisposable.add(
            repository.getDataFromDatabase()
                .subscribe(
                    {
                        currencyList.postValue(it)
                    },
                    {
                        Log.d("TAG", it.message.toString())
                    }
                )
        )
        return currencyList
    }

}