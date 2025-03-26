package com.example.contentproviders_th2

import android.content.pm.PackageManager
import android.database.Cursor
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import android.Manifest
import com.example.contentproviders_th2.ui.theme.ContentProviders_TH2Theme

class MainActivity : ComponentActivity() {
    private val REQUEST_READ_CONTACTS = 100
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity) // ✅ Đảm bảo gọi trước khi findViewById()
        enableEdgeToEdge()
        checkPermissions()
    }
    private fun checkPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)
            != PackageManager.PERMISSION_GRANTED) {
            // Nếu chưa có quyền, yêu cầu người dùng cấp quyền
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_CONTACTS),
                REQUEST_READ_CONTACTS
            )
        } else {
            // Nếu đã có quyền, tiến hành đọc danh bạ
            loadContacts()
        }
    }

    // Xử lý kết quả cấp quyền

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == REQUEST_READ_CONTACTS && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            // Nếu người dùng cấp quyền, tiếp tục đọc danh bạ
            loadContacts()
        }
    }
    private fun loadContacts() {
        val contactsList = ArrayList<String>()  // Danh sách lưu tên và số điện thoại
        val contentResolver = contentResolver
        val uri = ContactsContract.Contacts.CONTENT_URI

        // Truy vấn ID và tên liên hệ
        val cursor: Cursor? = contentResolver.query(
            uri,
            arrayOf(ContactsContract.Contacts._ID, ContactsContract.Contacts.DISPLAY_NAME),
            null,
            null,
            ContactsContract.Contacts.DISPLAY_NAME + " ASC"
        )

        cursor?.use {
            val idIndex = it.getColumnIndex(ContactsContract.Contacts._ID)
            val nameIndex = it.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)

            while (it.moveToNext()) {
                val contactId = it.getString(idIndex) // Lấy ID liên hệ
                val contactName = it.getString(nameIndex) // Lấy tên liên hệ

                // Truy vấn số điện thoại của liên hệ này
                val phoneCursor: Cursor? = contentResolver.query(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    arrayOf(ContactsContract.CommonDataKinds.Phone.NUMBER),
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                    arrayOf(contactId),
                    null
                )

                phoneCursor?.use { phoneIt ->
                    while (phoneIt.moveToNext()) {
                        val phoneNumber = phoneIt.getString(0) // Lấy số điện thoại
                        contactsList.add("$contactName: $phoneNumber") // Thêm vào danh sách
                    }
                }
            }
        }

        // Hiển thị danh sách lên ListView
        displayContacts(contactsList)
    }
    private fun displayContacts(contactsList: ArrayList<String>) {
        val listView = findViewById<ListView>(R.id.listViewContacts)
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, contactsList)
        listView.adapter = adapter
    }

}
