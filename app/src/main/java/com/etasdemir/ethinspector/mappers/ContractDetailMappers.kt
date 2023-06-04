package com.etasdemir.ethinspector.mappers

import com.etasdemir.ethinspector.data.remote.dao.BlockchairContractResponse
import com.etasdemir.ethinspector.ui.components.AddressTransactionItemState
import com.etasdemir.ethinspector.ui.contract.ContractDetailState
import com.etasdemir.ethinspector.ui.contract.components.ContractInfoColumnState

fun mapContractResponseToState(response: BlockchairContractResponse): ContractDetailState {
    val data = response.data
    val transactions = arrayListOf<AddressTransactionItemState>()

    data?.calls?.forEach {
        transactions.add(
            AddressTransactionItemState(
                it.transactionHash,
                it.value,
                it.blockId,
                it.time
            )
        )
    }

    val contractInfo = if (data == null) null else {
        val address = data.address
        val contract = address.contractDetails
        ContractInfoColumnState(
            contract.creatingAddress,
            contract.creatingTime,
            address.transactionCount,
            address.balanceWei,
            address.balanceUsd
        )
    }

    return ContractDetailState(
        contractInfo,
        transactions
    )
}