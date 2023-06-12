package com.etasdemir.ethinspector.ui.contract

import androidx.lifecycle.viewModelScope
import com.etasdemir.ethinspector.data.Repository
import com.etasdemir.ethinspector.data.domain_model.Contract
import com.etasdemir.ethinspector.mappers.remote_to_domain.mapContractResponseToState
import com.etasdemir.ethinspector.ui.UIResponseState
import com.etasdemir.ethinspector.ui.mapResponseToUIResponseState
import com.etasdemir.ethinspector.ui.shared.AddressViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContractDetailViewModel @Inject constructor(
    private val repository: Repository,
) : AddressViewModel(repository) {

    private val _contractState =
        MutableStateFlow<UIResponseState<Contract>>(UIResponseState.Loading())
    val contractState = _contractState.asStateFlow()

    fun getContractDetailByHash(hash: String) {
        viewModelScope.launch {
            val contractResponse = repository.getContractInfoByHash(hash)
            val contractState = mapResponseToUIResponseState(contractResponse) {
                mapContractResponseToState(it)
            }
            _contractState.value = contractState
        }
    }
}