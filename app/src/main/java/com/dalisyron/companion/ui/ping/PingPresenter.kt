package com.dalisyron.companion.ui.ping

class PingPresenter : PingContract.Presenter {
    lateinit var view: PingContract.View

    override fun onPingButtonClicked() {
        view.setPingButtonState(false)
        view.showDialogBox("پیام شما به هلپ جو ارسال شد", "...لطفا منتظر نتیجه بمانید", "OK")
    }
}