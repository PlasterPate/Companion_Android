package com.dalisyron.data.repository

import com.dalisyron.data.datasource.TokenLocalDataSource
import com.dalisyron.data.datasource.TokenRemoteDataSource
import com.dalisyron.data.datasource.TripRemoteDataSource
import com.dalisyron.data.model.TripItemEntity
import com.dalisyron.data.model.TripResponseEntity
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject

class TripRepository @Inject constructor(
    private val tripRemoteDataSource: TripRemoteDataSource,
    private val tokenRemoteDataSource: TokenRemoteDataSource,
    private val tokenLocalDataSource: TokenLocalDataSource
) {

    fun createNewTrip(tripItemEntity: TripItemEntity): Single<TripResponseEntity> {
        return tokenLocalDataSource.getAccessToken().flatMap { token: String ->
            tripRemoteDataSource.createNewTrip(tripItemEntity, token).doOnError {
                println("first error -> ${it.message}")
            }.onErrorResumeNext {
                it -> tokenLocalDataSource.getRefreshToken().doOnSuccess {
                    println("got refresh $it from local")
                }
                .flatMap { refresh ->
                    tokenLocalDataSource.saveRefreshToken(refresh)
                }
                .flatMap { refresh ->
                    tokenRemoteDataSource.refreshAccessToken(refresh).doOnSuccess {
                        println("got access $it with the refresh key")
                    }.flatMap {access ->
                        tokenLocalDataSource.saveAccessToken(access)
                    }.flatMap { access ->
                        println("re: access was $access")
                        tripRemoteDataSource.createNewTrip(tripItemEntity, access)
                    }
                }
            }
        }
    }

}