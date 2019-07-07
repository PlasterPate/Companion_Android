package com.dalisyron.companion.ui.addContacts

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.dalisyron.companion.R
import com.dalisyron.data.model.ContactEntity

class ContactsAdapter(val contactsList : ArrayList<ContactEntity>) : RecyclerView.Adapter<ContactsAdapter.ViewHolder>() {

    lateinit var onContactItemClickListener: OnContactItemClickListener

    override fun getItemCount(): Int {
        return contactsList.size
    }

    override fun onBindViewHolder(holder: ContactsAdapter.ViewHolder, position: Int) {
        holder.bindItem(contactsList[position], onContactItemClickListener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactsAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_contact, parent, false)
        return ViewHolder(view)
    }

    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        fun bindItem(contactEntity: ContactEntity, onContactItemClickListener: OnContactItemClickListener) {
            val textName = itemView.findViewById<AppCompatTextView>(R.id.contact_name)
            val textPhoneNumber = itemView.findViewById<AppCompatTextView>(R.id.contact_phone_number)

            textName.text = contactEntity.name
            textPhoneNumber.text = contactEntity.phoneNumber
            itemView.setOnClickListener {
                onContactItemClickListener.onItemClicked(contactEntity)
            }
        }

    }
}