package com.dalisyron.data.datasource

import io.reactivex.Single

interface UserLocalDataSource {

    fun getUser(): Single<String?>

    fun saveToken(access : String, refresh : String) : Single<Unit>

    fun saveUser(userName : String) : Single<Unit>
}