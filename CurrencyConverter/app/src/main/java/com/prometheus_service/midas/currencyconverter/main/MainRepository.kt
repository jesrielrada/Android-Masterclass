package com.prometheus_service.midas.currencyconverter.main

import com.prometheus_service.midas.currencyconverter.data.models.CurrencyResponse
import com.prometheus_service.midas.currencyconverter.util.Resource

interface MainRepository {

    suspend fun getRates(apiKey: String, base: String) : Resource<CurrencyResponse>
}