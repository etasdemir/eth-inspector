package com.etasdemir.ethinspector.mappers.local_to_domain

import com.etasdemir.ethinspector.data.domain_model.*
import com.etasdemir.ethinspector.data.local.entity.ContractAndTransactionsRelationEntity

fun mapContractRelationToContract(
    contractRelation: ContractAndTransactionsRelationEntity
): Contract {
    val contractInfoEntity = contractRelation.contractInfoEntity
    val contractInfo = ContractInfo(
        contractInfoEntity.contractAddress,
        contractInfoEntity.creatorAddress,
        contractInfoEntity.creationTime,
        contractInfoEntity.txCount,
        contractInfoEntity.balanceWei,
        contractInfoEntity.balanceUsd,
    )
    val transactions = arrayListOf<AddressTransaction>()
    for (transaction in contractRelation.transactions) {
        transactions.add(
            AddressTransaction(
                transaction.transactionHash,
                transaction.amountWei,
                transaction.block,
                transaction.date
            )
        )
    }

    return Contract(
        contractInfo,
        transactions,
        contractInfoEntity.isFavourite,
        contractInfoEntity.userGivenName
    )
}