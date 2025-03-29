package com.example.contentprovider_th_bt2

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.SmsManager
import android.telephony.TelephonyManager
import android.util.Log

class MissedCallReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == TelephonyManager.ACTION_PHONE_STATE_CHANGED) {
            val state = intent.getStringExtra(TelephonyManager.EXTRA_STATE)
            val incomingNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER)

            if (state == TelephonyManager.EXTRA_STATE_RINGING) {
                Log.d("MissedCallReceiver", "Có cuộc gọi đến từ: $incomingNumber")
            } else if (state == TelephonyManager.EXTRA_STATE_IDLE && incomingNumber != null) {
                Log.d("MissedCallReceiver", "Cuộc gọi từ $incomingNumber bị nhỡ")
                sendSms(context, incomingNumber, "Xin lỗi, tôi đang bận. Tôi sẽ gọi lại sau.")
            }
        }
    }

    private fun sendSms(context: Context?, phoneNumber: String, message: String) {
        try {
            val smsManager = SmsManager.getDefault()
            smsManager.sendTextMessage(phoneNumber, null, message, null, null)
            Log.d("MissedCallReceiver", "Đã gửi tin nhắn SMS đến: $phoneNumber")
        } catch (e: Exception) {
            Log.e("MissedCallReceiver", "Lỗi khi gửi SMS: ${e.message}")
        }
    }
}