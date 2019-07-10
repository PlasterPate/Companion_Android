package com.dalisyron.companion.datasource

import android.content.SharedPreferences
import com.dalisyron.data.datasource.UserLocalDataSource
import io.reactivex.Single
import javax.inject.Inject

class UserLocalDataSourceImpl @Inject constructor(private val sharedPreferences: SharedPreferences) : UserLocalDataSource {

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
    }

}