package com.dalisyron.companion

import android.app.Application
import com.dalisyron.companion.di.component.AppComponent
import com.dalisyron.companion.di.component.DaggerAppComponent
import com.dalisyron.companion.di.module.AppModule
import dagger.Component

class CompanionApp : Application() {

    override fun onCreate() {
        super.onCreate()
        component = DaggerAppComponent.builder().appModule(AppModule(this)).build()
    }

    companion object {
        lateinit var component : AppComponent

        var userEmail : String? = null
    }
}