package com.dalisyron.companion.ui.home

import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.*
import android.widget.VideoView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.dalisyron.companion.R
import com.dalisyron.companion.ui.login.LoginFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.android.support.DaggerFragment
import android.view.animation.AccelerateInterpolator
import android.view.ViewAnimationUtils
import android.animation.Animator
import android.opengl.ETC1.getHeight
import android.opengl.ETC1.getWidth
import android.app.Activity
import android.animation.AnimatorListenerAdapter
import android.opengl.ETC1.getHeight
import android.opengl.ETC1.getWidth
import android.annotation.TargetApi
import android.content.ContentValues.TAG
import android.util.Log
import android.util.Printer
import android.view.View.OnLayoutChangeListener
import androidx.annotation.IntegerRes
import com.dalisyron.companion.ui.addContacts.AddContactsFragment
import com.dalisyron.companion.ui.contacts.ContactsFragment
import com.dalisyron.companion.ui.newtrip.NewTripFragment
import kotlinx.android.synthetic.main.fragment_home.*
import javax.inject.Inject
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.marginBottom
import androidx.core.view.marginRight
import com.tfighiera.revealactivity.RevealCircleAnimatorHelper


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

        val sda = ViewAnimationUtils.createCircularReveal(view, lineStartX.toInt(),
            lineStartY.toInt(),0.toFloat(),2000.toFloat())
        sda.duration = 1000
        sda.start()

        presenter.view = this

        new_trip.setOnClickListener {
            presenter.onNewTripButtonClicked(lineStartX,lineStartY)
        }

        manage_companions.setOnClickListener {
            presenter.onManageContactsClicked()
        }
//        val mVideoView = view.findViewById(R.id.home_video_view) as VideoView
//
//        val uri = Uri.parse("android.resource://" + requireContext().getPackageName() + "/" + R.raw.out)
//
//        mVideoView.setVideoURI(uri)
//        mVideoView.start()
//
//        mVideoView.setOnPreparedListener(MediaPlayer.OnPreparedListener { mediaPlayer -> mediaPlayer.isLooping = true })
        val addButton = view.findViewById(R.id.new_trip) as FloatingActionButton

//        addButton.setOnFocusChangeListener(View.OnFocusChangeListener {view , addButton -> addButton.setImageDrawable(R.drawable.ic_list)})
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
}
