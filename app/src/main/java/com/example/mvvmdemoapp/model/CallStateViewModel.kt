package com.example.mvvmdemoapp.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

sealed class CallEvent {
    data class IncomingCallStarted(val phoneNumber: String) : CallEvent()
    data class OutgoingCallStarted(val phoneNumber: String) : CallEvent()
    object IncomingCallEnded : CallEvent()
    data class MissedCall(val phoneNumber: String) : CallEvent()
}

class CallStateViewModel : ViewModel() {
    private val _callState = MutableLiveData<CallEvent>()
    val callState: LiveData<CallEvent> get() = _callState

    fun updateCallState(callEvent: CallEvent) {
        _callState.postValue(callEvent)
    }
}