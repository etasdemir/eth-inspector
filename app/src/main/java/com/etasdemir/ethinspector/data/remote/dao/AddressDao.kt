package com.etasdemir.ethinspector.data.remote.dao

import com.etasdemir.ethinspector.data.remote.entity.etherscan.ContractCreationResponse
import com.etasdemir.ethinspector.data.remote.entity.etherscan.EtherscanResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface AddressDao {

    suspend fun getAccountInfoByHash(addressHash: String): Response<Any>

    suspend fun getContractInfoByHash(addressHash: String): Response<Any>

    @GET("/api?module=contract&action=getcontractcreation")
    suspend fun getContractCreation(@Query("contractaddresses") contractAddresses: List<String>):
            Response<EtherscanResponse<List<ContractCreationResponse>>>
}