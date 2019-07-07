package com.dalisyron.companion.ui.addContacts

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dalisyron.companion.R
import com.dalisyron.data.model.ContactEntity
import kotlinx.android.synthetic.main.fragment_add_contacts.*


class AddContactsFragment : Fragment(), AddContactsContract.View {
    override fun showContacts() {
        val contactsList : ArrayList<ContactEntity> = ArrayList()
        val cursor = this.activity?.contentResolver?.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            null, null, null, null)

        if (cursor != null) {
            while (cursor.moveToNext()){
                contactsList.add(ContactEntity(cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)),
                    cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)) ))

            }
            cursor.close()
        }

        val adapter = ContactsAdapter(contactsList)

        add_contacts_recycler_view.adapter = adapter
        add_contacts_recycler_view.layoutManager = LinearLayoutManager(this.context, RecyclerView.VERTICAL, false)
    }

    val REQUEST_CONTACT_CODE = 1

    private val presenter: AddContactsPresenter by lazy {
        AddContactsPresenter().apply {
            view = this@AddContactsFragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_add_contacts, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (ActivityCompat.checkSelfPermission(this.requireContext(), Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(this.requireActivity(),
                arrayOf<String>(Manifest.permission.READ_CONTACTS),
                REQUEST_CONTACT_CODE)
        }else{
            presenter.onFetchContacts()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == REQUEST_CONTACT_CODE)
            presenter.onFetchContacts()
    }


}