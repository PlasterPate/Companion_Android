package com.dalisyron.companion.ui.addContacts

import java.sql.Struct

interface AddContactsContract {
    interface View : AddContactsContract{
        fun showContacts()
    }

    interface Presenter : AddContactsContract{
        fun onContactItemClicked()
        fun onFetchContacts()
        fun normalizePhoneNumber(phoneNumber : String) : String
    }
}