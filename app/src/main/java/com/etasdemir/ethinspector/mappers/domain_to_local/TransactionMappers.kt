package com.etasdemir.ethinspector.mappers.domain_to_local

import com.etasdemir.ethinspector.data.domain_model.Transaction
import com.etasdemir.ethinspector.data.local.entity.TransactionEntity

fun mapTransactionToTransactionEntity(transaction: Transaction): TransactionEntity {
    return TransactionEntity(
        transaction.transactionHash,
        transaction.timestamp,
        transaction.block.toLong(),
        transaction.amount,
        transaction.fee,
        transaction.fromAddress,
        transaction.toAddress,
        transaction.gasAmount,
        transaction.gasPrice,
        transaction.maxFeePerGas,
        transaction.txType,
        transaction.nonce,
        transaction.isFavourite,
    )
}