package com.dalisyron.companion.ui.helpeeStatus

import com.dalisyron.data.model.CompanionEntity
import java.net.URL

interface HelpeeStatusContract {
    interface view : HelpeeStatusContract{
        fun toastMessage(message : String)
        fun showPingProgress()
        fun hidePingProgress()
        fun showCompanionInfo(companionEntity : CompanionEntity)
    }

    interface presenter : HelpeeStatusContract{
        fun onEmergencyButtonClicked()
        fun onBeingPinged()
        fun onHelpeeRespond()
        fun onHelpeeNotRespond()
        fun onViewCreated(companionEntity: CompanionEntity)
    }
}