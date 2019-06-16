package com.dalisyron.companion.ui.register

import android.graphics.BitmapFactory
import android.graphics.Color
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
import com.dalisyron.companion.ui.home.HomeFragment
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_register.*
import javax.inject.Inject

class RegisterFragment : DaggerFragment(), RegisterContract.View {

    @Inject
    lateinit var presenter : RegisterPresenter

    override fun showError(error: String) {
        Toast.makeText(requireContext(), error, Toast.LENGTH_LONG).show()
    }

    override fun getFirstName(): String {
        return name_edit_text.text.toString()
    }

    override fun getLastName(): String {
        return family_edit_text.text.toString()
    }

    override fun getPhoneNumber(): String {
        return phone_number_edit_text.text.toString()
    }

    override fun getPassword(): String {
        return signup_password_edit_text.text.toString()
    }

    override fun getConfirmPassword(): String {
        return repeat_password_edit_text.text.toString()
    }

    override fun navigateToHome() {
        fragmentManager?.beginTransaction()?.replace(R.id.content_frame, HomeFragment())
            ?.addToBackStack("HomeFromRegister")?.commit()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    @Override
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter.view = this

        val mVideoView = view.findViewById(R.id.signup_video_view) as VideoView

        val uri = Uri.parse("android.resource://" + requireContext().getPackageName() + "/" + R.raw.out)

        mVideoView.setVideoURI(uri)
        mVideoView.start()

        mVideoView.setOnPreparedListener(MediaPlayer.OnPreparedListener { mediaPlayer -> mediaPlayer.isLooping = true })

        register_button.setOnClickListener {
            presenter.onRegisterButtonClicked()
        }
    }
}