package com.etasdemir.ethinspector.data.domain_model

data class EthStats(
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

    val erc20Stats: TokenStats,
    val nftStats: TokenStats
)

data class TokenStats(
    val tokens: String,
    val newTokens: String,
    val tx: String,
    val newTx: String,
)
