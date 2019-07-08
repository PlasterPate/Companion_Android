package com.dalisyron.companion.ui.main

import android.os.Bundle
import com.dalisyron.companion.R
import com.dalisyron.companion.ui.companionStatus.CompanionStatusFragment
import com.dalisyron.companion.ui.home.HomeFragment
import com.dalisyron.companion.ui.login.LoginFragment
import com.dalisyron.data.repository.UserRepository
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity(), MainContract.View {

    @Inject
    lateinit var userRepository : UserRepository

    val presenter : MainPresenter by lazy {
        MainPresenter(userRepository).apply {
            view = this@MainActivity
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        presenter.onCreate()
    }

    override fun navigateToHome() {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.setCustomAnimations(R.anim.test,R.anim.test)
        fragmentTransaction.replace(R.id.content_frame, HomeFragment())
        .commit()
    }

    override fun navigateToLogin() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.content_frame, CompanionStatusFragment())
            .commit()
    }
}
