package com.dalisyron.companion.ui.ping

import android.view.View

class PingPresenter : PingContract.Presenter {
    lateinit var view: PingContract.View

    override fun onProgressFinished() {
        view.setPingButtonState(true)
        view.setProgressBarVisibility(View.GONE)
        view.showDialogBox("وضعیت اظطراری!!", "هلپ جو وضعیت مناسبی ندارد", "متوجه شدم")
    }

    override fun onPingButtonClicked(){
        view.setPingButtonState(false)
        view.setProgressBarVisibility(View.VISIBLE)
        view.showProgressBar()
    }
}