package com.etasdemir.ethinspector.mappers

import com.etasdemir.ethinspector.data.remote.dao.BlockchairAccountResponse
import com.etasdemir.ethinspector.data.remote.dao.EtherscanTokenTransfers
import com.etasdemir.ethinspector.ui.address.AddressDetailState
import com.etasdemir.ethinspector.ui.address.components.*
import com.etasdemir.ethinspector.ui.components.AddressTransactionItemState
import com.etasdemir.ethinspector.utils.convertTokenAmountFromDecimal

fun mapAddressResponseToAddressDetailState(
    response: BlockchairAccountResponse
): AddressDetailState? {
    val data = response.data ?: return null
    val address = data.address
    val transactions = arrayListOf<AddressTransactionItemState>()
    val tokens = arrayListOf<TokenItemState>()
    val transfers = arrayListOf<TransferItemState>()

    val addressInfo =
        AddressInfoColumnState(address.balanceWei, address.balanceUsd, address.transactionCount)

    data.calls?.forEach { call ->
        transactions.add(
            AddressTransactionItemState(
                call.transactionHash,
                call.value,
                call.blockId,
                call.time
            )
        )
    }

    data.holdings?.erc20?.forEach {
        tokens.add(
            TokenItemState(it.tokenName, it.tokenSymbol, it.tokenAddress, it.balanceApproximate)
        )
    }

    return AddressDetailState(
        addressInfo,
        transactions,
        tokens,
        transfers
    )
}

fun addTransfersToResponse(state: AddressDetailState, transfersResponse: EtherscanTokenTransfers) {
    val transfers = arrayListOf<TransferItemState>()
    transfersResponse.result?.forEach {
        val amount = convertTokenAmountFromDecimal(it.value, it.tokenDecimal.toInt())
        transfers.add(
            TransferItemState(
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
    state.transfers = transfers
}
