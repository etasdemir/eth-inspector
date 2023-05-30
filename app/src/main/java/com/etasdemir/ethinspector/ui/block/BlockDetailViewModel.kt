package com.etasdemir.ethinspector.ui.block

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.etasdemir.ethinspector.data.remote.RemoteRepository
import com.etasdemir.ethinspector.mappers.mapBlockResponseToBlockState
import com.etasdemir.ethinspector.ui.UIResponseState
import com.etasdemir.ethinspector.ui.mapResponseToUIResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BlockDetailViewModel @Inject constructor(
    private val remoteRepository: RemoteRepository
) : ViewModel() {

    private val _blockDetailState =
        MutableStateFlow<UIResponseState<BlockDetailState>>(UIResponseState.Loading())
    val blockDetailState = _blockDetailState.asStateFlow()

    fun getBlockDetailByNumber(blockNumber: String) {
        viewModelScope.launch {
            val blockResponse =
                remoteRepository.getBlockInfoByNumber(blockNumber.toULong(), true)
            val uiBlockState = mapResponseToUIResponseState(blockResponse) {
                mapBlockResponseToBlockState(it)
            }
            _blockDetailState.value = uiBlockState
        }
    }

}