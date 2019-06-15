package com.dalisyron.data.repository

import com.dalisyron.data.datasource.UserLocalDataSource
import com.dalisyron.data.datasource.UserRemoteDataSource
import com.dalisyron.remote.dto.user.UserLoginItemEntity
import com.dalisyron.remote.dto.user.UserLoginResponseEntity
import com.dalisyron.remote.dto.user.UserRegisterItemEntity
import com.dalisyron.remote.dto.user.UserRegisterResponseEntity
import io.reactivex.Single

class UserRepository(private val userRemoteDataSource: UserRemoteDataSource,
                     private val userLocalDataSource: UserLocalDataSource) {
    fun login(userLoginItemEntity: UserLoginItemEntity) : Single<UserLoginResponseEntity> {
        return userRemoteDataSource.login(userLoginItemEntity)
    }

    fun register(userRegisterItemEntity: UserRegisterItemEntity) : Single<UserRegisterResponseEntity> {
        return userRemoteDataSource.register(userRegisterItemEntity)
    }

    fun getUser() : Single<String> {
        return userLocalDataSource.getUser()
    }
}