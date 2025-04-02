package com.example.onshare2

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.ComponentActivity

class MainActivity : ComponentActivity() {
    private lateinit var txtPhone: EditText
    private lateinit var txtInfor: TextView
    private lateinit var btnsave: Button
    private lateinit var btnload: Button
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       setContentView(R.layout.main_activity)

        txtPhone = findViewById(R.id.txt_phone)
        txtInfor = findViewById(R.id.txt_info)
        btnsave = findViewById(R.id.btn_save)
        btnload = findViewById(R.id.btn_load)

        btnsave.setOnClickListener{
            val phone = txtPhone.text.toString();
            val sharedPreferences = getSharedPreferences("YeuEm", MODE_PRIVATE).edit().putString("Phone",phone).apply();


        }
        btnload.setOnClickListener{
            val phone =  getSharedPreferences("YeuEm", MODE_PRIVATE).getString("phone","")
            txtInfor.setText(phone)
        }
    }
}


