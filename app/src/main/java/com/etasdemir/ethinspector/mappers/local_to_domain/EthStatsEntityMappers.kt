package com.etasdemir.ethinspector.mappers.local_to_domain

import com.etasdemir.ethinspector.data.domain_model.EthStats
import com.etasdemir.ethinspector.data.domain_model.TokenStats
import com.etasdemir.ethinspector.data.local.entity.EthStatsEntity

fun mapEthStatsEntityToEthStats(entity: EthStatsEntity): EthStats {
    val erc20Stats = entity.erc20Stats?.let {
        TokenStats(
            it.tokens,
            it.newTokens,
            it.tx,
            it.newTx,
        )
    }
    val nftStats = entity.nftStats?.let {
        TokenStats(
            it.tokens,
            it.newTokens,
            it.tx,
            it.newTx,
        )
    }
    return EthStats(
        entity.marketPrice,
        entity.priceChange,
        entity.circulation,
        entity.marketCap,
        entity.dominance,
        entity.blocks,
        entity.blockchainSize,
        entity.transactions,
        entity.calls,
        entity.mempoolTransactions,
        entity.mempoolTps,
        entity.mempoolPendingTxValue,
        entity.dailyTransactions,
        entity.dailyBlocks,
        entity.burned24h,
        entity.largestTxValue,
        entity.volume,
        entity.avgTxFee,
        erc20Stats,
        nftStats
    )
}