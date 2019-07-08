package com.dalisyron.companion.ui.addContacts

import com.dalisyron.data.model.ContactEntity

interface AddContactsContract {
    interface View : AddContactsContract{
        fun showContacts()
        fun navigateToNewTrip(contactEntity: ContactEntity)
    }

    interface Presenter : AddContactsContract{
        fun onContactItemClicked(contactEntity: ContactEntity)
        fun onFetchContacts()
    }
}