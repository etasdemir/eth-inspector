package com.etasdemir.ethinspector.ui.shared

import androidx.compose.material3.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.etasdemir.ethinspector.data.Repository
import com.etasdemir.ethinspector.data.domain_model.*
import com.etasdemir.ethinspector.ui.components.AddressSaveModalState
import com.etasdemir.ethinspector.ui.components.DetailTopBarState
import com.etasdemir.ethinspector.utils.clip
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
abstract class AddressViewModel<T> constructor(
    private val repository: Repository
) : ViewModel() {

    private lateinit var type: AddressType
    private lateinit var topBarTitle: String
    private var data: T? = null

    lateinit var modalState: AddressSaveModalState
        private set

    private val _isSheetShown = MutableStateFlow(false)
    val isSheetShown = _isSheetShown.asStateFlow()

    private val _topBarState = MutableStateFlow<DetailTopBarState?>(null)
    val topBarState = _topBarState.asStateFlow()


    fun initialize(address: String, title: String, type: AddressType) {
        this.type = type
        this.topBarTitle = title
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

    fun initData(data: T) {
        this.data = data
        _topBarState.value = DetailTopBarState(
            topBarTitle,
            isFavourite(),
            this::onFavouriteClick,
            getAddress()!!
        )
        changeTopBarFavouriteState(isFavourite())
    }

    private fun onModalCancel() {
        _isSheetShown.value = false
        viewModelScope.launch {
            val address = getAddress()
            address?.let {
                val name = address.clip(6)
                updateDbData(name = name, isFavourite =  true)
            }
        }
    }

    private fun onModalSave(name: String) {
        _isSheetShown.value = false
        viewModelScope.launch {
            updateDbData(name = name, isFavourite =  true)
        }
    }

    private fun onFavouriteClick(previous: Boolean, now: Boolean) {
        if (now) {
            _isSheetShown.value = true
        } else {
            viewModelScope.launch {
                updateDbData(isFavourite =  false)
                changeTopBarFavouriteState(false)
            }
        }
    }

    private suspend fun updateDbData(name: String? = null, isFavourite: Boolean? = null) {
        data?.let {
            if (type == AddressType.ACCOUNT && it is Account) {
                val newName = name ?: it.userGivenName
                val newIsFav = isFavourite ?: it.isFavourite
                val copy = it.copy(userGivenName = newName, isFavourite = newIsFav)
                repository.saveAccount(copy)
            } else if (type == AddressType.CONTRACT && it is Contract) {
                val newName = name ?: it.userGivenName
                val newIsFav = isFavourite ?: it.isFavourite
                val copy = it.copy(userGivenName = newName, isFavourite = newIsFav)
                repository.saveContract(copy)
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

    private fun isFavourite(): Boolean {
        if (data is Account) {
            return (data as Account).isFavourite
        } else if (data is Contract) {
            return (data as Contract).isFavourite
        }
        return false
    }

    private fun getAddress(): String? {
        if (data is Account) {
            return (data as Account).accountInfo.accountAddress
        } else if (data is Contract) {
            return (data as Contract).contractInfo.contractAddress
        }
        return null
    }
}