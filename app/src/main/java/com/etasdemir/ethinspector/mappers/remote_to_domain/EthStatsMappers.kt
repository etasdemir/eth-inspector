package com.etasdemir.ethinspector.mappers.remote_to_domain

import com.etasdemir.ethinspector.data.ResponseResult
import com.etasdemir.ethinspector.data.domain_model.EthStats
import com.etasdemir.ethinspector.data.domain_model.TokenStats
import com.etasdemir.ethinspector.data.remote.dto.blockchair.BlockchairResponse
import com.etasdemir.ethinspector.data.remote.dto.blockchair.EthStatsResponse

fun mapEthStatsResponseToEthStats(
    response: ResponseResult<BlockchairResponse<EthStatsResponse>>
): ResponseResult<EthStats> {
    val responseData = response.data
    val data = responseData?.data
    if (response is ResponseResult.Success && responseData != null && data != null) {
        val erc20Response = data.layer2?.erc_20
        val nftResponse = data.layer2?.erc_721
        val erc20Stats = erc20Response?.let {
            TokenStats(
                erc20Response.tokens!!,
                erc20Response.tokens_24h!!,
                erc20Response.transactions!!,
                erc20Response.transactions_24h!!,
            )
        }
        val nftStats = nftResponse?.let {
            TokenStats(
                nftResponse.tokens!!,
                nftResponse.tokens_24h!!,
                nftResponse.transactions!!,
                nftResponse.transactions_24h!!,
            )
        }
        val ethStats = EthStats(
            data.marketPriceUsd!!,
            data.marketPriceUsdChange24hPercentage!!,
            data.circulation!!,
            data.marketCapUsd!!,
            data.marketDominancePercentage!!,

            data.blocks!!,
            data.blockchainSize!!,
            data.transactions!!,
            data.calls!!,

            data.mempoolTransactions!!,
            data.mempoolTps!!,
            data.mempoolTotalValueApproximate!!,

            data.transactions24h!!,
            data.blocks24h!!,
            data.burned24h!!,
            data.largestTransaction24h!!.value_usd!!,
            data.volume24hApproximate!!,
            data.averageTransactionFeeUsd24h!!,

            erc20Stats,
            nftStats
        )
        return ResponseResult.Success(ethStats)
    } else {
        val errorMessage = response.errorMessage ?: "Error at mapEthStatsResponseToEthStats"
        return ResponseResult.Error(errorMessage)
    }
}
