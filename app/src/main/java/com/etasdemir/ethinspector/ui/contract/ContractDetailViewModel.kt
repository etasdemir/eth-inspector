package com.etasdemir.ethinspector.ui.contract

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.etasdemir.ethinspector.data.remote.RemoteRepository
import com.etasdemir.ethinspector.mappers.mapContractResponseToState
import com.etasdemir.ethinspector.ui.UIResponseState
import com.etasdemir.ethinspector.ui.mapResponseToUIResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContractDetailViewModel @Inject constructor(
    private val remoteRepository: RemoteRepository
) : ViewModel() {

    private val _contractDetailState =
        MutableStateFlow<UIResponseState<ContractDetailState>>(UIResponseState.Loading())
    val contractDetailState = _contractDetailState.asStateFlow()

    fun getContractDetailByHash(hash: String) {
        viewModelScope.launch {
            val contractResponse = remoteRepository.getContractInfoByHash(hash)
            val contractState = mapResponseToUIResponseState(contractResponse) {
                mapContractResponseToState(it)
            }
            _contractDetailState.value = contractState
        }
    }
}