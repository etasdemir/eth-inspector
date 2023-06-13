package com.etasdemir.ethinspector.mappers.domain_to_local

import com.etasdemir.ethinspector.data.domain_model.Contract
import com.etasdemir.ethinspector.data.local.entity.*

fun mapContractToContractRelation(
    contract: Contract
): ContractAndTransactionsRelationEntity {
    val contractInfo = contract.contractInfo
    val transactions = arrayListOf<AddressTransactionEntity>()
    val contractInfoEntity = ContractInfoEntity(
        contractInfo.contractAddress,
        contractInfo.creatorAddress,
        contractInfo.creationTime,
        contractInfo.txCount,
        contractInfo.balanceWei,
        contractInfo.balanceUsd,
        contract.isFavourite,
        contract.userGivenName,
    )
    contract.transactions.forEach {
        if (it.transactionHash != null) {
            transactions.add(
                AddressTransactionEntity(
                    it.transactionHash,
                    contractInfo.contractAddress,
                    it.amountWei,
                    it.block,
                    it.date,
                )
            )
        }
    }
    return ContractAndTransactionsRelationEntity(contractInfoEntity, transactions)
}