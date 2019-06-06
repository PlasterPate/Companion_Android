package com.dalisyron.companion.di.module

import android.content.SharedPreferences
import com.dalisyron.companion.datasource.UserLocalDataSourceImpl
import com.dalisyron.data.datasource.UserLocalDataSource
import com.dalisyron.data.datasource.UserRemoteDataSource
import com.dalisyron.data.repository.UserRepository
import com.dalisyron.remote.api.UserService
import com.dalisyron.remote.datasource.UserRemoteDataSourceImpl
import dagger.Module
import dagger.Provides

@Module
class HomeModule {

    @Provides
    fun providesUserRemoteDataSource(userService: UserService): UserRemoteDataSource {
        return UserRemoteDataSourceImpl(userService)
    }

    @Provides
    fun providesUserLocalDataSource(sharedPreferences: SharedPreferences): UserLocalDataSource {
        return UserLocalDataSourceImpl(sharedPreferences)
    }

    @Provides
    fun providesUserRepository(
        userRemoteDataSource: UserRemoteDataSource,
        userLocalDataSource: UserLocalDataSource
    ): UserRepository {
        return UserRepository(userRemoteDataSource, userLocalDataSource)
    }
}