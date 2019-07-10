package com.dalisyron.companion.ui.companionStatus

import android.os.CountDownTimer
import com.google.android.gms.maps.model.LatLng

interface CompanionStatusContract {
    interface View : CompanionStatusContract{
        fun setPingButtonState(state : Boolean)
        fun showDialogBox(title : String, message : String, buttonText : String)
        fun setProgressBarVisibility(visibility: Int)
        fun showProgressBar()
        fun cancleProgressBar()
        fun showHelpeeSource(source : LatLng)
    }

    interface Presenter : CompanionStatusContract{
        fun onPingButtonClicked()
        fun onProgressFinished()
        fun onProgressSuccessful()
        fun onViewCreated()
    }
}