package com.etasdemir.ethinspector.mappers.local_to_domain

import com.etasdemir.ethinspector.data.domain_model.EthStats
import com.etasdemir.ethinspector.data.domain_model.TokenStats
import com.etasdemir.ethinspector.data.local.entity.EthStatsEntity

fun EthStatsEntity.toEthStats(): EthStats {
    val erc20Stats = this.erc20Stats?.let {
        TokenStats(
            it.tokens,
            it.newTokens,
            it.tx,
            it.newTx,
        )
    }
    val nftStats = this.nftStats?.let {
        TokenStats(
            it.tokens,
            it.newTokens,
            it.tx,
            it.newTx,
        )
    }
    return EthStats(
        this.marketPrice,
        this.priceChange,
        this.circulation,
        this.marketCap,
        this.dominance,
        this.blocks,
        this.blockchainSize,
        this.transactions,
        this.calls,
        this.mempoolTransactions,
        this.mempoolTps,
        this.mempoolPendingTxValue,
        this.dailyTransactions,
        this.dailyBlocks,
        this.burned24h,
        this.largestTxValue,
        this.volume,
        this.avgTxFee,
        erc20Stats,
        nftStats
    )
}