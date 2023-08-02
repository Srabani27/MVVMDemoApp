package com.example.mvvmdemoapp.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class CallViewModel : ViewModel() {
    private val _toastMessage = MutableLiveData<String>()
    val toastMessage: LiveData<String>
        get() = _toastMessage

    fun onIncomingCallStarted(phoneNumber: String) {
        _toastMessage.value = "Incoming Call Started $phoneNumber"
    }

    fun onOutgoingCallStarted(phoneNumber: String) {
        _toastMessage.value = "Outgoing Call Started $phoneNumber"
    }

    fun onIncomingCallEnded() {
        _toastMessage.value = "Incoming Call Ended"
    }

    fun onOutgoingCallEnded() {
        _toastMessage.value = "Outgoing Call Ended"
    }
}
