package com.etasdemir.ethinspector.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.etasdemir.ethinspector.data.Repository
import com.etasdemir.ethinspector.data.domain_model.EthStats
import com.etasdemir.ethinspector.ui.UIResponseState
import com.etasdemir.ethinspector.ui.home.components.*
import com.etasdemir.ethinspector.ui.mapResponseToUIResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _ethStats =
        MutableStateFlow<UIResponseState<EthStats>>(UIResponseState.Loading())
    val ethStats: StateFlow<UIResponseState<EthStats>> =
        _ethStats.asStateFlow()

    fun getEthStats() {
        viewModelScope.launch {
            val ethStatsResponse = repository.getEthStats()
            val mappedResponse = mapResponseToUIResponseState(ethStatsResponse)
            _ethStats.value = mappedResponse
        }
    }

    fun mapEthStatsToState(ethStats: EthStats): EthStatsState {
        val mainStatsState = EthMainStatsState(
            ethStats.marketPrice,
            ethStats.priceChange,
            ethStats.circulation,
            ethStats.marketCap,
            ethStats.dominance
        )
        val allTimeStatsState = AllTimeStatsState(
            ethStats.blocks,
            ethStats.blockchainSize,
            ethStats.transactions,
            ethStats.calls
        )
        val mempoolStatsState = MempoolState(
            ethStats.mempoolTransactions,
            ethStats.mempoolTps,
            ethStats.mempoolPendingTxValue
        )
        val dailyStatsState = DailyStatsState(
            ethStats.dailyTransactions,
            ethStats.dailyBlocks,
            ethStats.burned24h,
            ethStats.largestTxValue,
            ethStats.volume,
            ethStats.avgTxFee,
        )
        return EthStatsState(
            mainStatsState,
            allTimeStatsState,
            mempoolStatsState,
            dailyStatsState,
            ethStats.erc20Stats,
            ethStats.nftStats
        )
    }
}