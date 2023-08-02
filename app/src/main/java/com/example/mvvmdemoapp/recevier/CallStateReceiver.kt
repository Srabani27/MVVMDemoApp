package com.example.mvvmdemoapp.recevier

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.TelephonyManager
import com.example.mvvmdemoapp.model.CallEvent
import com.example.mvvmdemoapp.model.CallStateViewModel

class CallStateReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == TelephonyManager.ACTION_PHONE_STATE_CHANGED) {
            val state = intent.getStringExtra(TelephonyManager.EXTRA_STATE)
            val phoneNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER)

            val callEvent = when (state) {
                TelephonyManager.EXTRA_STATE_RINGING -> CallEvent.IncomingCallStarted(phoneNumber.orEmpty())
                TelephonyManager.EXTRA_STATE_OFFHOOK -> CallEvent.OutgoingCallStarted(phoneNumber.orEmpty())
                TelephonyManager.EXTRA_STATE_IDLE -> {
                    if (phoneNumber != null) {
                        CallEvent.MissedCall(phoneNumber)
                    } else {
                        CallEvent.IncomingCallEnded
                    }
                }
                else -> null
            }

            if (callEvent != null) {
                val viewModel = CallStateViewModel()
                viewModel.updateCallState(callEvent)
            }
        }
    }
}
