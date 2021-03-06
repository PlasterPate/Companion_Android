package com.dalisyron.data.repository

import com.dalisyron.data.datasource.TokenLocalDataSource
import com.dalisyron.data.datasource.TokenRemoteDataSource
import com.dalisyron.data.datasource.UserLocalDataSource
import com.dalisyron.data.datasource.UserRemoteDataSource
import com.dalisyron.data.model.UserEntity
import com.dalisyron.remote.dto.user.UserLoginItemEntity
import com.dalisyron.remote.dto.user.UserRegisterItemEntity
import com.dalisyron.remote.dto.user.UserRegisterResponseEntity
import io.reactivex.Single
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userRemoteDataSource: UserRemoteDataSource,
    private val userLocalDataSource: UserLocalDataSource,
    private val tokenRemoteDataSource: TokenRemoteDataSource,
    private val tokenLocalDataSource: TokenLocalDataSource
) {
    fun login(userLoginItemEntity: UserLoginItemEntity): Single<Unit> {
        return userRemoteDataSource.login(userLoginItemEntity).flatMap { userLoginResponseEntity ->
            tokenLocalDataSource.saveAccessToken(access = userLoginResponseEntity.access)
                .flatMap { tokenLocalDataSource.saveRefreshToken(refresh = userLoginResponseEntity.refresh) }
                .flatMap { userLocalDataSource.saveUser(userLoginResponseEntity.id) }
        }
    }

    fun register(userRegisterItemEntity: UserRegisterItemEntity): Single<UserRegisterResponseEntity> {
        return userRemoteDataSource.register(userRegisterItemEntity)
    }

    fun getUser(): Single<String?> {
        return userLocalDataSource.getUser()
    }

    fun getUser(id: String): Single<UserEntity> {
        return tokenLocalDataSource.getAccessToken().flatMap { token: String ->
            userRemoteDataSource.getUser(id, token).doOnError {
                println("first error -> ${it.message}")
            }.onErrorResumeNext { it ->
                tokenLocalDataSource.getRefreshToken().doOnSuccess {
                    println("got refresh $it from local")
                }
                    .flatMap { refresh ->
                        tokenLocalDataSource.saveRefreshToken(refresh)
                    }
                    .flatMap { refresh ->
                        tokenRemoteDataSource.refreshAccessToken(refresh).doOnSuccess {
                            println("got access $it with the refresh key")
                        }.flatMap { access ->
                            tokenLocalDataSource.saveAccessToken(access)
                        }.flatMap { access ->
                            println("re: access was $access")
                            userRemoteDataSource.getUser(id, access)
                        }
                    }
            }
        }
    }
}