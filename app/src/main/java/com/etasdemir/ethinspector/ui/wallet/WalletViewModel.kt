package com.etasdemir.ethinspector.ui.wallet

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.etasdemir.ethinspector.data.Repository
import com.etasdemir.ethinspector.data.domain_model.SavedAddresses
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WalletViewModel @Inject constructor(
    repository: Repository
) : ViewModel() {

    private val _savedAddresses = MutableStateFlow<SavedAddresses?>(null)
    val savedAddresses = _savedAddresses.asStateFlow()

    init {
        viewModelScope.launch {
            val result = repository.getSavedAddresses()
            _savedAddresses.emit(result)
        }
    }
}