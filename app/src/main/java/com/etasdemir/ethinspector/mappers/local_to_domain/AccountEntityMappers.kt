package com.etasdemir.ethinspector.mappers.local_to_domain

import com.etasdemir.ethinspector.data.domain_model.*
import com.etasdemir.ethinspector.data.local.entity.AccountRelationEntity

fun mapAccountRelationToAccount(accountRelation: AccountRelationEntity): Account {
    val accountInfo = AccountInfo(
        accountRelation.accountInfo.accountAddress,
        accountRelation.accountInfo.balanceWei,
        accountRelation.accountInfo.balanceUsd,
        accountRelation.accountInfo.transactionCount,
    )
    val transactions = arrayListOf<AddressTransaction>()
    val transfers = arrayListOf<AddressTransfer>()
    val addressTokens = arrayListOf<AddressToken>()

    accountRelation.transactions.forEach {
        transactions.add(
            AddressTransaction(
                it.transactionHash,
                it.amountWei,
                it.block,
                it.date
            )
        )
    }
    accountRelation.transfers.forEach {
        transfers.add(
            AddressTransfer(
                it.hash,
                it.to,
                it.tokenName,
                it.tokenSymbol,
                it.amount,
                it.blockNumber,
                it.timestamp,
            )
        )
    }
    for (token in accountRelation.tokens) {
        addressTokens.add(
            AddressToken(
                token.ownerAddress,
                token.name,
                token.symbol,
                token.tokenAddress,
                token.quantity
            )
        )
    }

    return Account(
        accountInfo,
        transactions,
        addressTokens,
        transfers,
        accountRelation.accountInfo.isFavourite,
        accountRelation.accountInfo.userGivenName
    )
}