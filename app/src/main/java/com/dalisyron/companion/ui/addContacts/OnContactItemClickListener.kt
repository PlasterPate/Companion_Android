package com.dalisyron.companion.ui.addContacts

import com.dalisyron.data.model.ContactEntity

interface OnContactItemClickListener {

    fun onItemClicked(contactEntity: ContactEntity)

}