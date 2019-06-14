package com.dalisyron.companion.ui.register

import android.graphics.BitmapFactory
import android.media.MediaPlayer
import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.VideoView
import androidx.fragment.app.Fragment
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton
import com.dalisyron.companion.R
import dagger.android.support.DaggerFragment

class RegisterFragment : DaggerFragment() {

    lateinit var register_button : CircularProgressButton

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    @Override
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mVideoView = view.findViewById(R.id.signup_video_view) as VideoView

        val uri = Uri.parse("android.resource://" + requireContext().getPackageName() + "/" + R.raw.out)

        mVideoView.setVideoURI(uri)
        mVideoView.start()

        mVideoView.setOnPreparedListener(MediaPlayer.OnPreparedListener { mediaPlayer -> mediaPlayer.isLooping = true })


        register_button = view.findViewById(R.id.register_button)



        register_button.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                class doAsync(val handler: () -> Unit) : AsyncTask<String, String, String>() {
                    override fun doInBackground(vararg params: String?): String? {
                        Thread.sleep(3000)
                        return "Done"
                    }

                    override fun onPostExecute(result: String?) {
                        if(result.equals("Done")){
                            Toast.makeText(this@RegisterFragment.context,"This is it", Toast.LENGTH_SHORT).show()
                            register_button.doneLoadingAnimation(R.color.White, BitmapFactory.decodeResource(resources,R.drawable.ic_done_white_48dp))
                        }
                    }
                }
                register_button.startAnimation()
                doAsync{}.execute()
            }
        })
    }
}