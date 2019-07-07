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
import android.view.View.OnLayoutChangeListener
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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }
    @Override
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter.view = this

        new_trip.setOnClickListener {
            presenter.onNewTripButtonClicked()
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

    override fun navigateToContacts() {
        fragmentManager?.beginTransaction()?.replace(R.id.content_frame, AddContactsFragment())?.
            addToBackStack("AddContactsFromHome")?.commit()
    }
}
