package com.etasdemir.ethinspector.ui.shared

import androidx.compose.material3.*
import androidx.lifecycle.ViewModel
import com.etasdemir.ethinspector.data.local.LocalRepository
import com.etasdemir.ethinspector.ui.components.AddressSaveModalState
import com.etasdemir.ethinspector.ui.components.DetailTopBarState
import com.etasdemir.ethinspector.utils.AddressType
import com.etasdemir.ethinspector.utils.clip
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
@OptIn(ExperimentalMaterial3Api::class)
open class AddressViewModel @Inject constructor(
    private val localRepository: LocalRepository
) : ViewModel() {

    private lateinit var type: AddressType
    private lateinit var topBarTitle: String
    private lateinit var address: String

    lateinit var modalState: AddressSaveModalState
        private set

    private val _isSheetShown = MutableStateFlow(false)
    val isSheetShown = _isSheetShown.asStateFlow()

    private val _topBarState = MutableStateFlow<DetailTopBarState?>(null)
    val topBarState = _topBarState.asStateFlow()


    fun initialize(address: String, title: String, type: AddressType) {
        this.type = type
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
        Timber.e("save $type $address with name ${address.clip(6)}")
    }

    private fun onModalSave(name: String) {
        _isSheetShown.value = false
        Timber.e("save $type $address with name $name")
    }

    private fun onFavouriteClick(previous: Boolean, now: Boolean) {
        if (now) {
            _isSheetShown.value = true
        } else {
            Timber.e("remove $type $address from favourites")
        }
    }
}