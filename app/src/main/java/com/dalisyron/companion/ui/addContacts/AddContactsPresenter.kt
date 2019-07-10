package com.dalisyron.companion.ui.addContacts

import com.dalisyron.data.model.ContactEntity
import com.dalisyron.data.repository.UserRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class AddContactsPresenter @Inject constructor(private val userRepository: UserRepository) : AddContactsContract.Presenter {
    lateinit var view: AddContactsContract.View

    override fun onFetchContacts() {
        val contactsAll = view.getContacts()
        contactsAll.forEach { contact ->
            userRepository.getUser(normalizePhoneNumber(contact.phoneNumber)).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({it -> view.addContact(contact)}, {it -> println(it)})
        }
    }

    override fun onContactItemClicked(contactEntity: ContactEntity) {
        view.navigateToNewTrip(contactEntity)
    }

    override fun normalizePhoneNumber(phoneNumber: String): String {
        var normalizedNumber = ""
        for (c: Char in phoneNumber.reversed()) {
            if (normalizedNumber.length > 10)
                break
            if (c.isDigit())
                normalizedNumber += c
        }
        return normalizedNumber.reversed()
    }
}