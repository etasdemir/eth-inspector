package com.etasdemir.ethinspector.ui.transaction

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.etasdemir.ethinspector.data.remote.RemoteRepository
import com.etasdemir.ethinspector.mappers.mapTransactionToTxDetailState
import com.etasdemir.ethinspector.ui.UIResponseState
import com.etasdemir.ethinspector.ui.mapResponseToUIResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactionDetailViewModel @Inject constructor(
    private val remoteRepository: RemoteRepository
) : ViewModel() {

    private val _transactionState =
        MutableStateFlow<UIResponseState<TransactionDetailState>>(UIResponseState.Loading())
    val transactionState: StateFlow<UIResponseState<TransactionDetailState>> =
        _transactionState.asStateFlow()

    fun getTransactionByHash(txHash: String) {
        viewModelScope.launch {
            val txResponse = remoteRepository.getTransactionByHash(txHash)
            val mappedUIResponse = mapResponseToUIResponseState(txResponse) {
                mapTransactionToTxDetailState(it)
            }
            _transactionState.value = mappedUIResponse
        }
    }
}