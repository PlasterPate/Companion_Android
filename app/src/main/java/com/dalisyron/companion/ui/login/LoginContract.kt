package com.dalisyron.companion.ui.login

interface LoginContract {

    interface Presenter : LoginContract {
        fun onLoginButtonClicked()
    }

    interface View : LoginContract {
        fun navigateToHome()
        fun navigateToRegister()
        fun showProgressBar()
        fun showError()
    }
}