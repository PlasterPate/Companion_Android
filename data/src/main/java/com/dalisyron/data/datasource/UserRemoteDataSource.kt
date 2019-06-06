package com.dalisyron.data.datasource

import com.dalisyron.data.model.UserLoginInfoEntity
import com.dalisyron.data.model.UserLoginResponseEntity
import com.dalisyron.data.model.UserRegisterInfoEntity
import com.dalisyron.data.model.UserRegisterResponseEntity
import io.reactivex.Single

interface UserRemoteDataSource {

    fun login(userLoginInfoEntity: UserLoginInfoEntity) : Single<UserLoginResponseEntity>

    fun register(userRegisterInfoEntity: UserRegisterInfoEntity) : Single<UserRegisterResponseEntity>
}