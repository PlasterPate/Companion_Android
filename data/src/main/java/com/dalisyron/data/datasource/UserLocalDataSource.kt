package com.dalisyron.data.datasource

import io.reactivex.Single

interface UserLocalDataSource {

    fun getUser(): Single<String?>

    fun saveUser(userName: String): Single<Unit>

}