package com.dalisyron.companion.ui.helpeeStatus

import com.dalisyron.data.model.CompanionEntity

class HelpeeStatusPresenter : HelpeeStatusContract.presenter {
    override fun onViewCreated(companionEntity: CompanionEntity) {
        view.showCompanionInfo(companionEntity)
    }

    override fun onHelpeeRespond() {
        view.hidePingProgress()
    }

    override fun onHelpeeNotRespond() {
        view.hidePingProgress()
    }

    override fun onBeingPinged() {
        view.showPingProgress()
    }

    override fun onEmergencyButtonClicked() {
        view.toastMessage("وضعیت شما اطلاع داده شد")
        view.showPingProgress()
    }


    lateinit var view : HelpeeStatusContract.view
}