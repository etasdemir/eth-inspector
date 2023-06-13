package com.etasdemir.ethinspector.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "token_transfer")
data class TokenTransferEntity(
    val blockNumber: String,
    val timeStamp: String,
    @PrimaryKey
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