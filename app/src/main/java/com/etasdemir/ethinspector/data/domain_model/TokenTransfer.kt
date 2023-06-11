package com.etasdemir.ethinspector.data.domain_model

data class TokenTransfer(
    val blockNumber: String,
    val timeStamp: String,
    val hash: String,
    val blockHash: String,
    val from: String,
    val contractAddress: String,
    val to: String?,
    val value: String,
    val tokenName: String,
    val tokenSymbol: String,
    val tokenDecimal: String,
    val gas: String,
    val gasPrice: String,
    val gasUsed: String,
    val cumulativeGasUsed: String,
)
