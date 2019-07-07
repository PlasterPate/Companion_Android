package com.dalisyron.companion.ui.companionStatus

interface HelpeeStatusContract {
    interface view : HelpeeStatusContract{

    }

    interface presenter : HelpeeStatusContract{
        fun onEmergencyButtonClicked()
    }
}