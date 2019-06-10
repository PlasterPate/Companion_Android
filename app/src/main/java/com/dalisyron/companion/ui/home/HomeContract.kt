package com.dalisyron.companion.ui.home

interface HomeContract {

    interface Presenter : HomeContract {
        fun onNewTripButtonClicked()
        fun onTripLogButtonClicked()
        fun onActiveTripsButtonClicked()
        fun onManageContactsButtonClicked()
    }

    interface View : HomeContract {
        fun navigateToNewTrip()
    }
}