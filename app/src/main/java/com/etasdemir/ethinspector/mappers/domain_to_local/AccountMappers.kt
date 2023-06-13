package com.etasdemir.ethinspector.mappers.domain_to_local

import com.etasdemir.ethinspector.data.domain_model.Account
import com.etasdemir.ethinspector.data.local.entity.*

fun mapAccountToAccountRelation(account: Account, address: String): AccountRelationEntity {
    val accountInfoEntity = AccountInfoEntity(
        account.accountInfo.accountAddress,
        account.accountInfo.balanceWei,
        account.accountInfo.balanceUsd,
        account.accountInfo.transactionCount,
        account.isFavourite,
        account.userGivenName,
    )
    val transactions = arrayListOf<AddressTransactionEntity>()
    val transfers = arrayListOf<TransferEntity>()
    val tokens = arrayListOf<AddressTokenEntity>()

    account.transactions.forEach {
        if (it.transactionHash != null) {
            transactions.add(
                AddressTransactionEntity(
                    it.transactionHash,
                    address,
                    it.amountWei,
                    it.block,
                    it.date
                )
            )
        }
    }
    account.transfers.forEach {
        transfers.add(
            TransferEntity(
                it.hash,
                address,
                it.to,
                it.tokenName,
                it.tokenSymbol,
                it.amount,
                it.blockNumber,
                it.timestamp,
            )
        )
    }
    for (token in account.addressTokens) {
        tokens.add(
            AddressTokenEntity(
                address,
                token.name,
                token.symbol,
                token.tokenAddress,
                token.quantity
            )
        )
    }

    return AccountRelationEntity(
        accountInfoEntity,
        transactions,
        tokens,
        transfers
    )
}