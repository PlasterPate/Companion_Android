package com.dalisyron.companion.ui.home

class HomePresenter : HomeContract.Presenter {

    lateinit var view : HomeContract.View

    override fun onNewTripButtonClicked() {
        view.navigateToNewTrip()
    }
}