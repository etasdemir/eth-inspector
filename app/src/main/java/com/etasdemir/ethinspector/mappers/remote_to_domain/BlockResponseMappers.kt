package com.etasdemir.ethinspector.mappers.remote_to_domain

import com.etasdemir.ethinspector.data.ResponseResult
import com.etasdemir.ethinspector.data.domain_model.Block
import com.etasdemir.ethinspector.data.domain_model.BlockTransaction
import com.etasdemir.ethinspector.data.remote.dto.etherscan.BlockResponse
import com.etasdemir.ethinspector.data.remote.dto.etherscan.EtherscanRPCResponse
import com.etasdemir.ethinspector.utils.toDecimal

fun mapBlockResponseToBlock(response: ResponseResult<EtherscanRPCResponse<BlockResponse>>): ResponseResult<Block> {
    val responseData = response.data
    if (response is ResponseResult.Success && responseData != null) {
        val result = responseData.result
        val mappedTransactions = ArrayList<BlockTransaction>()
        for (transaction in result.transactions) {
            val number = result.number.toDecimal()
            val value = transaction.value.toDecimal()
            if (number != null && value != null) {
                mappedTransactions.add(
                    BlockTransaction(number, transaction.hash, value)
                )
            }
        }

        val blockNumber = result.number.toDecimal()
        val gasLimit = result.gasLimit.toDecimal()
        val gasUsed = result.gasUsed.toDecimal()
        val baseFeePerGas = result.baseFeePerGas.toDecimal()
        return if (blockNumber != null && gasLimit != null && gasUsed != null && baseFeePerGas != null) {
            val block = Block(
                blockNumber = blockNumber,
                timestamp = result.timestamp.toDecimal().toString(),
                txCount = result.transactions.size,
                minerAddress = result.miner,
                gasLimit = gasLimit,
                gasUsed = gasUsed,
                baseFeePerGas = baseFeePerGas,
                transactions = mappedTransactions
            )
            ResponseResult.Success(block)
        } else {
            ResponseResult.Error("Error at mapBlockResponseToBlock: could not convert hex to decimal")
        }

    } else {
        val errorMessage = response.errorMessage!!
        return ResponseResult.Error(errorMessage)
    }

}