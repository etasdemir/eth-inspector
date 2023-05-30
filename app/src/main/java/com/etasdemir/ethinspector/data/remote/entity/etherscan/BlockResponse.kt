package com.etasdemir.ethinspector.data.remote.entity.etherscan

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class BlockResponse(
    val baseFeePerGas: String,
    val gasLimit: String,
    val gasUsed: String,
    val hash: String,
    val miner: String,
    val nonce: String,
    val number: String,
    val parentHash: String,
    val size: String,
    val timestamp: String,
    val transactions: List<TransactionResponse>,
)