package com.etasdemir.ethinspector.data.remote.dao

import com.etasdemir.ethinspector.data.remote.entity.etherscan.EtherscanResponse
import com.etasdemir.ethinspector.data.remote.entity.etherscan.TransactionResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface TransactionDao {

    @GET("/api?module=proxy&action=eth_getTransactionByHash")
    suspend fun getTransactionByHash(@Query("txhash") transactionId: String): EtherscanResponse<TransactionResponse>

}