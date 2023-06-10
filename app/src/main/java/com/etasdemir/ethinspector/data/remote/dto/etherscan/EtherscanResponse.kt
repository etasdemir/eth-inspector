package com.etasdemir.ethinspector.data.remote.dto.etherscan

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class EtherscanResponse<T> (
    val status: String,
    val message: String,
    val result: T?,
)