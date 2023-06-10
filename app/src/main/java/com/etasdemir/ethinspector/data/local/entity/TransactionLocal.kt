package com.etasdemir.ethinspector.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TransactionLocal(
    // Info Card
    @PrimaryKey val transactionHash: String,
    val timestamp: String?,
    val block: Long,
    val amount: Double,
    val fee: Double,

    // Detail Card
    val fromAddress: String,
    val toAddress: String?,
    val gasAmount: Double,
    val gasPrice: Double,
    val maxFeePerGas: Double?,
    val txType: Int,
    val nonce: String?,

    val isFavourite: Boolean
)