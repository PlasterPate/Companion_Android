package com.dalisyron.companion.ui.addContacts

import java.sql.Struct
import com.dalisyron.data.model.ContactEntity

interface AddContactsContract {
    interface View : AddContactsContract{
        fun showContacts()
        fun navigateToNewTrip(contactEntity: ContactEntity)
    }

    interface Presenter : AddContactsContract{
        fun onContactItemClicked(contactEntity: ContactEntity)
        fun onFetchContacts()
        fun normalizePhoneNumber(phoneNumber : String) : String
    }
}