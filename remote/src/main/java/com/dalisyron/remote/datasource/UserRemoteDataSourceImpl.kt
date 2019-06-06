package com.dalisyron.remote.datasource

import com.dalisyron.data.datasource.UserRemoteDataSource
import com.dalisyron.data.model.UserLoginInfoEntity
import com.dalisyron.data.model.UserLoginResponseEntity
import com.dalisyron.data.model.UserRegisterInfoEntity
import com.dalisyron.data.model.UserRegisterResponseEntity
import com.dalisyron.remote.api.UserService
import com.dalisyron.remote.mappers.*
import io.reactivex.Single

class UserRemoteDataSourceImpl(private val userService: UserService) : UserRemoteDataSource {
    override fun login(userLoginInfoEntity: UserLoginInfoEntity): Single<UserLoginResponseEntity> {
        return userService.login(userLoginInfoEntity.toUserLoginInfoDto()).map {
            it.toUserLoginResponseEntity()
        }
    }

    override fun register(userRegisterInfoEntity: UserRegisterInfoEntity): Single<UserRegisterResponseEntity> {
        return userService.register(userRegisterInfoEntity.toUserRegisterInfoDto()).map {
            it.toUserRegisterResponseEntity()
        }
    }

}