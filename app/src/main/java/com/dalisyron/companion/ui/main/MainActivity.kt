package com.dalisyron.companion.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dalisyron.companion.CompanionApp
import com.dalisyron.companion.R
import com.dalisyron.companion.ui.home.HomeFragment
import com.dalisyron.companion.ui.login.LoginFragment
import com.dalisyron.companion.ui.register.RegisterFragment
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
        supportFragmentManager.beginTransaction()
            .replace(R.id.content_frame, HomeFragment())
            .commit()
    }

    override fun navigateToLogin() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.content_frame, RegisterFragment())
            .commit()
    }
}
