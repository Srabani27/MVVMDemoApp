package com.example.mvvmdemoapp.fragment

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.telephony.TelephonyManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.mvvmdemoapp.R
import com.example.mvvmdemoapp.model.CallViewModel

class ContactStatusFragment : Fragment() {

    private val callViewModel: CallViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_contact_status, container, false)

        val btnIncomingCallStarted: Button = view.findViewById(R.id.btnIncomingCallStarted)
        btnIncomingCallStarted.setOnClickListener {
            val phoneNumber = "8008004887" // Replace this with the actual phone number
            callViewModel.onIncomingCallStarted(phoneNumber)
        }

        val btnOutgoingCallStarted: Button = view.findViewById(R.id.btnOutgoingCallStarted)
        btnOutgoingCallStarted.setOnClickListener {
            val phoneNumber = "9876543210" // Replace this with the actual phone number
            callViewModel.onOutgoingCallStarted(phoneNumber)
        }

        val btnIncomingCallEnded: Button = view.findViewById(R.id.btnIncomingCallEnded)
        btnIncomingCallEnded.setOnClickListener {
            callViewModel.onIncomingCallEnded()
        }

        val btnOutgoingCallEnded: Button = view.findViewById(R.id.btnOutgoingCallEnded)
        btnOutgoingCallEnded.setOnClickListener {
            callViewModel.onOutgoingCallEnded()
        }

        callViewModel.toastMessage.observe(viewLifecycleOwner, { message ->
            if (message != null) {
                showToast(message)
            }
        })

        return view
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}
