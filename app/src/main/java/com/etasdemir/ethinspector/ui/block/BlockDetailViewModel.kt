package com.etasdemir.ethinspector.ui.block

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.etasdemir.ethinspector.data.Repository
import com.etasdemir.ethinspector.data.domain_model.Block
import com.etasdemir.ethinspector.ui.UIResponseState
import com.etasdemir.ethinspector.ui.components.DetailTopBarState
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

    private val _topBarState = MutableStateFlow<DetailTopBarState?>(null)
    val topBarState = _topBarState.asStateFlow()

    private val _blockState =
        MutableStateFlow<UIResponseState<Block>>(UIResponseState.Loading())
    val blockState = _blockState.asStateFlow()

    fun initialize(blockNumber: String, topBarTitle: String) {
        _topBarState.value = DetailTopBarState(
            topBarTitle,
            blockState.value.data?.isFavourite ?: false,
            this::onFavouriteChange,
            blockNumber
        )
    }

    fun getBlockDetailByNumber(blockNumber: String) {
        viewModelScope.launch {
            val blockResponse =
                repository.getBlockInfoByNumber(blockNumber.toULong(), true)
            val uiBlockState = mapResponseToUIResponseState(blockResponse)
            _blockState.value = uiBlockState
            changeTopBarFavouriteState(uiBlockState.data?.isFavourite ?: false)
        }
    }

    private fun onFavouriteChange(previous: Boolean, now: Boolean) {
        val block = blockState.value.data
        if (block != null) {
            viewModelScope.launch {
                val newBlock = block.copy(isFavourite = now)
                repository.saveBlock(newBlock)
                changeTopBarFavouriteState(now)
            }
        }
    }

    private fun changeTopBarFavouriteState(isFavourite: Boolean) {
        val prevState = topBarState.value
        if (prevState?.isFavouriteEnabled == isFavourite) {
            return
        }
        prevState?.let {
            _topBarState.value = DetailTopBarState(
                prevState.barTitle,
                isFavourite,
                prevState.onFavouriteClick,
                prevState.textToCopy
            )
        }
    }
}