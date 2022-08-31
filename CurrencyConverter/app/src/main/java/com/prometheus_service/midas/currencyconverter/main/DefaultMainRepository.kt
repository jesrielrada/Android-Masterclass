package com.prometheus_service.midas.currencyconverter.main

import com.prometheus_service.midas.currencyconverter.Constants.API_KEY
import com.prometheus_service.midas.currencyconverter.data.CurrencyApi
import com.prometheus_service.midas.currencyconverter.data.models.CurrencyResponse
import com.prometheus_service.midas.currencyconverter.util.Resource
import java.lang.Exception
import javax.inject.Inject


class DefaultMainRepository @Inject constructor(
    private val api: CurrencyApi
) : MainRepository {
    override suspend fun getRates(apiKey: String, base: String): Resource<CurrencyResponse> {
        return try {
            val response = api.getRates(API_KEY, base)
            val result = response.body()
            if(response.isSuccessful && result != null){
                Resource.Success(result)
            }else{
                Resource.Error(response.message())
            }

        }catch (e: Exception){
            Resource.Error(e.message ?: "An error occurred")
        }
    }

}