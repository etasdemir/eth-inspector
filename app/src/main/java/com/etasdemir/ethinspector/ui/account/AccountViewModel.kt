package com.etasdemir.ethinspector.ui.account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.etasdemir.ethinspector.data.Repository
import com.etasdemir.ethinspector.data.domain_model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val repository: Repository
): ViewModel() {

    private val _userState = MutableStateFlow<User?>(null)
    val userState = _userState.asStateFlow()

    fun getUser() {
        viewModelScope.launch {
            val user = repository.getUser()
            _userState.value = user
        }
    }

    fun updateUser(newUser: User) {
        viewModelScope.launch {
            repository.updateUser(newUser)
            _userState.value = newUser
        }
    }
}