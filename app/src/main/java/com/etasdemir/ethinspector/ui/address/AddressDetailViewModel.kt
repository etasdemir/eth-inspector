package com.etasdemir.ethinspector.ui.address

import androidx.lifecycle.viewModelScope
import com.etasdemir.ethinspector.data.Repository
import com.etasdemir.ethinspector.data.domain_model.Account
import com.etasdemir.ethinspector.ui.UIResponseState
import com.etasdemir.ethinspector.ui.mapResponseToUIResponseState
import com.etasdemir.ethinspector.ui.shared.AddressViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddressDetailViewModel @Inject constructor(
    private val repository: Repository,
) : AddressViewModel<Account>(repository) {

    private val _addressState =
        MutableStateFlow<UIResponseState<Account>>(UIResponseState.Loading())
    val addressState = _addressState.asStateFlow()

    fun getAccountInfoByHash(hash: String) {
        viewModelScope.launch {
            val addressResponse = repository.getAccountInfoByHash(hash)
            val uiAddressState = mapResponseToUIResponseState(addressResponse)
            uiAddressState.data?.let {
                super.initData(it)
            }
            _addressState.value = uiAddressState
        }
    }

}