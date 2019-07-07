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

}