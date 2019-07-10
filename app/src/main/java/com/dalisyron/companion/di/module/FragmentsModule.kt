package com.dalisyron.companion.di.module

import com.dalisyron.companion.ui.addContacts.AddContactsFragment
import com.dalisyron.companion.ui.home.HomeFragment
import com.dalisyron.companion.ui.login.LoginFragment
import com.dalisyron.companion.ui.newtrip.NewTripFragment
import com.dalisyron.companion.ui.register.RegisterFragment
import com.dalisyron.companion.ui.search.SearchFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentsModule {

    @ContributesAndroidInjector
    abstract fun homeFragment() : HomeFragment

    @ContributesAndroidInjector
    abstract fun loginFragment() : LoginFragment

    @ContributesAndroidInjector
    abstract fun registerFragment() : RegisterFragment

    @ContributesAndroidInjector
    abstract fun searchFragment() : SearchFragment

    @ContributesAndroidInjector
    abstract fun newTripFragment() : NewTripFragment

    @ContributesAndroidInjector
    abstract fun addContactsFragment() : AddContactsFragment


}