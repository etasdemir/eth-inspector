package com.etasdemir.ethinspector.mappers

import com.etasdemir.ethinspector.data.remote.entity.etherscan.EtherscanRPCResponse
import com.etasdemir.ethinspector.data.remote.entity.etherscan.TransactionResponse
import com.etasdemir.ethinspector.ui.transaction.TransactionDetailState
import com.etasdemir.ethinspector.utils.*

fun mapTransactionToTxDetailState(state: EtherscanRPCResponse<TransactionResponse>): TransactionDetailState {
    val result = state.result
    val amount = result.value.toDecimal().toString().fromWei(EthUnit.ETHER).toDouble()
    val gas = result.gas.toDecimal().toDouble()
    val gasPrice = result.gasPrice.toDecimal().toString().fromWei(EthUnit.GWEI).toDouble()
    val maxFeePerGas: Double? =
        result.maxFeePerGas?.toDecimal()?.toString()?.fromWei(EthUnit.GWEI)?.toDouble()
    val fee: Double = gas * gasPrice

    return TransactionDetailState(
        transactionHash = result.hash,
        timestamp = null,
        block = result.blockNumber.toDecimal(),
        amount = amount,
        fee = fee,
        fromAddress = result.from,
        toAddress = result.to,
        gasAmount = gas,
        gasPrice = gasPrice,
        maxFeePerGas = maxFeePerGas,
        txType = result.type.toDecimal().toInt(),
        nonce = result.nonce?.toDecimal().toString(),
    )
}