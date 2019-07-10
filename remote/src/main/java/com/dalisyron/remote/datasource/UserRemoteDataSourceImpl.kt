package com.dalisyron.remote.datasource

import com.dalisyron.data.datasource.UserRemoteDataSource
import com.dalisyron.data.model.UserEntity
import com.dalisyron.remote.api.UserService
import com.dalisyron.remote.dto.user.*
import com.dalisyron.remote.mappers.*
import io.reactivex.Single
import javax.inject.Inject

class UserRemoteDataSourceImpl @Inject constructor(private val userService: UserService) : UserRemoteDataSource {
    override fun getUser(id: String, token : String): Single<UserEntity> {
        return userService.getUser(UserGetDto(id), "Bearer $token").map {
            it.toUserEntity()
        }
    }

    override fun login(userLoginItemEntity: UserLoginItemEntity): Single<UserLoginResponseEntity> {
        return userService.login(userLoginItemEntity.toUserLoginItemDto()).map {
            it.toUserLoginResponseEntity()
        }
    }

    override fun register(userRegisterItemEntity: UserRegisterItemEntity): Single<UserRegisterResponseEntity> {
        return userService.register(userRegisterItemEntity.toUserRegisterItemDto()).map {
            it.toUserRegisterResponseEntity()
        }
    }

}