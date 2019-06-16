package com.dalisyron.companion.ui.register

interface RegisterContract {

    interface Presenter : RegisterContract {
        fun onRegisterButtonClicked()
    }

    interface View : RegisterContract {
        fun navigateToHome()
        fun showError(error : String)
        fun getFirstName() : String
        fun getLastName() : String
        fun getPhoneNumber() : String
        fun getPassword() : String
        fun getConfirmPassword() : String
    }
}