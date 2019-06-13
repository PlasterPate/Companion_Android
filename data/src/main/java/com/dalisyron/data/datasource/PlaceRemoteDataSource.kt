package com.dalisyron.data.datasource

interface PlaceRemoteDataSource {

    fun getAutoCompletePlaces(input : String, key : String)

}