package com.etasdemir.ethinspector.data.domain_model

data class Account(
    val accountInfo: AccountInfo,
    val transactions: List<AddressTransaction>,
    val addressTokens: List<AddressToken>,
    val transfers: List<AddressTransfer>,

    var isFavourite: Boolean = false,
    var userGivenName: String? = null
)

data class AccountInfo(
    val accountAddress: String,
    val balanceWei: String,
    val balanceUsd: Double,
    val transactionCount: Long
)
