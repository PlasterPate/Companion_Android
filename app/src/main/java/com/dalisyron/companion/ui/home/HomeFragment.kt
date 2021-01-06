package com.dalisyron.companion.ui.home

import android.os.Bundle
import android.view.*
import com.dalisyron.companion.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.android.support.DaggerFragment
import android.view.ViewAnimationUtils
import android.content.ContentValues.TAG
import android.util.Log
import android.animation.Animator
import android.opengl.ETC1.getHeight
import android.opengl.ETC1.getWidth
import android.app.Activity
import android.animation.AnimatorListenerAdapter
import android.opengl.ETC1.getHeight
import android.opengl.ETC1.getWidth
import android.annotation.TargetApi
import android.view.View.OnLayoutChangeListener
import com.dalisyron.companion.ui.companionStatus.CompanionStatusFragment
import com.dalisyron.companion.ui.addContacts.AddContactsFragment
import com.dalisyron.companion.ui.contacts.ContactsFragment
import com.dalisyron.companion.ui.newtrip.NewTripFragment
import kotlinx.android.synthetic.main.fragment_home.*
import javax.inject.Inject


class HomeFragment : DaggerFragment(), HomeContract.View {
    @Inject
    lateinit var presenter : HomePresenter

    lateinit var rootLayout: View

    private var revealX: Int = 0

    private var revealY: Int = 0

    var x : Float = 0.toFloat()
    var y : Float = 0.toFloat()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        return root
    }

    @Override
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.view!!.setOnTouchListener(View.OnTouchListener { _, event ->

            x = event.getX()
            y = event.getY()

            when(event.action) {
                MotionEvent.ACTION_DOWN -> {
                    Log.d(TAG, "ACTION_DOWN \nx: $x\ny: $y")
                }
            }
            return@OnTouchListener  true
        })


        val lineStartX = x
        val lineStartY = y

        val anim = ViewAnimationUtils.createCircularReveal(view, lineStartX.toInt(),
            lineStartY.toInt(),0.toFloat(),2000.toFloat())
        anim.duration = 1000
        anim.start()

        presenter.view = this

        new_trip.setOnClickListener {
            presenter.onNewTripButtonClicked(lineStartX,lineStartY)
        }

        manage_companions.setOnClickListener {
            presenter.onManageContactsClicked()
        }

        watching_trips.setOnClickListener {
            presenter.onCompanionStatusClicked()
        }
    }

    override fun navigateToNewTrip() {
        fragmentManager?.beginTransaction()?.replace(R.id.content_frame, NewTripFragment())?.
            addToBackStack("NewTripFromHome")?.commit()
    }

    override fun navigateToNewTrip(x:Float,y:Float) {
        fragmentManager?.beginTransaction()?.replace(R.id.content_frame, NewTripFragment.newInstance(x,y))?.
            addToBackStack("NewTripFromHome")?.commit()
    }

    override fun navigateToContacts() {
        fragmentManager?.beginTransaction()?.replace(R.id.content_frame, AddContactsFragment())?.
            addToBackStack("AddContactsFromHome")?.commit()
    }

    override fun navigateToCompanionStatus() {
        fragmentManager?.beginTransaction()?.replace(R.id.content_frame, CompanionStatusFragment())?.
            addToBackStack("CompanionStatusFromHome")?.commit()
    }
}
