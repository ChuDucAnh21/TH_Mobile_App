package com.example.sqlite_th1

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.activity.ComponentActivity

import com.example.sqlite_th1.Database.databaseUser


class MainActivity : ComponentActivity() {
    private lateinit var  btnAdd: Button;
    private lateinit var  btnShow: Button;
    private lateinit var  btnXoa: Button;
    private lateinit var  btnUpdate: Button;
    private lateinit var  txtName: EditText;
    private lateinit var  txtPhone: EditText;
    private lateinit var  lv_user: ListView;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        txtName = findViewById(R.id.edt_name)
        txtPhone = findViewById(R.id.edt_phone)
        btnAdd = findViewById(R.id.btn_them)
        btnShow = findViewById(R.id.btn_show)
        btnXoa = findViewById(R.id.btn_xoa)
        lv_user = findViewById(R.id.lv_user)
        btnUpdate = findViewById(R.id.btn_sua)
        var db = databaseUser(this)


            val dataList = db.showData();
            val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, dataList)

          btnShow.setOnClickListener {
          lv_user.adapter = adapter
        }


        btnAdd.setOnClickListener {
            val name = txtName.text.toString()
            val phone = txtPhone.text.toString()
            if (name.isNotEmpty() && phone.isNotEmpty()) {
                db.addUser(name, phone)
                txtName.setText("")
                txtPhone.setText("")
                val dataList = db.showData();
                adapter.clear()
                adapter.addAll(dataList)
                adapter.notifyDataSetChanged()
            } else {
                Toast.makeText(this, "Please enter name and phone", Toast.LENGTH_SHORT).show()
            }
        }
        lv_user.setOnItemClickListener { parent, view, position, id ->
            val selectedItem = parent.getItemAtPosition(position) as String
            val parts = selectedItem.split(" - ")
            txtName.setText(parts[0])
            txtPhone.setText(parts[1])
        }

        btnXoa.setOnClickListener {
            val phone = txtPhone.text.toString()
            if (phone.isNotEmpty()) {
                db.deleteUser(phone)
                txtName.setText("")
                txtPhone.setText("")
                val dataList = db.showData();
                adapter.clear()
                adapter.addAll(dataList)
                adapter.notifyDataSetChanged()
            } else {
                Toast.makeText(this, "Please enter name to delete", Toast.LENGTH_SHORT).show()
            }
        }
        btnUpdate.setOnClickListener {
            val name = txtName.text.toString()
            val phone = txtPhone.text.toString()
            if (name.isNotEmpty() && phone.isNotEmpty()) {
                db.updateUser(name, phone)
                txtName.setText("")
                txtPhone.setText("")
                val dataList = db.showData();
                adapter.clear()
                adapter.addAll(dataList)
              //  adapter.notifyDataSetChanged()
            } else {
                Toast.makeText(this, "Please enter name and phone", Toast.LENGTH_SHORT).show()
            }
        }


    }
}

