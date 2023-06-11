package com.etasdemir.ethinspector.data.domain_model

data class Transaction(
    val transactionHash: String,
    val timestamp: String?,
    val block: ULong,
    val amount: Double,
    val fee: Double,

    val fromAddress: String,
    val toAddress: String?,
    val gasAmount: Double,
    val gasPrice: Double,
    val maxFeePerGas: Double?,
    val txType: Int,
    val nonce: String?,

    var isFavourite: Boolean = false
)