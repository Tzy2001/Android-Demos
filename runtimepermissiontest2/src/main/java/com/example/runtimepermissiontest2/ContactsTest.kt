package com.example.runtimepermissiontest2


import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.PersistableBundle
import android.provider.ContactsContract
import android.provider.ContactsContract.CommonDataKinds.Phone
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.runtimepermissiontest2.databinding.ActivityContactsTestBinding


class ContactsTest :AppCompatActivity(){
    private val contactsList =ArrayList<String>()
    private lateinit var adapter:ArrayAdapter<String>

    private val binding by lazy { ActivityContactsTestBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        adapter=ArrayAdapter(this,android.R.layout.simple_list_item_1,contactsList)
        binding.contactsView.adapter=adapter
        if (ContextCompat.checkSelfPermission(this,Manifest.permission.READ_CONTACTS)
        !=PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,
            arrayOf(Manifest.permission.READ_CONTACTS),1)

        }else{
            readContacts()
        }
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            1 -> {
                if (grantResults.isNotEmpty() &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED
                ) {
                    readContacts()
                } else {
                    Toast.makeText(this, "You denied the permission", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun readContacts(){
        contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,null,null,null)?.apply {
            while (moveToNext()){

                val displayNameIndex=getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)
                val numberIndex=getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
             /*   val displayName=getString(getColumnIndex(
                    ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME
                ))
                val number=getString(getColumnIndex(
                    ContactsContract.CommonDataKinds.Phone.NUMBER
                ))*/
                var displayName=""
                var number=""
                if (displayNameIndex>=0&&numberIndex>=0){
                     displayName=getString(displayNameIndex)
                     number=getString(numberIndex)
                }
                contactsList.add("$displayName\n$number")
            }
            adapter.notifyDataSetChanged()
            close()
        }
    }
}