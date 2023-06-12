package com.etasdemir.ethinspector.ui.block

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.etasdemir.ethinspector.data.Repository
import com.etasdemir.ethinspector.data.domain_model.Block
import com.etasdemir.ethinspector.mappers.remote_to_domain.mapBlockResponseToBlockState
import com.etasdemir.ethinspector.ui.UIResponseState
import com.etasdemir.ethinspector.ui.mapResponseToUIResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BlockDetailViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _blockState =
        MutableStateFlow<UIResponseState<Block>>(UIResponseState.Loading())
    val blockState = _blockState.asStateFlow()

    fun getBlockDetailByNumber(blockNumber: String) {
        viewModelScope.launch {
            val blockResponse =
                repository.getBlockInfoByNumber(blockNumber.toULong(), true)
            val uiBlockState = mapResponseToUIResponseState(blockResponse) {
                mapBlockResponseToBlockState(it)
            }
            _blockState.value = uiBlockState
        }
    }

}