package com.dalisyron.companion.ui.contacts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewAnimationUtils
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.dalisyron.companion.R

class ContactsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_contacts, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val anim = ViewAnimationUtils.createCircularReveal(
            view, 0.toInt(),
            0.toInt(), 0.toFloat(), 2000.toFloat()
        )
        anim.duration = 1000
        anim.start()
    }
}