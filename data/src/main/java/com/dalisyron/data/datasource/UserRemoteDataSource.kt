package com.dalisyron.data.datasource

import com.dalisyron.data.model.UserEntity
import com.dalisyron.remote.dto.user.UserLoginItemEntity
import com.dalisyron.remote.dto.user.UserLoginResponseEntity
import com.dalisyron.remote.dto.user.UserRegisterItemEntity
import com.dalisyron.remote.dto.user.UserRegisterResponseEntity
import io.reactivex.Single

interface UserRemoteDataSource {

    fun login(userLoginItemEntity: UserLoginItemEntity) : Single<UserLoginResponseEntity>

    fun register(userLoginRegisterItemEntity: UserRegisterItemEntity) : Single<UserRegisterResponseEntity>

    fun getUser(id : String, token : String) : Single<UserEntity>

}