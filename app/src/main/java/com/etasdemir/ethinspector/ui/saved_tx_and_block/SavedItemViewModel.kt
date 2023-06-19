package com.etasdemir.ethinspector.ui.saved_tx_and_block

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.etasdemir.ethinspector.data.Repository
import com.etasdemir.ethinspector.data.domain_model.Block
import com.etasdemir.ethinspector.data.domain_model.Transaction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SavedItemViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _savedTransactionsState = MutableStateFlow<List<Transaction>>(emptyList())
    val savedTransactionsState = _savedTransactionsState.asStateFlow()

    private val _savedBlocksState = MutableStateFlow<List<Block>>(emptyList())
    val savedBlocksState = _savedBlocksState.asStateFlow()

    fun getSavedItems(type: SavedItemType) {
        viewModelScope.launch(Dispatchers.IO) {
            if (type == SavedItemType.BLOCK) {
                val blocks = repository.getFavouriteBlocks()
                _savedBlocksState.emit(blocks)
            } else if (type == SavedItemType.TRANSACTION) {
                val transactions = repository.getFavouriteTransactions()
                _savedTransactionsState.emit(transactions)
            }
        }
    }

}