package com.etasdemir.ethinspector.data.domain_model

data class Block(
    val blockNumber: ULong,
    val timestamp: String,
    val txCount: Int,
    val minerAddress: String,
    val gasLimit: ULong,
    val gasUsed: ULong,
    val baseFeePerGas: ULong,
    val transactions: List<BlockTransaction>,

    var isFavourite: Boolean = false
)

data class BlockTransaction(
    val address: String,
    val amount: ULong,
)
