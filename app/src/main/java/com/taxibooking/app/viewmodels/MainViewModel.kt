package com.taxibooking.app.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.taxibooking.app.utils.FirebaseAuthHelper
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainViewModel(
    private val authHelper: FirebaseAuthHelper = FirebaseAuthHelper()
) : ViewModel() {

    private val _requestStatus = MutableLiveData<RequestStatus>(RequestStatus.Idle)
    val requestStatus: LiveData<RequestStatus> = _requestStatus

    fun requestTaxi() {
        viewModelScope.launch {
            _requestStatus.value = RequestStatus.Searching
            delay(3_000)
            _requestStatus.value = RequestStatus.DriverAssigned
        }
    }

    fun clearToIdle() {
        _requestStatus.value = RequestStatus.Idle
    }

    fun signOut() {
        authHelper.signOut()
    }

    sealed class RequestStatus {
        data object Idle : RequestStatus()
        data object Searching : RequestStatus()
        data object DriverAssigned : RequestStatus()
    }
}
