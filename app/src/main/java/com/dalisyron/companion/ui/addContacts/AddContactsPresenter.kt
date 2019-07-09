package com.dalisyron.companion.ui.addContacts

import com.dalisyron.data.model.ContactEntity

class AddContactsPresenter : AddContactsContract.Presenter{
    lateinit var view : AddContactsContract.View

    override fun onFetchContacts() {
        view.showContacts()
    }

    override fun onContactItemClicked(contactEntity: ContactEntity) {
        view.navigateToNewTrip(contactEntity)
    }

    override fun normalizePhoneNumber(phoneNumber: String) : String {
        val number = phoneNumber.reversed()
        var normalizedNumber : String = ""
        for (c : Char in number){
            if (normalizedNumber.length > 10)
                break
            if (c.isDigit())
                normalizedNumber += c
        }
        return normalizedNumber
    }
}