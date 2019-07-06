package com.dalisyron.companion.ui.addContacts

interface AddContactsContract {
    interface View : AddContactsContract{
        fun showContacts()
    }

    interface Presenter : AddContactsContract{
        fun onContactItemClicked()
        fun onFetchContacts()
    }
}