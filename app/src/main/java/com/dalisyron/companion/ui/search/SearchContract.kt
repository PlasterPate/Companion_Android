package com.dalisyron.companion.ui.search

import com.dalisyron.data.model.PlaceEntity

interface SearchContract {

    interface Presenter : SearchContract{

        fun onSearchQueryChanged(query : String)
        fun onSearchSubmit(query : String)
        fun onPlaceItemClicked()
    }

    interface View : SearchContract{
        fun navigateToNewTrip()
        fun showProgressBar()
        fun hideProgressBar()
        fun clearPlaces()
        fun addPlace(placeEntity: PlaceEntity)
    }
}