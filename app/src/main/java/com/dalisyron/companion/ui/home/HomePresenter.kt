package com.dalisyron.companion.ui.home

import javax.inject.Inject

class HomePresenter @Inject constructor() : HomeContract.Presenter {

    lateinit var view : HomeContract.View

    override fun onNewTripButtonClicked() {
        view.navigateToNewTrip()
    }
}