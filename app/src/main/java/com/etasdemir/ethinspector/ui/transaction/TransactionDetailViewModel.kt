package com.etasdemir.ethinspector.ui.transaction

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.etasdemir.ethinspector.data.Repository
import com.etasdemir.ethinspector.data.domain_model.Transaction
import com.etasdemir.ethinspector.ui.UIResponseState
import com.etasdemir.ethinspector.ui.components.DetailTopBarState
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

    private val _topBarState = MutableStateFlow<DetailTopBarState?>(null)
    val topBarState = _topBarState.asStateFlow()

    fun initialize(transactionHash: String, topBarTitle: String) {
        _topBarState.value = DetailTopBarState(
            topBarTitle,
            transactionState.value.data?.isFavourite ?: false,
            this::onFavouriteChange,
            transactionHash
        )
    }

    fun getTransactionByHash(txHash: String) {
        viewModelScope.launch {
            val txResponse = repository.getTransactionByHash(txHash)
            val mappedUIResponse = mapResponseToUIResponseState(txResponse)
            _transactionState.value = mappedUIResponse
            changeTopBarFavouriteState(mappedUIResponse.data?.isFavourite ?: false)
        }
    }

    private fun onFavouriteChange(previous: Boolean, now: Boolean) {
        val transaction = transactionState.value.data
        if (transaction != null) {
            viewModelScope.launch {
                val newTransaction = transaction.copy(isFavourite = now)
                repository.saveTransaction(newTransaction)
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