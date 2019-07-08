package com.dalisyron.companion.ui.helpeeStatus

interface HelpeeStatusContract {
    interface view : HelpeeStatusContract{
        fun toastMessage(message : String)
        fun showPingProgress()
        fun hidePingProgress()
    }

    interface presenter : HelpeeStatusContract{
        fun onEmergencyButtonClicked()
        fun onBeingPinged()
        fun onHelpeeRespond()
        fun onHelpeeNotRespond()
    }
}