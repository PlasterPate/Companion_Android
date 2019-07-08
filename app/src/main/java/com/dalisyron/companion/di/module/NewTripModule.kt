package com.dalisyron.companion.di.module

import com.dalisyron.companion.datasource.TokenLocalDataSourceImpl
import com.dalisyron.data.datasource.TokenLocalDataSource
import com.dalisyron.data.datasource.TokenRemoteDataSource
import com.dalisyron.data.datasource.TripRemoteDataSource
import com.dalisyron.remote.datasource.TokenRemoteDataSourceImpl
import com.dalisyron.remote.datasource.TripRemoteDataSourceImpl
import dagger.Module
import dagger.Provides

@Module
class NewTripModule() {

    @Provides
    fun providesTokenLocalDataSource(tokenLocalDataSource : TokenLocalDataSourceImpl) : TokenLocalDataSource {
        return tokenLocalDataSource
    }

    @Provides
    fun providesTokenRemoteDataSource(tokenRemoteDataSource : TokenRemoteDataSourceImpl) : TokenRemoteDataSource {
        return tokenRemoteDataSource
    }

    @Provides
    fun providesTripRemoteDataSource(tripRemoteDataSourceImpl: TripRemoteDataSourceImpl) : TripRemoteDataSource {
        return tripRemoteDataSourceImpl
    }
}