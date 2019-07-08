package com.dalisyron.companion.ui.register

import com.dalisyron.data.repository.UserRepository
import com.dalisyron.remote.dto.user.UserRegisterItemEntity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class RegisterPresenter @Inject constructor(val userRepository: UserRepository) : RegisterContract.Presenter {

    lateinit var view : RegisterContract.View

    override fun onRegisterButtonClicked() {

        val firstName = view.getFirstName()
        val lastName = view.getLastName()
        val password = view.getPassword()
        val phoneNumber = view.getPhoneNumber()

        val userRegisterItemEntity = UserRegisterItemEntity(
            avatar = "default",
            email = "default@example.com",
            firstName = firstName,
            lastName = lastName,
            password = password,
            username = phoneNumber
        )

        if (validateRegisterInfo(userRegisterItemEntity)) {
            userRepository.register(userRegisterItemEntity)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ userRegisterResponseEntity ->
                    view.doneRegisterButtonSuccess()
                    view.navigateToHome()
                }, { throwable ->
                    view.stopRegisterButtonAnimation()
                    view.setRegisterButtonRadius()
                    view.showError(throwable.message?:"")
                })
        } else {
            view.stopRegisterButtonAnimation()
            view.setRegisterButtonRadius()
            view.showError("Invalid Register Info")
        }
    }

    fun validateRegisterInfo(userRegisterItemEntity: UserRegisterItemEntity) : Boolean {
        return with(userRegisterItemEntity) {
            username.isNotEmpty() && password.isNotEmpty() && firstName.isNotEmpty() && lastName.isNotEmpty()
                    && username.all { it.isDigit() } && password.length > 4
        }
    }


}