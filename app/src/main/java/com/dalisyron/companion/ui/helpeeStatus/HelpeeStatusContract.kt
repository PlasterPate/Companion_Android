package com.dalisyron.companion.ui.helpeeStatus

import java.net.URL

interface HelpeeStatusContract {
    interface view : HelpeeStatusContract{
        fun toastMessage(message : String)
        fun showPingProgress()
        fun hidePingProgress()
        fun showCompanionInfo(name : String, phoneNumber : String, profileImage : URL)
    }

    interface presenter : HelpeeStatusContract{
        fun onEmergencyButtonClicked()
        fun onBeingPinged()
        fun onHelpeeRespond()
        fun onHelpeeNotRespond()
    }
}