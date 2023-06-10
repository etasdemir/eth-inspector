package com.etasdemir.ethinspector.data.remote.service

import com.etasdemir.ethinspector.data.remote.dto.*
import com.etasdemir.ethinspector.data.remote.dto.blockchair.BlockchairResponse
import com.etasdemir.ethinspector.data.remote.dto.blockchair.EthStatsResponse
import retrofit2.Response
import retrofit2.http.GET

interface EthStatsService {
    @GET("/ethereum/stats")
    suspend fun getEthStats(): Response<BlockchairResponse<EthStatsResponse>>
}