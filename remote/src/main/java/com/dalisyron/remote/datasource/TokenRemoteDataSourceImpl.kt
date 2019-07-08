package com.dalisyron.remote.datasource

import com.dalisyron.data.datasource.TokenRemoteDataSource
import com.dalisyron.remote.api.TokenService
import com.dalisyron.remote.dto.token.RefreshAccessItemDto
import io.reactivex.Single
import javax.inject.Inject

class TokenRemoteDataSourceImpl @Inject constructor(val tokenService: TokenService) : TokenRemoteDataSource {
    override fun refreshAccessToken(refresh : String): Single<String?> {
        return tokenService.refreshAccessToken(RefreshAccessItemDto(refresh)).map {
            it?.access
        }
    }
}