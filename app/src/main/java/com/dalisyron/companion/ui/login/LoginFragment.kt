package com.dalisyron.companion.ui.login

import android.animation.Animator
import android.annotation.TargetApi
import android.net.Uri
import android.os.Bundle
import android.widget.VideoView
import com.dalisyron.companion.R
import dagger.android.support.DaggerFragment
import android.media.MediaPlayer
import android.os.Build
import android.view.*
import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton
import com.dd.morphingbutton.MorphingButton
import com.wnafee.vector.MorphButton
import com.wnafee.vector.compat.AnimatedVectorDrawable
import com.wnafee.vector.compat.ResourcesCompat
import com.wnafee.vector.compat.VectorDrawable
import com.google.android.material.animation.AnimatorSetCompat.playTogether
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.os.AsyncTask
import android.view.ViewAnimationUtils
import android.widget.Toast
import com.dalisyron.companion.ui.home.HomeFragment


class LoginFragment : DaggerFragment() {


    lateinit var btn: CircularProgressButton

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



        btn = view.findViewById(R.id.firssst_morph)



        btn.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                class doAsync(val handler: () -> Unit) : AsyncTask<String, String, String>() {
                    override fun doInBackground(vararg params: String?): String? {
                        Thread.sleep(3000)
                        return "Done"
                    }

                    override fun onPostExecute(result: String?) {
                        if(result.equals("Done")){
                            Toast.makeText(this@LoginFragment.context,"This is it",Toast.LENGTH_SHORT).show()
                            btn.doneLoadingAnimation(R.color.Red,BitmapFactory.decodeResource(resources,R.drawable.ic_done_white_48dp))
                        }
                    }
                }
                btn.startAnimation()
                doAsync{}.execute()
            }
        })

//        bas = view.findViewById(R.id.morph)
//
//
//        val circle = MorphingButton.Params.create()
//            .duration(500)
//            .cornerRadius(90) // 56 dp
//            .width(90) // 56 dp
//            .height(90) // 56 dp
//            .color(R.color.twitterBlue) // normal state color
//            .colorPressed(R.color.White) // pressed state color
//            .icon(R.drawable.ic_done_white_48dp)
//
//        bas.setOnClickListener(object : View.OnClickListener {
//            override fun onClick(v: View?) {
//                bas.morph(circle)
//            }
//        })
//
//        morphbtn = view.findViewById(R.id.playPauseBtn)
//
//        morphbtn.setOnClickListener(object : View.OnClickListener {
//            override fun onClick(v: View?) {
//                morphbtn.setState(MorphButton.MorphState.START, true)
//            }
//        })


    }
}