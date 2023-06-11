package com.etasdemir.ethinspector.data.domain_model

data class Account(
    val accountInfo: AccountInfo,
    val transactions: List<AddressTransaction>,
    val tokens: List<Token>,
    var transfers: List<AddressTransfer>,

    var isFavourite: Boolean = false,
    var userGivenName: String? = null
)

data class AccountInfo(
    val balanceWei: String,
    val balanceUsd: Double,
    val transactionCount: Long
)
