package com.dalisyron.companion.datasource

import android.content.SharedPreferences
import com.dalisyron.data.datasource.UserLocalDataSource
import io.reactivex.Single
import javax.inject.Inject

class UserLocalDataSourceImpl(private val sharedPreferences: SharedPreferences) : UserLocalDataSource {
    override fun getUser() : Single<String?> {
        val userEmail : String? = sharedPreferences.getString(USER_EMAIL_KEY, null)
        return Single.just(userEmail)
    }

    companion object {
        const val USER_EMAIL_KEY = "user_id"
    }
}