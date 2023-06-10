package com.etasdemir.ethinspector.data.domain_model

data class Contract(
    val contractInfo: ContractInfo,
    val transactions: List<AddressTransaction>
)

data class ContractInfo(
    val creatorAddress: String,
    val creationTime: String,
    val txCount: Long,
    val balanceWei: String,
    val balanceUsd: Double
)
