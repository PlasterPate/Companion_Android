package com.dalisyron.companion.di.module

import android.content.Context
import android.content.SharedPreferences
import com.dalisyron.companion.CompanionApp
import dagger.Module
import dagger.Provides

@Module
class AppModule {

    @Provides
    fun providesContext(app : CompanionApp) : Context {
        return app.applicationContext
    }

    @Provides
    fun providesSharedPrefrences(context : Context) : SharedPreferences {
        return context.getSharedPreferences("user.data", Context.MODE_PRIVATE)
    }

}