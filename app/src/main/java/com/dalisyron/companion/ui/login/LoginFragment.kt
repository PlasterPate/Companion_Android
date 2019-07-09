package com.dalisyron.companion.ui.login

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.VideoView
import com.dalisyron.companion.R
import dagger.android.support.DaggerFragment
import android.media.MediaPlayer
import android.view.*
import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.Typeface
import android.os.AsyncTask
import android.view.animation.Animation
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dalisyron.companion.ui.home.HomeFragment
import com.dalisyron.companion.ui.register.RegisterFragment
import com.dalisyron.data.repository.UserRepository
import com.dalisyron.remote.api.UserService
import com.dalisyron.remote.datasource.UserRemoteDataSourceImpl
import com.google.android.material.textfield.TextInputEditText
import com.tfighiera.revealactivity.RevealActivity
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_register.*
import org.w3c.dom.Text
import javax.inject.Inject
import android.text.method.PasswordTransformationMethod
import android.R.attr.password



class LoginFragment : DaggerFragment(), LoginContract.View {

    override fun stopLoginButtonAnimation() {
        login_button.animation = null
        login_button.clearAnimation()
    }

    override fun startLoginButtonAnimation() {
        login_button.startAnimation()
    }

    override fun doneLoginButtonSuccess() {
        login_button.doneLoadingAnimation(Color.parseColor("#008000"),BitmapFactory.decodeResource(resources,R.drawable.ic_done_white_48dp))
    }

    @Inject
    lateinit var presenter : LoginPresenter

    override fun navigateToHome() {
        fragmentManager?.beginTransaction()?.replace(R.id.content_frame, HomeFragment())
            ?.addToBackStack("HomeFromLogin")?.commit()
    }

    override fun navigateToRegister() {
        fragmentManager?.beginTransaction()?.replace(R.id.content_frame, RegisterFragment())
            ?.addToBackStack("RegisterFromLogin")?.commit()
    }

    override fun showProgressBar() {

    }

    override fun showError(error: String) {
        Toast.makeText(requireContext(), error, Toast.LENGTH_LONG).show()
    }

    override fun getUserName(): String {
        return email_edit_text.text.toString()
    }

    override fun getPassword(): String {
        return password_edit_text.text.toString()
    }


    lateinit var btn: CircularProgressButton
    lateinit var password : TextInputEditText

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    @Override
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter.view = this

        registerTextView.setOnClickListener {
            presenter.onRegisterClicked()
        }
        val mVideoView = view.findViewById(R.id.login_video_view) as VideoView

        val uri = Uri.parse("android.resource://" + requireContext().getPackageName() + "/" + R.raw.out)

        mVideoView.setVideoURI(uri)
        mVideoView.start()

        mVideoView.setOnPreparedListener(MediaPlayer.OnPreparedListener { mediaPlayer -> mediaPlayer.isLooping = true })

        password = view.findViewById(R.id.password_edit_text)
        password.typeface = Typeface.DEFAULT
        password.transformationMethod = PasswordTransformationMethod()

        btn = view.findViewById(R.id.login_button)


        btn.setOnClickListener {
            presenter.onLoginButtonClicked()
        }
        /*
        btn.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                class doAsync(val handler: () -> Unit) : AsyncTask<String, String, String>() {
                    override fun doInBackground(vararg params: String?): String? {
                        Thread.sleep(3000)
                        presenter.onLoginButtonClicked()
                        return "Done"
                    }


                    override fun onPostExecute(result: String?) {
                        if(result.equals("Done")){
                            Toast.makeText(this@LoginFragment.context,"This is it",Toast.LENGTH_SHORT).show()
                            btn.doneLoadingAnimation(Color.parseColor("#008000"),BitmapFactory.decodeResource(resources,R.drawable.ic_done_white_48dp))
                            //startActivity(RevealActivity.newIntent(this@LoginFragment.context as Context,view))
                        }
                    }
                }
                btn.startAnimation()
                doAsync{}.execute()
            }
        })
        */

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