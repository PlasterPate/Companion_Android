package com.dalisyron.companion.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dalisyron.companion.CompanionApp
import com.dalisyron.companion.R
import com.dalisyron.companion.ui.home.HomeFragment
import com.dalisyron.companion.ui.login.LoginFragment
import com.dalisyron.data.repository.UserRepository
import javax.inject.Inject

class MainActivity : AppCompatActivity(), MainContract.View {

    @Inject
    lateinit var userRepository : UserRepository

    val presenter : MainPresenter by lazy {
        MainPresenter(userRepository).apply {
            view = this@MainActivity
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        CompanionApp.component.inject(this)
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
            .replace(R.id.content_frame, LoginFragment())
            .commit()
    }
}
