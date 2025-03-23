package com.example.sharepreference_th1

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.ComponentActivity
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject


class MainActivity : ComponentActivity() {
    private lateinit var txtName : EditText
    private lateinit var txtPass : EditText
    private lateinit var txtShow : TextView
    private lateinit var btnAdd : Button
    private lateinit var btnShow : Button
    private lateinit var btnXoa : Button
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        txtName=findViewById(R.id.txt_name)
        txtPass=findViewById(R.id.txt_pass)
        txtShow=findViewById(R.id.txt_show)
        btnXoa=findViewById(R.id.btn_xoa)
        btnAdd=findViewById(R.id.btn_them)
        btnShow=findViewById(R.id.btn_show)

        btnAdd.setOnClickListener{
            val tenUser = txtName.text.toString()
            val passUser = txtPass.text.toString()
            val infoUser = "$tenUser $passUser"
             PreferenceHelper(this).addUser(infoUser)

        }


        btnXoa.setOnClickListener{
           PreferenceHelper(this).deleteUser()
            txtShow.text = "Xóa thành công!"
        }
        btnShow.setOnClickListener{

            PreferenceHelper(this).showUser().let { result ->
                txtShow.text = result
            }

    }
}}

