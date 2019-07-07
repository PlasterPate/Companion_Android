package com.dalisyron.data.repository

import com.dalisyron.data.datasource.UserLocalDataSource
import com.dalisyron.data.datasource.UserRemoteDataSource
import com.dalisyron.remote.dto.user.UserLoginItemEntity
import com.dalisyron.remote.dto.user.UserLoginResponseEntity
import com.dalisyron.remote.dto.user.UserRegisterItemEntity
import com.dalisyron.remote.dto.user.UserRegisterResponseEntity
import io.reactivex.Single
import io.reactivex.functions.BiFunction

class UserRepository(private val userRemoteDataSource: UserRemoteDataSource,
                     private val userLocalDataSource: UserLocalDataSource) {
    fun login(userLoginItemEntity: UserLoginItemEntity) : Single<Unit> {
        return userRemoteDataSource.login(userLoginItemEntity).flatMap {userLoginResponseEntity ->
            userLocalDataSource.saveToken(userLoginResponseEntity.access, userLoginResponseEntity.access)
                .zipWith(userLocalDataSource.saveUser(userLoginResponseEntity.id), BiFunction {
                    saveToken : Unit, saveUser : Unit -> Unit
                })
        }
    }

    fun register(userRegisterItemEntity: UserRegisterItemEntity) : Single<UserRegisterResponseEntity> {
        return userRemoteDataSource.register(userRegisterItemEntity)
    }

    fun getUser() : Single<String?> {
        return userLocalDataSource.getUser()
    }
}