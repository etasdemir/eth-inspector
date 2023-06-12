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
            mappedTransactions.add(
                BlockTransaction(result.number.toDecimal(), transaction.hash, transaction.value.toDecimal())
            )
        }
        val block = Block(
            blockNumber = result.number.toDecimal(),
            timestamp = result.timestamp.toDecimal().toString(),
            txCount = result.transactions.size,
            minerAddress = result.miner,
            gasLimit = result.gasLimit.toDecimal(),
            gasUsed = result.gasUsed.toDecimal(),
            baseFeePerGas = result.baseFeePerGas.toDecimal(),
            transactions = mappedTransactions
        )
        return ResponseResult.Success(block)
    } else {
        val errorMessage = response.errorMessage!!
        return ResponseResult.Error(errorMessage)
    }

}