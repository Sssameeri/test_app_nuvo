package com.drewrick.coinmarketapp.model.networking

import com.drewrick.coinmarketapp.model.models.Response
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface NetworkAPI {
    @Headers("Accept:application/json")
    @GET("cryptocurrency/listings/latest")
    fun getInfo(
        @Query("CMC_PRO_API_KEY") apiKey: String
    ): Single<Response>
}