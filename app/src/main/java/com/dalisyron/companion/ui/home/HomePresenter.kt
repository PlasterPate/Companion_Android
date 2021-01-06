package com.dalisyron.companion.ui.home

import javax.inject.Inject

class HomePresenter @Inject constructor() : HomeContract.Presenter {

    lateinit var view : HomeContract.View

    override fun onNewTripButtonClicked(x:Float,y:Float) {
        view.navigateToNewTrip(x,y)
    }

    override fun onManageContactsClicked() {
        view.navigateToContacts()
    }

    override fun onCompanionStatusClicked() {
        view.navigateToCompanionStatus()
    }
}