package com.etasdemir.ethinspector.mappers.remote_to_domain

import com.etasdemir.ethinspector.data.ResponseResult
import com.etasdemir.ethinspector.data.domain_model.Transaction
import com.etasdemir.ethinspector.data.remote.dto.etherscan.EtherscanRPCResponse
import com.etasdemir.ethinspector.data.remote.dto.etherscan.TransactionResponse
import com.etasdemir.ethinspector.utils.*

fun mapTransactionResponseToTransaction(
    response: ResponseResult<EtherscanRPCResponse<TransactionResponse>>
): ResponseResult<Transaction> {
    val responseData = response.data
    val result = responseData?.result
    if (response is ResponseResult.Success && responseData != null && result != null) {
        val amount = result.value.toDecimal()?.toString()?.fromWei(EthUnit.ETHER)?.toDouble()
        val gas = result.gas.toDecimal()?.toDouble()
        val gasPrice = result.gasPrice.toDecimal()?.toString()?.fromWei(EthUnit.GWEI)?.toDouble()
        val maxFeePerGas: Double? =
            result.maxFeePerGas?.toDecimal()?.toString()?.fromWei(EthUnit.GWEI)?.toDouble()
        val fee = if (gas != null && gasPrice != null) (gasPrice * gas) else null
        val block = result.blockNumber.toDecimal()
        if (block != null && amount != null && fee != null && gas != null && gasPrice != null) {
            val transaction = Transaction(
                transactionHash = result.hash,
                timestamp = null,
                block = block,
                amount = amount,
                fee = fee,
                fromAddress = result.from,
                toAddress = result.to,
                gasAmount = gas,
                gasPrice = gasPrice,
                maxFeePerGas = maxFeePerGas,
                txType = result.type.toDecimal()?.toInt() ?: 0,
                nonce = result.nonce?.toDecimal()?.toString(),
            )
            return ResponseResult.Success(transaction)
        } else {
            return ResponseResult.Error("Error at mapTransactionResponseToTransaction: could not convert hex to decimal")
        }

    } else {
        val errorMessage = response.errorMessage ?: "Error at mapTransactionToTxDetailState"
        return ResponseResult.Error(errorMessage)
    }
}