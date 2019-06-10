package com.dalisyron.companion.di.component

import com.dalisyron.companion.CompanionApp
import com.dalisyron.companion.di.module.*
import com.dalisyron.companion.ui.home.HomeFragment
import com.dalisyron.companion.ui.login.LoginFragment
import com.dalisyron.companion.ui.main.MainActivity
import com.dalisyron.companion.ui.register.RegisterFragment
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector

@Component(modules = [
    AndroidInjectionModule::class,
    AppModule::class,
    HomeModule::class,
    NetworkModule::class,
    ActivityModule::class,
    FragmentsModule::class
])
interface AppComponent : AndroidInjector<CompanionApp> {

    @Component.Factory
    abstract class Factory : AndroidInjector.Factory<CompanionApp>
}