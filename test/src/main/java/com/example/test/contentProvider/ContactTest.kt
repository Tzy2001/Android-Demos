package com.example.test.contentProvider

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.test.R
import com.example.test.databinding.ActivityContactTestBinding

class ContactTest : AppCompatActivity() {
    private val binding by lazy { ActivityContactTestBinding.inflate(layoutInflater) }
    private val contactsList = ArrayList<String>()
    private lateinit var adapter: ArrayAdapter<String>

    private val mContext = this


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, contactsList)
        binding.contactsView.adapter = adapter
        if (ContextCompat.checkSelfPermission(
                mContext,
                Manifest.permission.READ_CONTACTS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_CONTACTS), 1)
        } else {
            readContacts()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode){
            1->{
                if (grantResults!=null && grantResults[0] ==PackageManager.PERMISSION_GRANTED){
                    readContacts()
                } else{
                  Toast.makeText(mContext,"您未同意授权通讯录",Toast.LENGTH_SHORT).show()
                }
            }

        }
    }

    @SuppressLint("Range")
    private fun readContacts() {
        contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            null,
            null,
            null,
            null
        )?.apply {
            while (moveToNext()) {
//                获取联系人姓名
                val displayName =
                    getString(getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
//                获取联系人手机号
                val displayPhone =
                    getString(getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                contactsList.add("$displayName\\$displayPhone")
            }
            adapter.notifyDataSetChanged()
            close()
        }
    }
}