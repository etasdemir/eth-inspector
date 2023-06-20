package com.etasdemir.ethinspector.data.remote.dto.blockchair

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class EthStatsResponse(
    // Main Stats
    @field:Json(name = "market_dominance_percentage") val marketDominancePercentage: Double? = null,
    @field:Json(name = "market_cap_usd") val marketCapUsd: String? = null,
    @field:Json(name = "market_price_usd") val marketPriceUsd: Double? = null,
    @field:Json(name = "market_price_btc") val marketPriceBtc: Double? = null,
    @field:Json(name = "market_price_usd_change_24h_percentage") val marketPriceUsdChange24hPercentage: Double? = null,
    @field:Json(name = "circulation_approximate") val circulation: String? = null,

    // All Time
    @field:Json(name = "blocks") val blocks: String? = null,
    @field:Json(name = "transactions") val transactions: String? = null,
    @field:Json(name = "blockchain_size") val blockchainSize: String? = null,
    @field:Json(name = "calls") val calls: String? = null,
    @field:Json(name = "burned") val burned: String? = null,

    // 24h Stats
    @field:Json(name = "blocks_24h") val blocks24h: Double? = null,
    @field:Json(name = "transactions_24h") val transactions24h: Double? = null,
    @field:Json(name = "volume_24h_approximate") val volume24hApproximate: String? = null,
    @field:Json(name = "burned_24h") val burned24h: String? = null,
    @field:Json(name = "largest_transaction_24h") val largestTransaction24h: LargestTransaction24h? = LargestTransaction24h(),
    @field:Json(name = "average_transaction_fee_usd_24h") val averageTransactionFeeUsd24h: Double? = null,
    @field:Json(name = "suggested_transaction_fee_gwei_options") val transactionFeeOpts:
    TransactionFeeGweiOptions? = TransactionFeeGweiOptions(),

    // MempoolCard
    @field:Json(name = "mempool_transactions") val mempoolTransactions: String? = null,
    @field:Json(name = "mempool_median_gas_price") val mempoolMedianGasPrice: String? = null,
    @field:Json(name = "mempool_tps") val mempoolTps: String? = null,
    @field:Json(name = "mempool_total_value_approximate") val mempoolTotalValueApproximate: String? = null,

    // AddressToken Stats
    @field:Json(name = "layer_2") val layer2: Layer2? = Layer2(),
)

@JsonClass(generateAdapter = true)
data class LargestTransaction24h(
    @field:Json(name = "hash") val hash: String? = null,
    @field:Json(name = "value_usd") val value_usd: String? = null,
)

@JsonClass(generateAdapter = true)
data class Layer2(
    @field:Json(name = "erc_20") val erc_20: TokenStat? = null,
    @field:Json(name = "erc_721") val erc_721: TokenStat? = null,
)

@JsonClass(generateAdapter = true)
data class TokenStat(
    @field:Json(name = "tokens") val tokens: String? = null,
    @field:Json(name = "transactions") val transactions: String? = null,
    @field:Json(name = "tokens_24h") val tokens_24h: String? = null,
    @field:Json(name = "transactions_24h") val transactions_24h: String? = null,
)

@JsonClass(generateAdapter = true)
data class TransactionFeeGweiOptions(
    @field:Json(name = "sloth") val sloth: Int? = null,
    @field:Json(name = "slow") val slow: Int? = null,
    @field:Json(name = "normal") val normal: Int? = null,
    @field:Json(name = "fast") val fast: Int? = null,
    @field:Json(name = "cheetah") val cheetah: Int? = null,
)