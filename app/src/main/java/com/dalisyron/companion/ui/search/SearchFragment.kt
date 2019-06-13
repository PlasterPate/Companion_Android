package com.dalisyron.companion.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dalisyron.companion.R
import com.google.android.gms.location.places.ui.PlacePicker
import dagger.android.support.DaggerFragment

class SearchFragment : DaggerFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }
}