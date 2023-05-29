package com.etasdemir.ethinspector.data.remote.entity.blockchair

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AddressResponse<T>(
    val address: T,
    val calls: List<CallResponse>?,
    @Json(name = "layer_2")
    val holdings: Holding? = null,
)

@JsonClass(generateAdapter = true)
data class Holding(
    @Json(name = "erc_20")
    val erc20: List<TokenInfo>,
)

@JsonClass(generateAdapter = true)
data class TokenInfo(
    @Json(name = "token_address")
    val tokenAddress: String,
    @Json(name = "token_name")
    val tokenName: String,
    @Json(name = "token_symbol")
    val tokenSymbol: String,
    @Json(name = "token_decimals")
    val tokenDecimals: Long,
    @Json(name = "balance_approximate")
    val balanceApproximate: Double,
    val balance: String,
)

