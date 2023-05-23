package com.etasdemir.ethinspector.data.remote.dao

import com.etasdemir.ethinspector.data.remote.entity.*
import com.etasdemir.ethinspector.data.remote.entity.blockchair.BlockchairResponse
import com.etasdemir.ethinspector.data.remote.entity.blockchair.EthStats
import retrofit2.Response
import retrofit2.http.GET

interface EthStatsDao {
    @GET("/ethereum/stats")
    suspend fun getEthStats(): Response<BlockchairResponse<EthStats>>
}