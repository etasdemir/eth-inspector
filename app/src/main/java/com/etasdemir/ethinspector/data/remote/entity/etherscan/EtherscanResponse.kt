package com.etasdemir.ethinspector.data.remote.entity.etherscan

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class EtherscanResponse<T> (
    val status: String,
    val message: String,
    val result: T?,
)