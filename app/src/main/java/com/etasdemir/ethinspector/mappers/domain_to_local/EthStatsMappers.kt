package com.etasdemir.ethinspector.mappers.domain_to_local

import com.etasdemir.ethinspector.data.domain_model.EthStats
import com.etasdemir.ethinspector.data.local.entity.EthStatsEntity
import com.etasdemir.ethinspector.data.local.entity.LocalTokenStats

fun mapEthStatsToEntity(ethStats: EthStats, installationId: String): EthStatsEntity {
    val erc20Stats = ethStats.erc20Stats?.let {
        return@let if (it.tokens != null && it.newTokens != null && it.tx != null && it.newTx != null) {
            LocalTokenStats(
                it.tokens,
                it.newTokens,
                it.tx,
                it.newTx,
            )
        } else null
    }
    val nftStats = ethStats.nftStats?.let {
        return@let if (it.tokens != null && it.newTokens != null && it.tx != null && it.newTx != null) {
            LocalTokenStats(
                it.tokens,
                it.newTokens,
                it.tx,
                it.newTx,
            )
        } else null
    }
    return EthStatsEntity(
        installationId,
        ethStats.marketPrice,
        ethStats.priceChange,
        ethStats.circulation,
        ethStats.marketCap,
        ethStats.dominance,
        ethStats.blocks,
        ethStats.blockchainSize,
        ethStats.transactions,
        ethStats.calls,
        ethStats.mempoolTransactions,
        ethStats.mempoolTps,
        ethStats.mempoolPendingTxValue,
        ethStats.dailyTransactions,
        ethStats.dailyBlocks,
        ethStats.burned24h,
        ethStats.largestTxValue,
        ethStats.volume,
        ethStats.avgTxFee,
        erc20Stats,
        nftStats
    )
}