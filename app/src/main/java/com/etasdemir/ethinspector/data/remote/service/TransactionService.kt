package com.etasdemir.ethinspector.data.remote.service

import com.etasdemir.ethinspector.data.remote.dto.etherscan.*
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface TransactionService {

    @GET("/api?module=proxy&action=eth_getTransactionByHash")
    suspend fun getTransactionByHash(@Query("txhash") transactionId: String):
            Response<EtherscanRPCResponse<TransactionResponse>>

}