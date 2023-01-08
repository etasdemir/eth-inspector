package com.etasdemir.ethinspector.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.etasdemir.ethinspector.data.remote.RemoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    remoteRepository: RemoteRepository
) : ViewModel() {

    init {
        viewModelScope.launch {
//            remoteRepository.getEthStats()
//                .flowOn(Dispatchers.IO)
//                .collect {
//                    // got result
//                }
        }
    }
}