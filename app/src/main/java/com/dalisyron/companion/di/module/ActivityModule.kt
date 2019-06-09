package com.dalisyron.companion.di.module

import com.dalisyron.companion.ui.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {

    @ContributesAndroidInjector
    abstract fun mainActivity() : MainActivity

}