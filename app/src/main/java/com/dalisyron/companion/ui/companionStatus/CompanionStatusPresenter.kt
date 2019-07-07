package com.dalisyron.companion.ui.companionStatus

import android.os.CountDownTimer
import android.view.View

class CompanionStatusPresenter : CompanionStatusContract.Presenter {

    lateinit var view: CompanionStatusContract.View

    override fun onProgressFinished() {
        view.setPingButtonState(true)
        view.setProgressBarVisibility(View.GONE)
        view.showDialogBox("وضعیت اضطراری!!", "هلپ جو وضعیت مناسبی ندارد", "متوجه شدم")
    }

    override fun onPingButtonClicked(){
        view.setPingButtonState(false)
        view.setProgressBarVisibility(View.VISIBLE)
        view.showProgressBar()
    }

    override fun onProgressSuccessful() {
        view.setPingButtonState(true)
        view.setProgressBarVisibility(View.GONE)
        view.cancleProgressBar()
        view.showDialogBox("همه چی آرومه!!", "هلپ جو خوب است", "متوجه شدم")
    }
}
