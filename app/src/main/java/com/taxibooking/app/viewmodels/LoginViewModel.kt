package com.taxibooking.app.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {

    private val _uiState = MutableLiveData<LoginUiState>(LoginUiState.Idle)
    val uiState: LiveData<LoginUiState> = _uiState

    private val _navigateToMain = MutableLiveData<Boolean>()
    val navigateToMain: LiveData<Boolean> = _navigateToMain

    /**
     * Simulated login: no Firebase. Non-empty email and password succeed immediately.
     */
    fun login(email: String, password: String) {
        if (email.isBlank() || password.isBlank()) {
            return
        }
        _uiState.value = LoginUiState.Success
        _navigateToMain.value = true
    }

    fun consumeNavigateToMain() {
        _navigateToMain.value = false
    }

    sealed class LoginUiState {
        data object Idle : LoginUiState()
        data object Loading : LoginUiState()
        data object Success : LoginUiState()
        data class Error(val message: String) : LoginUiState()
    }
}
