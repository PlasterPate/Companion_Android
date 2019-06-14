package com.dalisyron.companion.di.module

import com.dalisyron.data.datasource.PlaceRemoteDataSource
import com.dalisyron.remote.datasource.PlaceRemoteDataSourceImpl
import dagger.Binds
import dagger.Module

@Module
abstract class DataModule {

    @Binds
    abstract fun bindsRemotePlaceDataSource(placeRemoteDataSourceImpl: PlaceRemoteDataSourceImpl) : PlaceRemoteDataSource

}