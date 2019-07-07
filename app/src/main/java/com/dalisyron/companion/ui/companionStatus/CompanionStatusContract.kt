package com.dalisyron.companion.ui.companionStatus

import android.os.CountDownTimer

interface CompanionStatusContract {
    interface View : CompanionStatusContract{
        fun setPingButtonState(state : Boolean)
        fun showDialogBox(title : String, message : String, buttonText : String)
        fun setProgressBarVisibility(visibility: Int)
        fun showProgressBar()
        fun cancleProgressBar()
    }

    interface Presenter : CompanionStatusContract{
        fun onPingButtonClicked()
        fun onProgressFinished()
        fun onProgressSuccessful()
    }
}