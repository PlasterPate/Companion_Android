package com.dalisyron.companion.ui.login

interface LoginContract {

    interface Presenter : LoginContract {
        fun onLoginButtonClicked()
        fun onRegisterClicked()
    }

    interface View : LoginContract {
        fun navigateToHome()
        fun navigateToRegister()
        fun showProgressBar()
        fun showError(error : String)
        fun getUserName() : String
        fun getPassword() : String
        fun stopLoginButtonAnimation()
        fun startLoginButtonAnimation()
        fun doneLoginButtonSuccess()
    }
}