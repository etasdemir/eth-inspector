package com.etasdemir.ethinspector.data.local.entity

import androidx.room.*

/**
 * If EthStats endpoint fails, use latest local copy
 * */
@Entity
data class EthStatsLocal(
    @PrimaryKey val installationId: String,

    // Main Stats
    val marketPrice: Double,
    val priceChange: Double,
    val circulation: String,
    val marketCap: String,
    val dominance: Double,

    // All time stats
    val blocks: String,
    val blockchainSize: String,
    val transactions: String,
    val calls: String,

    // Mempool
    val mempoolTransactions: String,
    val mempoolTps: String,
    val mempoolPendingTxValue: String,

    // Daily Stats
    val dailyTransactions: Double,
    val dailyBlocks: Double,
    val burned24h: String,
    val largestTxValue: String,
    val volume: String,
    val avgTxFee: Double,

    @Embedded(prefix = "erc_")
    val erc20Stats: LocalTokenStats,
    @Embedded(prefix = "nft_")
    val nftStats: LocalTokenStats
)

data class LocalTokenStats(
    val tokens: String,
    val newTokens: String,
    val tx: String,
    val newTx: String,
)