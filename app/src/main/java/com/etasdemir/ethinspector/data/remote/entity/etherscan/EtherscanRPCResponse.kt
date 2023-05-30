package com.etasdemir.ethinspector.data.remote.entity.etherscan

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class EtherscanRPCResponse<T> (
    @Json(name = "jsonrpc")
    val jsonRPC: String,
    val id: Long,
    val result: T,
)