package com.etasdemir.ethinspector.ui.contract

import androidx.compose.material3.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.etasdemir.ethinspector.data.remote.RemoteRepository
import com.etasdemir.ethinspector.mappers.mapContractResponseToState
import com.etasdemir.ethinspector.ui.UIResponseState
import com.etasdemir.ethinspector.ui.components.AddressSaveModalState
import com.etasdemir.ethinspector.ui.components.DetailTopBarState
import com.etasdemir.ethinspector.ui.mapResponseToUIResponseState
import com.etasdemir.ethinspector.utils.clip
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
@OptIn(ExperimentalMaterial3Api::class)
class ContractDetailViewModel @Inject constructor(
    private val remoteRepository: RemoteRepository
) : ViewModel() {

    private lateinit var topBarTitle: String
    private lateinit var address: String

    lateinit var modalState: AddressSaveModalState
        private set

    private val _contractDetailState =
        MutableStateFlow<UIResponseState<ContractDetailState>>(UIResponseState.Loading())
    val contractDetailState = _contractDetailState.asStateFlow()

    private val _isSheetShown = MutableStateFlow(false)
    val isSheetShown = _isSheetShown.asStateFlow()

    private val _topBarState = MutableStateFlow<DetailTopBarState?>(null)
    val topBarState = _topBarState.asStateFlow()

    fun initialize(address: String, title: String) {
        this.topBarTitle = title
        this._topBarState.value = DetailTopBarState(
            title,
            false,
            this::onFavouriteClick,
            title
        )
        this.address = address
        val sheetState = SheetState(
            skipPartiallyExpanded = true,
            initialValue = SheetValue.Hidden,
            skipHiddenState = false
        )
        this.modalState = AddressSaveModalState(
            address,
            this::onModalCancel,
            this::onModalSave,
            sheetState
        )
    }

    private fun onModalCancel() {
        _isSheetShown.value = false
        Timber.e("save address $address with name ${address.clip(6)}")
    }

    private fun onModalSave(name: String) {
        _isSheetShown.value = false
        Timber.e("save address $address with name $name")
    }

    private fun onFavouriteClick(previous: Boolean, now: Boolean) {
        if (now) {
            _isSheetShown.value = true
        } else {
            Timber.e("remove address $address from favourites")
        }
    }

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