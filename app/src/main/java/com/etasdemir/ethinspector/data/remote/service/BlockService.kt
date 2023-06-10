package com.etasdemir.ethinspector.data.remote.service

import com.etasdemir.ethinspector.data.remote.dto.etherscan.*
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface BlockService {

    @GET("/api?module=proxy&action=eth_getBlockByNumber")
    suspend fun getBlockInfoByHash(
        @Query("tag") blockHash: String,
        @Query("boolean") getTransactionsAsObject: Boolean
    ): Response<EtherscanRPCResponse<BlockResponse>>

}