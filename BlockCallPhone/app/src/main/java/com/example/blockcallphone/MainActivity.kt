package com.example.blockcallphone
import android.Manifest

import android.annotation.SuppressLint
import android.content.Context
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.blockcallphone.ui.theme.BlockCallPhoneTheme
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : ComponentActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: BlockedNumbersAdapter
    private lateinit var addButton: FloatingActionButton
    private val blockedNumbers = mutableListOf<String>()

    companion object {
        private const val PERMISSIONS_REQUEST_CODE = 100
        private val REQUIRED_PERMISSIONS = arrayOf( Manifest.permission.READ_PHONE_STATE,Manifest.permission.READ_CALL_LOG, Manifest.permission.ANSWER_PHONE_CALLS, Manifest.permission.CALL_PHONE

        )
    }

@SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        // Kiểm tra và yêu cầu quyền
        if (!hasPermissions()) {
            requestPermissions()
        }

        // Khởi tạo RecyclerView
        recyclerView = findViewById(R.id.rcv)
        recyclerView.layoutManager = LinearLayoutManager(this)

        loadBlockedNumbers()

        adapter = BlockedNumbersAdapter(blockedNumbers) { position ->
            removeBlockedNumber(position)
        }
        recyclerView.adapter = adapter

        // Nút thêm số điện thoại mới
        addButton = findViewById(R.id.add_btn)
        addButton.setOnClickListener {
           setContent {
                showAddNumberDialog()
            }

        }
    }
    private fun hasPermissions(): Boolean {
        return REQUIRED_PERMISSIONS.all {
            ContextCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_GRANTED
        }
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS, PERMISSIONS_REQUEST_CODE)
    }

    private fun loadBlockedNumbers() {
        val sharedPrefs = getSharedPreferences("BlockedNumbers", MODE_PRIVATE)
        val numbersSet = sharedPrefs.getStringSet("numbers", setOf()) ?: setOf()
        blockedNumbers.addAll(numbersSet)
    }

    private fun saveBlockedNumbers() {
        val sharedPrefs = getSharedPreferences("BlockedNumbers", MODE_PRIVATE)
        val editor = sharedPrefs.edit()
        editor.putStringSet("numbers", blockedNumbers.toSet())
        editor.apply()
    }


    @Composable
    private fun showAddNumberDialog() {
        val context = LocalContext.current // Lấy context từ Composable
        val dialogView = LayoutInflater.from(context).inflate(R.layout.activity_add_number, null)
        val editText = dialogView.findViewById<EditText>(R.id.edt_phone)

        val dialog = AlertDialog.Builder(context)
            .setTitle("Thêm số điện thoại")
            .setView(dialogView)
            .setPositiveButton("Thêm", null)
            .setNegativeButton("Hủy", null)
            .create()

        dialog.show()

        // Xử lý nút Thêm
        dialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener  {
            val number = editText.text.toString().trim()

            if (number.isNotEmpty()) {
                if (number in blockedNumbers) {
                    Toast.makeText(context, "Số điện thoại đã tồn tại", Toast.LENGTH_SHORT).show()
                } else {
                    addBlockedNumber(number)
                    dialog.dismiss()
                }
            } else {
                Toast.makeText(context, "Nhập số ddienj thoai" +
                        "", Toast.LENGTH_SHORT).show()
            }
        }
    }

    @Composable
    fun Builder(x0: Context) {
        TODO("Not yet implemented")
    }

    @Composable
    fun Builder(x0: Nothing?) {
        TODO("Not yet implemented")
    }

    private fun addBlockedNumber(number: String) {
        blockedNumbers.add(number)
        adapter.notifyItemInserted(blockedNumbers.size - 1)
        saveBlockedNumbers()
        val context = null
        Toast.makeText(context, "Đã thêm số $number", Toast.LENGTH_SHORT).show()
    }

    private fun removeBlockedNumber(position: Int) {
        val number = blockedNumbers[position]
        blockedNumbers.removeAt(position)
        adapter.notifyItemRemoved(position)
        saveBlockedNumbers()
        val context = null
        Toast.makeText(context, "Đã xóa số $number ", Toast.LENGTH_SHORT).show()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        permissions
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSIONS_REQUEST_CODE) {
            if (grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
                val context = null
                Toast.makeText(context, "Quyền đã cấp", Toast.LENGTH_SHORT).show()
            } else {
                val context = null
                Toast.makeText(context, "Quyền bị từ chối", Toast.LENGTH_SHORT).show()
            }
        }
    }

}


