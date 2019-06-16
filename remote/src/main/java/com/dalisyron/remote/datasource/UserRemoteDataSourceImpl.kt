package com.dalisyron.remote.datasource

import com.dalisyron.data.datasource.UserRemoteDataSource
import com.dalisyron.remote.api.UserService
import com.dalisyron.remote.dto.user.UserLoginItemEntity
import com.dalisyron.remote.dto.user.UserLoginResponseEntity
import com.dalisyron.remote.dto.user.UserRegisterItemEntity
import com.dalisyron.remote.dto.user.UserRegisterResponseEntity
import com.dalisyron.remote.mappers.*
import io.reactivex.Single
import javax.inject.Inject

class UserRemoteDataSourceImpl @Inject constructor(private val userService: UserService) : UserRemoteDataSource {
    override fun login(userLoginInfoEntity: UserLoginInfoEntity): Single<UserLoginResponseEntity> {
        return userService.login(userLoginInfoEntity.toUserLoginInfoDto()).map {
            it.toUserLoginResponseEntity()
        }
    }

    override fun register(userRegisterItemEntity: UserRegisterItemEntity): Single<UserRegisterResponseEntity> {
        return userService.register(userRegisterItemEntity.toUserRegisterItemDto()).map {
            it.toUserRegisterResponseEntity()
        }
    }

}