package com.dalisyron.companion.ui.ping

import android.app.AlertDialog
import android.content.DialogInterface
import android.opengl.Visibility
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.dalisyron.companion.R
import dagger.android.DispatchingAndroidInjector
import kotlinx.android.synthetic.main.fragment_ping.*
import android.os.CountDownTimer
import android.widget.ProgressBar
import kotlinx.android.synthetic.main.fragment_new_trip.*


class PingFragment : Fragment(), PingContract.View {
    override fun setProgressBarVisibility(visibility: Int) {
        progress_bar.visibility = visibility
    }

    override fun showProgressBar() {
        ping_button.setOnClickListener{
            val mCountDownTimer: CountDownTimer
            val mProgressBar: ProgressBar = progress_bar
            mProgressBar.progress = 0
            mCountDownTimer = object : CountDownTimer(15000, 30) {

                override fun onTick(millisUntilFinished: Long) {
                    mProgressBar.incrementProgressBy(2)
                }

                override fun onFinish() {
                    //Do what you want
                    mProgressBar.progress = 500
                    cancel()
                }
            }

            mCountDownTimer.start()
        }
    }

    override fun setPingButtonState(state: Boolean) {
        ping_button.isEnabled = state
    }

    override fun showDialogBox(title: String, message: String, buttonText: String) {
        val dialogBuilder = AlertDialog.Builder(this.context)

        dialogBuilder.setMessage(message)
            .setPositiveButton(buttonText, DialogInterface.OnClickListener{dialog, which ->  })

        val alert = dialogBuilder.create()
        alert.setTitle(title)
        alert.show()
    }

    private val presenter: PingPresenter by lazy {
        PingPresenter().apply {
            view = this@PingFragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(com.dalisyron.companion.R.layout.fragment_ping, container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }
}