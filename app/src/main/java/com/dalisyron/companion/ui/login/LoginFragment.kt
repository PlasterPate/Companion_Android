package com.dalisyron.companion.ui.login

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.VideoView
import com.dalisyron.companion.R
import dagger.android.support.DaggerFragment
import android.media.MediaPlayer
import android.view.ViewAnimationUtils
import android.animation.Animator
import android.os.Build
import android.annotation.TargetApi
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.Button
import androidx.fragment.app.Fragment
import com.dalisyron.companion.ui.home.HomeFragment
import com.dalisyron.companion.ui.main.MainActivity
import com.google.android.material.button.MaterialButton
import kotlinx.android.synthetic.main.fragment_home.*
import android.opengl.ETC1.getHeight
import android.opengl.ETC1.getWidth
import com.vincan.circularrevealcompat.ViewAnimationCompatUtils




class LoginFragment : DaggerFragment() {

    lateinit var enterbtn: MaterialButton
    lateinit var revealView: View
    lateinit var touchView: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }


    @Override
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mVideoView = view.findViewById(R.id.login_video_view) as VideoView

        val uri = Uri.parse("android.resource://" + requireContext().getPackageName() + "/" + R.raw.out)

        mVideoView.setVideoURI(uri)
        mVideoView.start()

        mVideoView.setOnPreparedListener(MediaPlayer.OnPreparedListener { mediaPlayer -> mediaPlayer.isLooping = true })
        
    }
}