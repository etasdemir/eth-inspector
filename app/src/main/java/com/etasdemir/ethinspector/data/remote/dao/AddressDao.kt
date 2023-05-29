package com.etasdemir.ethinspector.data.remote.dao

import com.etasdemir.ethinspector.data.remote.entity.blockchair.*
import com.etasdemir.ethinspector.data.remote.entity.etherscan.ContractCreationResponse
import com.etasdemir.ethinspector.data.remote.entity.etherscan.EtherscanResponse
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

typealias BlockchairAccountResponse = BlockchairResponse<AddressResponse<AccountResponse>>
typealias BlockchairContractResponse = BlockchairResponse<AddressResponse<ContractResponse>>
typealias EtherscanContractCreations = EtherscanResponse<List<ContractCreationResponse>>


interface EtherscanAddressDao {

    @GET("/api?module=contract&action=getcontractcreation")
    suspend fun getContractCreation(@Query("contractaddresses") contractAddresses: List<String>):
            Response<EtherscanContractCreations>
}

interface BlockchairAddressDao {

    @GET("/ethereum/dashboards/address/{hash}?erc_20=true&nonce=false")
    suspend fun getAccountInfoByHash(@Path("hash") addressHash: String):
            Response<ResponseBody>

    @GET("/ethereum/dashboards/address/{hash}?contract_details=true")
    suspend fun getContractInfoByHash(@Path("hash") addressHash: String):
            Response<ResponseBody>

}