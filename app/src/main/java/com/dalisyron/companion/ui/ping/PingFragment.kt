package com.dalisyron.companion.ui.ping

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.dalisyron.companion.R
import dagger.android.DispatchingAndroidInjector

class PingFragment : Fragment(), PingContract.View {

    override fun setPingButtonState(state: Boolean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
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
        return inflater.inflate(R.layout.fragment_ping, container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}