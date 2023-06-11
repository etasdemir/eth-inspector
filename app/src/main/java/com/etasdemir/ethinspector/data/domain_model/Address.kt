package com.etasdemir.ethinspector.data.domain_model

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
