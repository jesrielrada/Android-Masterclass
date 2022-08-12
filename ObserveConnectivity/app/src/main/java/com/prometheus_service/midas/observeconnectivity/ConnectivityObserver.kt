package com.prometheus_service.midas.observeconnectivity

import kotlinx.coroutines.flow.Flow

interface ConnectivityObserver {

    fun observer() : Flow<Status>

    enum class Status{
        Available, Unavailable, Losing, Lost
    }
}