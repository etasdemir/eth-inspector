package com.etasdemir.ethinspector.data.remote.dao

import com.etasdemir.ethinspector.data.remote.entity.blockchair.*
import com.etasdemir.ethinspector.data.remote.entity.etherscan.*
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

typealias BlockchairAccountResponse = BlockchairResponse<AddressResponse<AccountResponse>>
typealias BlockchairContractResponse = BlockchairResponse<AddressResponse<ContractResponse>>
typealias EtherscanContractCreations = EtherscanResponse<List<ContractCreationResponse>>
typealias EtherscanTokenTransfers = EtherscanResponse<List<TokenTransferResponse>>


interface EtherscanAddressDao {
    @GET("/api?module=contract&action=getcontractcreation")
    suspend fun getContractCreation(@Query("contractaddresses") contractAddresses: List<String>):
            Response<EtherscanContractCreations>

    @GET("/api?module=account&action=tokentx")
    suspend fun getERC20TokenTransfers(
        @Query("address") addressHash: String,
        @Query("page") pageNumber: Int = 1,
        @Query("offset") offset: Int = 100,
        @Query("sort") sort: String = "asc"
    ): Response<EtherscanTokenTransfers>
}

interface BlockchairAddressDao {
    @GET("/ethereum/dashboards/address/{hash}?erc_20=true&nonce=false")
    suspend fun getAccountInfoByHash(@Path("hash") addressHash: String):
            Response<ResponseBody>

    @GET("/ethereum/dashboards/address/{hash}?contract_details=true&erc_20=false&nonce=false")
    suspend fun getContractInfoByHash(@Path("hash") addressHash: String):
            Response<ResponseBody>

}