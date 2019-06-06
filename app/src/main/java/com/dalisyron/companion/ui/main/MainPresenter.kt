package com.dalisyron.companion.ui.main

import com.dalisyron.companion.CompanionApp
import com.dalisyron.data.repository.UserRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainPresenter(val userRepository: UserRepository) : MainContract.Presenter {

    lateinit var view : MainContract.View

    override fun onCreate() {
        userRepository.getUser().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { userEmail : String? ->
                when(userEmail) {
                    null -> {
                        view.navigateToLogin()
                    }
                    else -> {
                        CompanionApp.userEmail = userEmail
                        view.navigateToHome()
                    }
                }
            }
    }

}