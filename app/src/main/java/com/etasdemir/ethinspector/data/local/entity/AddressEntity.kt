package com.etasdemir.ethinspector.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "address_transaction")
data class AddressTransactionEntity(
    @PrimaryKey val transactionHash: String,
    val addressHash: String,
    val amountWei: Double,
    val block: Long,
    val date: String,
)

@Entity(primaryKeys = ["symbol", "accountAddress"])
data class TokenEntityAccountCrossRef(
    val symbol: String,
    val accountAddress: String
)

@Entity(tableName = "token")
data class TokenEntity(
    val name: String,
    @PrimaryKey val symbol: String,
    val address: String,
    val quantity: Double
)

@Entity(tableName = "transfer")
data class TransferEntity(
    @PrimaryKey val hash: String,
    val addressHash: String,
    val to: String?,
    val tokenName: String,
    val tokenSymbol: String,
    val amount: Double,
    val blockNumber: String,
    val timestamp: String
)