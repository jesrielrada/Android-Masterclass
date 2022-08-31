package com.prometheus_service.midas.currencyconverter.data

import com.prometheus_service.midas.currencyconverter.data.models.CurrencyResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface CurrencyApi {

    @GET("/exchangerates_data/latest")
    suspend fun getRates(
        @Header("apikey") apiKey: String,
        @Query("base") base: String
    ): Response<CurrencyResponse>

}
