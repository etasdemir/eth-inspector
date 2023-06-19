package com.etasdemir.ethinspector.data.domain_model

import com.etasdemir.ethinspector.data.local.entity.AccountRelationEntity
import com.etasdemir.ethinspector.data.local.entity.ContractAndTransactionsRelationEntity

data class AddressTransaction(
    val transactionHash: String?,
    val amountWei: Double,
    val block: Long,
    val date: String,
)

data class AddressTransfer(
    val hash: String,
    val to: String?,
    val tokenName: String,
    val tokenSymbol: String,
    val amount: Double,
    val blockNumber: String,
    val timestamp: String
)

enum class AddressType {
    CONTRACT, ACCOUNT
}

data class SavedAddresses(
    val savedAccounts: List<AccountRelationEntity>,
    val savedContracts: List<ContractAndTransactionsRelationEntity>
)
