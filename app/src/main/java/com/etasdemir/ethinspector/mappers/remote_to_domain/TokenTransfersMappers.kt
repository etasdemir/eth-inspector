package com.etasdemir.ethinspector.mappers.remote_to_domain

import com.etasdemir.ethinspector.data.ResponseResult
import com.etasdemir.ethinspector.data.domain_model.TokenTransfer
import com.etasdemir.ethinspector.data.remote.service.EtherscanTokenTransfers

fun mapTokenTransfersResponseToTokenTransfers(
    response: ResponseResult<EtherscanTokenTransfers>
): ResponseResult<List<TokenTransfer>> {
    val responseData = response.data
    val data = responseData?.result
    if (response is ResponseResult.Success && responseData != null && data != null) {
        val tokenTransfers = arrayListOf<TokenTransfer>()
        data.forEach {
            tokenTransfers.add(
                TokenTransfer(
                    it.blockNumber,
                    it.timeStamp,
                    it.hash,
                    it.blockHash,
                    it.from,
                    it.contractAddress,
                    it.to,
                    it.value,
                    it.tokenName,
                    it.tokenSymbol,
                    it.tokenDecimal,
                    it.gas,
                    it.gasPrice,
                    it.gasUsed,
                    it.cumulativeGasUsed
                )
            )
        }
        return ResponseResult.Success(tokenTransfers)
    } else {
        val errorMessage =
            response.errorMessage ?: "Error at mapTokenTransfersResponseToTokenTransfers"
        return ResponseResult.Error(errorMessage)
    }
}