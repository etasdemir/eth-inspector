package com.etasdemir.ethinspector.ui.search

import androidx.lifecycle.ViewModel
import com.etasdemir.ethinspector.data.remote.RemoteRepository
import com.etasdemir.ethinspector.utils.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

enum class RawTextType {
    ADDRESS, BLOCK, TRANSACTION, INVALID
}

@HiltViewModel
class SearchViewModel @Inject constructor(remoteRepository: RemoteRepository) : ViewModel() {

    private val _searchResult: MutableStateFlow<RequestUIStateWithType<Any, RawTextType>> =
        MutableStateFlow(RequestUIStateWithType())
    val searchResult: StateFlow<RequestUIStateWithType<Any, RawTextType>> =
        _searchResult.asStateFlow()

    fun searchText(text: String) {
        _searchResult.value = RequestUIStateWithType(RequestState.LOADING)

        when (parseRawTextToType(text)) {
            RawTextType.TRANSACTION -> {
                // search transaction
                _searchResult.value =
                    RequestUIStateWithType(RequestState.SUCCESS, null, RawTextType.TRANSACTION)
            }
            RawTextType.ADDRESS -> {
                // search address
                _searchResult.value =
                    RequestUIStateWithType(RequestState.SUCCESS, null, RawTextType.ADDRESS)
            }
            RawTextType.BLOCK -> {
                // search block
                _searchResult.value =
                    RequestUIStateWithType(RequestState.SUCCESS, null, RawTextType.BLOCK)
            }
            else -> {
                _searchResult.value =
                    RequestUIStateWithType(RequestState.SUCCESS, type = RawTextType.INVALID)
            }
        }
    }

    private fun parseRawTextToType(text: String): RawTextType {
        if (text.startsWith("0x")) {
            if (text.length == Constants.TRANSACTION_HEX_LEN) {
                return RawTextType.TRANSACTION
            } else if (text.length == Constants.ADDRESS_HEX_LEN) {
                return RawTextType.ADDRESS
            }
        } else {
            return RawTextType.BLOCK
        }
        return RawTextType.INVALID
    }
}