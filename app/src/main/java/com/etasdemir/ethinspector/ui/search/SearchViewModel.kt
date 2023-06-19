package com.etasdemir.ethinspector.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.etasdemir.ethinspector.data.Repository
import com.etasdemir.ethinspector.data.domain_model.SearchType
import com.etasdemir.ethinspector.ui.UIResponseState
import com.etasdemir.ethinspector.ui.mapResponseToUIResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _searchResult: MutableStateFlow<Pair<SearchType, UIResponseState<*>>?> =
        MutableStateFlow(null)
    val searchResult: StateFlow<Pair<SearchType, UIResponseState<*>>?> =
        _searchResult.asStateFlow()

    fun searchText(searchText: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _searchResult.value = Pair(SearchType.INVALID, UIResponseState.Loading<Any>())
            val searchResultPair = repository.search(searchText)
            val uiResponseState =
                mapResponseToUIResponseState(searchResultPair.second)
            val searchType = searchResultPair.first
            _searchResult.value = Pair(searchType, uiResponseState)
        }
    }

    fun resetSearchResult() {
        _searchResult.value = null
    }
}