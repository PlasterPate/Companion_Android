package com.dalisyron.companion.ui.login

import android.util.Log
import com.dalisyron.data.repository.UserRepository
import com.dalisyron.remote.dto.user.UserLoginItemEntity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

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
                        view.doneLoginButtonSuccess()
                        view.navigateToHome()
                    },
                    { throwable ->
                        view.doneLoginButtonSuccess()               // Mock code
                        view.navigateToHome()                       // Mock code

//                        view.stopLoginButtonAnimation()           // Correct code
//                        view.setLoginButtonRadius()               // Correct code
//                        view.showError(throwable.message?:"")     // Correct code
                    }
                )
        }

    }

    fun isLoginInfoValid(userLoginItemEntity: UserLoginItemEntity): Boolean {
        return true                                                     // Mock code
//        return (userLoginItemEntity.password.isNotEmpty())            // Correct code
//                && (userLoginItemEntity.username.isNotEmpty())
    }
}