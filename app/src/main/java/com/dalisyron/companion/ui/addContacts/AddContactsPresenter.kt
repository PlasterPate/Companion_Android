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
        var normalizedNumber = ""
        for (c : Char in phoneNumber.reversed()){
            if (normalizedNumber.length > 9)
                break
            if (c.isDigit())
                normalizedNumber += c
        }
        return normalizedNumber.reversed()
    }
}