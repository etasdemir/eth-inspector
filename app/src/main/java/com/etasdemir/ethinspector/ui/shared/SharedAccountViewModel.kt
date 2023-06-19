package com.etasdemir.ethinspector.ui.shared

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.etasdemir.ethinspector.data.Repository
import com.etasdemir.ethinspector.data.domain_model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedAccountViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    /**
     * Note: Normally, shared flow would be used for events. But, for some reason stateflow, and flow
     * are not emitted in a shared view model. Only live data and shared flow worked.
     * For example, userState updated in account screen but EthInspectorTheme does not received that update.
     * */
    private val _userState = MutableSharedFlow<User?>(1)
    val userState = _userState.asSharedFlow()

    fun getUser() {
        viewModelScope.launch(Dispatchers.IO) {
            _userState.emit(repository.getUser())
        }
    }

    fun saveUser(user: User) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.saveUser(user)
            val savedUser = repository.getUser()
            _userState.emit(savedUser)
        }
    }
}