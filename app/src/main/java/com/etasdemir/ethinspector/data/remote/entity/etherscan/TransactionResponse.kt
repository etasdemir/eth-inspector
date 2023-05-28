package com.etasdemir.ethinspector.data.remote.entity.etherscan

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TransactionResponse (
    val blockHash: String,
    val blockNumber: String,
    val from: String,
    val gas: String,
    val gasPrice: String,
    val maxFeePerGas: String? = null,
    val maxPriorityFeePerGas: String? = null,
    val hash: String,
    val nonce: String,
    val to: String?,
    val transactionIndex: String,
    val value: String,
    val type: String,
    val chainId: String,
    val v: String,
    val r: String,
    val s: String,
)