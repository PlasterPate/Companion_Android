package com.dalisyron.companion.datasource

import android.content.SharedPreferences
import com.dalisyron.data.datasource.UserLocalDataSource
import io.reactivex.Single
import javax.inject.Inject

class UserLocalDataSourceImpl(private val sharedPreferences: SharedPreferences) : UserLocalDataSource {

    override fun getAccessToken(): Single<String?> {
        return Single.fromCallable {
            sharedPreferences.getString(ACCESS_TOKEN_KEY, "")
        }
    }

    override fun getRefreshToken(): Single<String?> {
        return Single.fromCallable {
            sharedPreferences.getString(REFRESH_TOKEN_KEY, "")
        }
    }

    override fun saveTokens(access: String, refresh: String) : Single<Unit> {
        return Single.fromCallable {
            sharedPreferences.edit().putString(ACCESS_TOKEN_KEY, access).commit()
            sharedPreferences.edit().putString(REFRESH_TOKEN_KEY, refresh).commit()
            Unit
        }
    }

    override fun getUser() : Single<String?> {
        return Single.just(sharedPreferences.getString(USER_PHONE_KEY, ""))
    }

    override fun saveUser(userName: String): Single<Unit> {
        return Single.fromCallable {
            sharedPreferences.edit().putString(USER_PHONE_KEY, userName).commit()
            Unit
        }
    }

    companion object {
        const val USER_PHONE_KEY = "user_id"
        const val ACCESS_TOKEN_KEY = "access_token_key"
        const val REFRESH_TOKEN_KEY = "refresh_token_key"
    }
}