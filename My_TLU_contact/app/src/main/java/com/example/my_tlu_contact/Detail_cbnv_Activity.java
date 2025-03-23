package com.example.my_tlu_contact;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.my_tlu_contact.DonVi.DonVi_Activity;

public class Detail_Activity extends AppCompatActivity {
    private TextView dt_name,dt_sdt,dt_address_ma;
     private Button btn_back;
    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        dt_sdt =(TextView) findViewById(R.id.dt_sdt);
        dt_name = (TextView)findViewById(R.id.dt_ten);
        dt_address_ma =(TextView) findViewById(R.id.dt_diachi_macb);


       //Nhan du lieu tu Intent
        Intent intent = getIntent();
        if(intent != null){
            dt_name.setText(intent.getStringExtra("NAME"));
            dt_sdt.setText(intent.getStringExtra("PHONE"));
            dt_address_ma.setText(intent.getStringExtra("ADDRESS_MA"));

        }
        btn_back = findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}