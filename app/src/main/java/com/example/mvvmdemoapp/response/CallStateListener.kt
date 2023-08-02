package com.example.mvvmdemoapp.response

import com.example.mvvmdemoapp.model.CallEvent

interface CallStateListener {
    fun onCallStateChanged(callEvent: CallEvent)
}