package com.dalisyron.companion.ui.login

import android.util.Log
import android.widget.Toast
import com.dalisyron.data.repository.UserRepository
import com.dalisyron.remote.dto.user.UserLoginItemEntity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException
import javax.inject.Inject
import kotlin.concurrent.thread

class LoginPresenter @Inject constructor(val userRepository: UserRepository) : LoginContract.Presenter {
    override fun onRegisterClicked() {
        view.navigateToRegister()
    }

    lateinit var view: LoginContract.View

    override fun onLoginButtonClicked() {

        val password = view.getPassword()
        val userName = view.getUserName()

        Log.i("UserName, Password = ", "$userName $password")

        val userLoginItemEntity = UserLoginItemEntity(userName, password)

        if (isLoginInfoValid(userLoginItemEntity)) {
            userRepository.login(userLoginItemEntity)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { userLoginResponseEntity ->
                        view.navigateToHome()
                    },
                    { throwable ->
                        view.showError(throwable.message?:"")
                    }
                )
        }
    }

    fun isLoginInfoValid(userLoginItemEntity: UserLoginItemEntity): Boolean {
        return (userLoginItemEntity.password.isNotEmpty())
                && (userLoginItemEntity.username.isNotEmpty())
    }
}