package com.dalisyron.companion.di.module

import android.content.Context
import android.content.SharedPreferences
import com.dalisyron.companion.CompanionApp
import dagger.Module
import dagger.Provides

@Module
class AppModule(val companionApp: CompanionApp) {

    @Provides
    fun providesContext() : Context {
        return companionApp.applicationContext
    }

    @Provides
    fun providesSharedPrefrences(context : Context) : SharedPreferences {
        return context.getSharedPreferences("user.data", Context.MODE_PRIVATE)
    }

}