package org.fossify.phone.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.telecom.TelecomManager
import android.widget.Toast
import org.fossify.commons.extensions.*
import org.fossify.commons.helpers.REQUEST_CODE_SET_DEFAULT_DIALER
import org.fossify.phone.R
import org.fossify.phone.extensions.getHandleToUse
import org.fossify.phone.extensions.resolveOutgoingCallNumber

class DialerActivity : SimpleActivity() {
    private var callNumber: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (appExpired) {
            return
        }

        if (intent.action == Intent.ACTION_CALL && intent.data != null) {
            callNumber = intent.data

            // make sure Simple Dialer is the default Phone app before initiating an outgoing call
            if (!isDefaultDialer()) {
                launchSetDefaultDialerIntent()
            } else {
                initOutgoingCall()
            }
        } else {
            toast(R.string.unknown_error_occurred)
            finish()
        }
    }

    @SuppressLint("MissingPermission")
    private fun initOutgoingCall() {
        try {
            val originalNumber = callNumber.toString().replace("tel:", "")
            val destinationNumber = resolveOutgoingCallNumber(originalNumber)

            getHandleToUse(intent, originalNumber) { handle ->
                if (handle != null) {
                    Bundle().apply {
                        putParcelable(TelecomManager.EXTRA_PHONE_ACCOUNT_HANDLE, handle)
                        putBoolean(TelecomManager.EXTRA_START_CALL_WITH_VIDEO_STATE, false)
                        putBoolean(TelecomManager.EXTRA_START_CALL_WITH_SPEAKERPHONE, false)
                        telecomManager.placeCall(Uri.fromParts("tel", destinationNumber, null), this)
                    }
                }
                finish()
            }
        } catch (e: Exception) {
            showErrorToast(e)
            finish()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, resultData: Intent?) {
        super.onActivityResult(requestCode, resultCode, resultData)
        if (requestCode == REQUEST_CODE_SET_DEFAULT_DIALER) {
            if (!isDefaultDialer()) {
                try {
                    hideKeyboard()
                    Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                        data = Uri.parse("package:$packageName")
                        startActivity(this)
                    }
                    toast(R.string.default_phone_app_prompt, Toast.LENGTH_LONG)
                } catch (ignored: Exception) {
                }
                finish()
            } else {
                initOutgoingCall()
            }
        }
    }
}
