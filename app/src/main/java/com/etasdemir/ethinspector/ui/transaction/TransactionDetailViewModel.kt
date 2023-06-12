package com.etasdemir.ethinspector.ui.transaction

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.etasdemir.ethinspector.data.Repository
import com.etasdemir.ethinspector.data.domain_model.Transaction
import com.etasdemir.ethinspector.ui.UIResponseState
import com.etasdemir.ethinspector.ui.mapResponseToUIResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactionDetailViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _transactionState =
        MutableStateFlow<UIResponseState<Transaction>>(UIResponseState.Loading())
    val transactionState: StateFlow<UIResponseState<Transaction>> =
        _transactionState.asStateFlow()

    fun getTransactionByHash(txHash: String) {
        viewModelScope.launch {
            val txResponse = repository.getTransactionByHash(txHash)
            val mappedUIResponse = mapResponseToUIResponseState(txResponse)
            _transactionState.value = mappedUIResponse
        }
    }
}