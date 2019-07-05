package com.dalisyron.companion.ui.home

interface HomeContract {

    interface Presenter : HomeContract {
        fun onNewTripButtonClicked()
        fun onManageContactsClicked()
    }

    interface View : HomeContract {
        fun navigateToNewTrip()
        fun navigateToContacts()
    }
}