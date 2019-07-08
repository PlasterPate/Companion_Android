package com.dalisyron.data.repository

import com.dalisyron.data.datasource.TokenLocalDataSource
import com.dalisyron.data.datasource.UserLocalDataSource
import com.dalisyron.data.datasource.UserRemoteDataSource
import com.dalisyron.remote.dto.user.UserLoginItemEntity
import com.dalisyron.remote.dto.user.UserRegisterItemEntity
import com.dalisyron.remote.dto.user.UserRegisterResponseEntity
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import javax.inject.Inject

class UserRepository @Inject constructor(private val userRemoteDataSource: UserRemoteDataSource,
                     private val userLocalDataSource: UserLocalDataSource,
                     private val tokenLocalDataSource: TokenLocalDataSource) {
    fun login(userLoginItemEntity: UserLoginItemEntity) : Single<Unit> {
        return userRemoteDataSource.login(userLoginItemEntity).flatMap {userLoginResponseEntity ->
            tokenLocalDataSource.saveAccessToken(access = userLoginResponseEntity.access)
                .flatMap { tokenLocalDataSource.saveRefreshToken(refresh = userLoginResponseEntity.refresh) }
                .flatMap { userLocalDataSource.saveUser(userLoginResponseEntity.id) }
        }
    }

    fun register(userRegisterItemEntity: UserRegisterItemEntity) : Single<UserRegisterResponseEntity> {
        return userRemoteDataSource.register(userRegisterItemEntity)
    }

    fun getUser() : Single<String?> {
        return userLocalDataSource.getUser()
    }
}