package com.dalisyron.companion.ui.addContacts

class AddContactsPresenter : AddContactsContract.Presenter{
    lateinit var view : AddContactsContract.View

    override fun onFetchContacts() {
        view.showContacts()
    }

    override fun onContactItemClicked() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}