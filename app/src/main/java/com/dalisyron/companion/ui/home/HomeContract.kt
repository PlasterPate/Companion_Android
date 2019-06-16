package com.dalisyron.companion.ui.home

interface HomeContract {

    interface Presenter : HomeContract {
        fun onNewTripButtonClicked()
    }

    interface View : HomeContract {
        fun navigateToNewTrip()
    }
}