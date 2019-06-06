package com.dalisyron.data.repository

import com.dalisyron.data.datasource.UserLocalDataSource
import com.dalisyron.data.datasource.UserRemoteDataSource
import com.dalisyron.data.model.UserLoginInfoEntity
import com.dalisyron.data.model.UserLoginResponseEntity
import com.dalisyron.data.model.UserRegisterInfoEntity
import com.dalisyron.data.model.UserRegisterResponseEntity
import io.reactivex.Single

class UserRepository(private val userRemoteDataSource: UserRemoteDataSource,
                     private val userLocalDataSource: UserLocalDataSource) {
    fun login(userLoginInfoEntity: UserLoginInfoEntity) : Single<UserLoginResponseEntity> {
        return userRemoteDataSource.login(userLoginInfoEntity)
    }

    fun register(userRegisterInfoEntity: UserRegisterInfoEntity) : Single<UserRegisterResponseEntity> {
        return userRemoteDataSource.register(userRegisterInfoEntity)
    }

    fun getUser() : Single<String> {
        return userLocalDataSource.getUser()
    }
}