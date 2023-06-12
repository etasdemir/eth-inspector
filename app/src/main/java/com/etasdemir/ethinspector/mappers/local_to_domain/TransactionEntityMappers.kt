package com.etasdemir.ethinspector.mappers.local_to_domain

import com.etasdemir.ethinspector.data.domain_model.Transaction
import com.etasdemir.ethinspector.data.local.entity.TransactionEntity

fun mapTransactionEntityToTransaction(transactionEntity: TransactionEntity): Transaction {
    return Transaction(
        transactionEntity.transactionHash,
        transactionEntity.timestamp,
        transactionEntity.block.toULong(),
        transactionEntity.amount,
        transactionEntity.fee,
        transactionEntity.fromAddress,
        transactionEntity.toAddress,
        transactionEntity.gasAmount,
        transactionEntity.gasPrice,
        transactionEntity.maxFeePerGas,
        transactionEntity.txType,
        transactionEntity.nonce,
        transactionEntity.isFavourite,
    )
}