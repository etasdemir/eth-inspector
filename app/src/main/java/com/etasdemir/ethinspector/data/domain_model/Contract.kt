package com.etasdemir.ethinspector.data.domain_model

data class Contract(
    val contractInfo: ContractInfo,
    val transactions: List<AddressTransaction>,

    var isFavourite: Boolean = false,
    var userGivenName: String? = null
)

data class ContractInfo(
    val contractAddress: String,
    val creatorAddress: String,
    val creationTime: String,
    val txCount: Long,
    val balanceWei: String,
    val balanceUsd: Double
)
