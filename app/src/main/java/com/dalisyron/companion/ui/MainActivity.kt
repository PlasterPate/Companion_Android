package com.dalisyron.companion.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dalisyron.companion.R
import com.dalisyron.companion.ui.home.HomeFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction()
            .replace(R.id.content_frame, HomeFragment())
            .commit()
    }
}
