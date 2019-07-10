package com.dalisyron.companion.ui.home

interface HomeContract {

    interface Presenter : HomeContract {
        fun onNewTripButtonClicked(x:Float,y:Float)
        fun onManageContactsClicked()
    }

    interface View : HomeContract {
        fun navigateToNewTrip()
        fun navigateToContacts()
        fun navigateToNewTrip(x:Float,y:Float)
    }
}