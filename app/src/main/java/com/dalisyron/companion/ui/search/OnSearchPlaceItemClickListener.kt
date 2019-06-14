package com.dalisyron.companion.ui.search

import com.dalisyron.data.model.PlaceEntity

interface OnSearchPlaceItemClickListener {
    fun onItemClicked(placeEntity: PlaceEntity)
}