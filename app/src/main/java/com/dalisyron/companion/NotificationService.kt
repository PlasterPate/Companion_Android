package com.dalisyron.companion

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.dalisyron.remote.api.TokenService
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class NotificationService : FirebaseMessagingService() {

    override fun onMessageReceived(p0: RemoteMessage?) {
        super.onMessageReceived(p0)
        println("Notif Received!!!!!!!!!!")
        println(p0)
    }

    override fun onNewToken(p0: String?) {
        super.onNewToken(p0)
        println("tokennnnnnnnnnnnnnnnnn")
        println(p0)
        println("tooken")
    }
}
