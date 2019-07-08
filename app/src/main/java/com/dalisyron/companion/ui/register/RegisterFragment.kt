package com.dalisyron.companion.ui.register

import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.Typeface
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.VideoView
import com.dalisyron.companion.R
import com.dalisyron.companion.ui.home.HomeFragment
import com.google.android.material.textfield.TextInputEditText
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_register.*
import org.w3c.dom.Text
import javax.inject.Inject

class RegisterFragment : DaggerFragment(), RegisterContract.View {

    @Inject
    lateinit var presenter : RegisterPresenter
    lateinit var password : TextInputEditText
    lateinit var repeat_password : TextInputEditText

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

    override fun stopRegisterButtonAnimation() {
        register_button.revertAnimation()
    }

    override fun startRegisterButtonAnimation() {
        register_button.startAnimation()
    }

    override fun doneRegisterButtonSuccess() {
        register_button.doneLoadingAnimation(Color.parseColor("#008000"),BitmapFactory.decodeResource(resources,R.drawable.ic_done_white_48dp))
    }

    override fun setRegisterButtonRadius() {
        register_button.setInitialCornerRadius(64.toFloat())
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

        password = view.findViewById(R.id.password_edit_text)
        password.typeface = Typeface.DEFAULT
        password.transformationMethod = PasswordTransformationMethod()

        repeat_password = view.findViewById(R.id.password_edit_text)
        repeat_password.typeface = Typeface.DEFAULT
        repeat_password.transformationMethod = PasswordTransformationMethod()
        setRegisterButtonRadius()

        register_button.setOnClickListener {
            register_button.startAnimation()
            presenter.onRegisterButtonClicked()
        }
    }
}