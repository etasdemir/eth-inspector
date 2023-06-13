package com.etasdemir.ethinspector.data.local.entity

import androidx.room.Entity

@Entity(tableName = "address_transaction", primaryKeys = ["transactionHash", "addressHash"])
data class AddressTransactionEntity(
    val transactionHash: String,
    val addressHash: String,
    val amountWei: Double,
    val block: Long,
    val date: String,
)

@Entity(tableName = "address_token", primaryKeys = ["ownerAddress", "tokenAddress"])
data class AddressTokenEntity(
    val ownerAddress: String,
    val name: String,
    val symbol: String,
    val tokenAddress: String,
    val quantity: Double
)

@Entity(tableName = "transfer", primaryKeys = ["hash", "addressHash"])
data class TransferEntity(
    val hash: String,
    val addressHash: String,
    val to: String?,
    val tokenName: String,
    val tokenSymbol: String,
    val amount: Double,
    val blockNumber: String,
    val timestamp: String
)