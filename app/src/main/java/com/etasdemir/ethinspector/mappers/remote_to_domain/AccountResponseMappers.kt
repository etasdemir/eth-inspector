package com.etasdemir.ethinspector.mappers.remote_to_domain

import com.etasdemir.ethinspector.data.ResponseResult
import com.etasdemir.ethinspector.data.domain_model.*
import com.etasdemir.ethinspector.data.remote.service.BlockchairAccountResponse
import com.etasdemir.ethinspector.utils.convertTokenAmountFromDecimal

fun mapAccountResponseToAccount(
    response: ResponseResult<BlockchairAccountResponse>,
    accountAddress: String
): ResponseResult<Account> {
    val root = response.data
    val data = response.data?.data
    if (response is ResponseResult.Success && root != null && data != null) {
        val address = data.address
        val transactions = arrayListOf<AddressTransaction>()
        val addressTokens = arrayListOf<AddressToken>()
        val transfers = arrayListOf<AddressTransfer>()

        val accountInfo =
            AccountInfo(
                accountAddress,
                address.balanceWei,
                address.balanceUsd,
                address.transactionCount
            )

        data.calls?.forEach { call ->
            transactions.add(
                AddressTransaction(
                    call.transactionHash,
                    call.value,
                    call.blockId,
                    call.time
                )
            )
        }

        data.holdings?.erc20?.forEach {
            addressTokens.add(
                AddressToken(
                    accountAddress,
                    it.tokenName,
                    it.tokenSymbol,
                    it.tokenAddress,
                    it.balanceApproximate
                )
            )
        }

        return ResponseResult.Success(
            Account(
                accountInfo,
                transactions,
                addressTokens,
                transfers
            )
        )
    } else {
        val errorMessage = response.errorMessage!!
        return ResponseResult.Error(errorMessage)
    }
}

fun addTransfersToAccount(
    account: ResponseResult<Account>,
    transfersResponse: ResponseResult<List<TokenTransfer>>
): ResponseResult<Account> {
    val accountData = account.data
    val transfersData = transfersResponse.data
    val transfers = arrayListOf<AddressTransfer>()
    if (transfersResponse is ResponseResult.Success && transfersData != null) {
        transfersData.forEach {
            val amount = convertTokenAmountFromDecimal(it.value, it.tokenDecimal.toInt())
            transfers.add(
                AddressTransfer(
                    it.hash,
                    it.to,
                    it.tokenName,
                    it.tokenSymbol,
                    amount,
                    it.blockNumber,
                    it.timeStamp
                )
            )
        }
    }
    return if (account is ResponseResult.Success && accountData != null) {
        ResponseResult.Success(
            Account(
                accountData.accountInfo,
                accountData.transactions,
                accountData.addressTokens,
                transfers
            )
        )
    } else {
        val errorMessage = account.errorMessage!!
        ResponseResult.Error(errorMessage)
    }
}
