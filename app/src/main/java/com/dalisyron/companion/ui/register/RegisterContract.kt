package com.dalisyron.companion.ui.register

interface RegisterContract {

    interface Presenter : RegisterContract {
        fun onRegisterButtonClicked()
    }

    interface View : RegisterContract {
        fun navigateToHome()
        fun showError()
    }
}