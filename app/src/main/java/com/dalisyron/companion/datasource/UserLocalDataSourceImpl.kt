package com.dalisyron.companion.datasource

import android.content.SharedPreferences
import com.dalisyron.data.datasource.UserLocalDataSource
import io.reactivex.Single
import javax.inject.Inject

class UserLocalDataSourceImpl(private val sharedPreferences: SharedPreferences) : UserLocalDataSource {
    override fun getUser() : Single<String> {
        return Single.just(sharedPreferences.getString(USER_EMAIL_KEY, ""))
    }

    companion object {
        const val USER_EMAIL_KEY = "user_id"
    }
}