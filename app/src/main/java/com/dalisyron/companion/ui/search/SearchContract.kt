package com.dalisyron.companion.ui.search

interface SearchContract {
    interface Presenter : SearchContract{
        fun onSearchBarClicked()
        fun onSearchBarTextChanged()
        fun onPlaceItemClicked()
    }

    interface View : SearchContract{
        fun navigateToNewTrip()
    }
}