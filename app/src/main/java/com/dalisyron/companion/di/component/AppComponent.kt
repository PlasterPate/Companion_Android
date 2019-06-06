package com.dalisyron.companion.di.component

import com.dalisyron.companion.di.module.AppModule
import com.dalisyron.companion.di.module.HomeModule
import com.dalisyron.companion.di.module.NetworkModule
import com.dalisyron.companion.ui.home.HomeFragment
import com.dalisyron.companion.ui.login.LoginFragment
import com.dalisyron.companion.ui.main.MainActivity
import com.dalisyron.companion.ui.register.RegisterFragment
import dagger.Component

@Component(modules = [
    AppModule::class,
    HomeModule::class,
    NetworkModule::class
])
interface AppComponent {
    fun inject(mainActivity: MainActivity)
    fun inject(homeFragment: HomeFragment)
    fun inject(loginFragment: LoginFragment)
    fun inject(registerFragment: RegisterFragment)
}