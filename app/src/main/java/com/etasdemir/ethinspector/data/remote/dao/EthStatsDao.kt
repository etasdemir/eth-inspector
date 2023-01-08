package com.etasdemir.ethinspector.data.remote.dao

import com.etasdemir.ethinspector.data.remote.entity.EthStats
import com.etasdemir.ethinspector.data.remote.entity.GenericResponse
import retrofit2.Response
import retrofit2.http.GET

interface EthStatsDao {
    @GET("/ethereum/stats")
    suspend fun getEthStats(): Response<GenericResponse<EthStats>>
}