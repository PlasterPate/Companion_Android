package com.dalisyron.companion.ui.ping

interface PingContract {
    interface View : PingContract{
        fun setPingButtonState(state : Boolean)
        fun showDialogBox(title : String, message : String, buttonText : String)

    }

    interface Presenter : PingContract{
        fun onPingButtonClicked()
    }
}