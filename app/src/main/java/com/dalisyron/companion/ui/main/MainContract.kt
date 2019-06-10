package com.dalisyron.companion.ui.main

interface MainContract {

    interface Presenter : MainContract {
        fun onCreate()
    }

    interface View : MainContract {
        fun navigateToHome()
        fun navigateToLogin()
    }
}