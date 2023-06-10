package com.etasdemir.ethinspector.data.remote.dto.etherscan

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ContractCreationResponse(
    val contractAddress: String,
    val contractCreator: String,
    val txHash: String,
)