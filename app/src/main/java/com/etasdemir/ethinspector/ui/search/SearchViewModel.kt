package com.etasdemir.ethinspector.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.etasdemir.ethinspector.data.remote.RemoteRepository
import com.etasdemir.ethinspector.data.remote.entity.SearchType
import com.etasdemir.ethinspector.utils.RequestState
import com.etasdemir.ethinspector.utils.RequestUIStateWithType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SearchViewModel @Inject constructor(
    private val remoteRepository: RemoteRepository
) : ViewModel() {

    private val _searchResult: MutableStateFlow<RequestUIStateWithType<Any, SearchType>> =
        MutableStateFlow(RequestUIStateWithType())
    val searchResult: StateFlow<RequestUIStateWithType<Any, SearchType>> =
        _searchResult.asStateFlow()

    fun searchText(searchText: String) {
        _searchResult.value = RequestUIStateWithType(RequestState.LOADING)
        viewModelScope.launch {
            val searchResultPair = remoteRepository.search(searchText)
            when (searchResultPair.first) {
                SearchType.TRANSACTION -> {
                    _searchResult.value = RequestUIStateWithType(
                        RequestState.SUCCESS, searchResultPair.second, SearchType.TRANSACTION
                    )
                }

                SearchType.ADDRESS -> {
                    _searchResult.value = RequestUIStateWithType(
                        RequestState.SUCCESS, searchResultPair.second, SearchType.ADDRESS
                    )
                }

                SearchType.BLOCK -> {
                    _searchResult.value = RequestUIStateWithType(
                        RequestState.SUCCESS, searchResultPair.second, SearchType.BLOCK
                    )
                }

                else -> {
                    _searchResult.value =
                        RequestUIStateWithType(RequestState.SUCCESS, type = SearchType.INVALID)
                }
            }
        }
    }

}