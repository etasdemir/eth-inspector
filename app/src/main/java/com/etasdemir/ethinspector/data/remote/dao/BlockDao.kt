package com.etasdemir.ethinspector.data.remote.dao

import com.etasdemir.ethinspector.data.remote.entity.etherscan.BlockResponse
import com.etasdemir.ethinspector.data.remote.entity.etherscan.EtherscanResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface BlockDao {

    @GET("/api?module=proxy&action=eth_getBlockByNumber")
    suspend fun getBlockInfoByHash(
        @Query("tag") blockHash: String,
        @Query("boolean") getTransactionsAsObject: Boolean
    ): Response<EtherscanResponse<BlockResponse>>

}