package com.etasdemir.ethinspector.data.remote.dto.blockchair

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ContractDetailsResponse (
    val type: String?,
    val data: TokenData?,
    @Json(name = "creating_transaction_hash")
    val creatingTransactionHash: String,
    @Json(name = "creating_address")
    val creatingAddress: String,
    @Json(name = "creating_time")
    val creatingTime: String,
)

@JsonClass(generateAdapter = true)
data class TokenData(
    @Json(name = "token_name")
    val tokenName: String,
    @Json(name = "token_symbol")
    val tokenSymbol: String,
    @Json(name = "token_decimals")
    val tokenDecimals: Long,
)
