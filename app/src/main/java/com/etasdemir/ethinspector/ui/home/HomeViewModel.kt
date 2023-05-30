package com.etasdemir.ethinspector.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.etasdemir.ethinspector.data.remote.RemoteRepository
import com.etasdemir.ethinspector.data.remote.entity.blockchair.BlockchairResponse
import com.etasdemir.ethinspector.data.remote.entity.blockchair.EthStatsResponse
import com.etasdemir.ethinspector.ui.UIResponseState
import com.etasdemir.ethinspector.ui.mapResponseToUIResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

typealias EthStatsResponseState = UIResponseState<BlockchairResponse<EthStatsResponse>>

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val remoteRepository: RemoteRepository
) : ViewModel() {

    private val _ethStatsResult =
        MutableStateFlow<EthStatsResponseState>(UIResponseState.Loading())
    val ethStatsResult: StateFlow<EthStatsResponseState> =
        _ethStatsResult.asStateFlow()

    fun getEthStats() {
        viewModelScope.launch {
            val ethStatsResponse = remoteRepository.getEthStats()
            val mappedResponse = mapResponseToUIResponseState(ethStatsResponse)
            _ethStatsResult.value = mappedResponse
        }
    }
}