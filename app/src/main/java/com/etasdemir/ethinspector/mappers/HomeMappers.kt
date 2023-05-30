package com.etasdemir.ethinspector.mappers

import com.etasdemir.ethinspector.data.remote.entity.blockchair.EthStatsResponse
import com.etasdemir.ethinspector.data.remote.entity.blockchair.TokenStat
import com.etasdemir.ethinspector.ui.home.components.*
import timber.log.Timber

fun mapEthStatsToMainStatsState(ethStats: EthStatsResponse): EthMainStatsState? {
    return try {
        EthMainStatsState(
            ethStats.marketPriceUsd!!,
            ethStats.marketPriceUsdChange24hPercentage!!,
            ethStats.circulation!!,
            ethStats.marketCapUsd!!,
            ethStats.marketDominancePercentage!!
        )
    } catch (exception: NullPointerException) {
        Timber.e("mapEthStatsToMainStatsState field null.")
        null
    }
}

fun mapEthStatsToAllTimeStatsState(ethStats: EthStatsResponse): AllTimeStatsState? {
    return try {
        AllTimeStatsState(
            ethStats.blocks!!,
            ethStats.blockchainSize!!,
            ethStats.transactions!!,
            ethStats.calls!!
        )
    } catch (exception: NullPointerException) {
        Timber.e("mapEthStatsToAllTimeStatsState field null.")
        null
    }
}

fun mapEthStatsToMempoolStatsState(ethStats: EthStatsResponse): MempoolState? {
    return try {
        MempoolState(
            ethStats.mempoolTransactions!!,
            ethStats.mempoolTps!!,
            ethStats.mempoolTotalValueApproximate!!
        )
    } catch (exception: NullPointerException) {
        Timber.e("mapEthStatsToMempoolStatsState field null.")
        null
    }
}

fun mapEthStatsToDailyStatsState(ethStats: EthStatsResponse): DailyStatsState? {
    return try {
        DailyStatsState(
            ethStats.transactions24h!!,
            ethStats.blocks24h!!,
            ethStats.burned24h!!,
            ethStats.largestTransaction24h!!.value_usd!!,
            ethStats.volume24hApproximate!!,
            ethStats.averageTransactionFeeUsd24h!!
        )
    } catch (exception: NullPointerException) {
        Timber.e("mapEthStatsToDailyStatsState field null.")
        null
    }
}

fun mapEthStatsToTokenStatsState(tokenStat: TokenStat): TokenStatsState? {
    return try {
        TokenStatsState(
            tokenStat.tokens!!,
            tokenStat.tokens_24h!!,
            tokenStat.transactions!!,
            tokenStat.transactions_24h!!
        )
    } catch (exception: NullPointerException) {
        Timber.e("mapEthStatsToTokenStatsState field null.")
        null
    }
}
