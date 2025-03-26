package com.example.my_tlu_contact;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.my_tlu_contact.Activate.activity_add_CB;
import com.example.my_tlu_contact.Database.dbSQLite;
import com.example.my_tlu_contact.User_Login.Login_Activity;

public class Detail_cbnv_Activity extends AppCompatActivity {
    private TextView dt_nameCB,dt_sdtCB,dt_addressCB,dt_dateCB,dt_idCB;
     private ImageButton btn_back,btn_callCB,btn_messCB;
     private Button btnSuaCB,btnXoaCB;
    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_cbnv);

        dt_idCB =(TextView) findViewById(R.id.dt_manv_cbnv);
        dt_nameCB = (TextView)findViewById(R.id.dt_ten_cbnv);
        dt_sdtCB =(TextView) findViewById(R.id.dt_sdt_cbnv);
        dt_addressCB =(TextView) findViewById(R.id.dt_diachi_cbnv);
        dt_dateCB =(TextView) findViewById(R.id.dt_date_cbnv);
        btnSuaCB = findViewById(R.id.btn_suaItemCB);
        btnXoaCB = findViewById(R.id.btn_xoaItemCB);
        btn_back = findViewById(R.id.dt_arrow_backCBNV);
        btn_messCB = findViewById(R.id.btn_mess_CB);
        btn_callCB = findViewById(R.id.btn_callCB);

        String data = Login_Activity.DataHolder.getInstance().getData();
        if (data != null) {
            if (data.equals("ADMIN")) {
                btnSuaCB.setVisibility(View.VISIBLE);
                btnXoaCB.setVisibility(View.VISIBLE);
            } else if (data.equals("USER")) {
                btnSuaCB.setVisibility(View.GONE);
                btnXoaCB.setVisibility(View.GONE);
            }
        }

       //Nhan du lieu tu Intent
        Intent intent = getIntent();
        if(intent != null){
            dt_idCB.setText(intent.getStringExtra("ID"));
            dt_dateCB.setText(intent.getStringExtra("DATE"));
            dt_nameCB.setText(intent.getStringExtra("NAME"));
            dt_sdtCB.setText(intent.getStringExtra("PHONE"));
            dt_addressCB.setText(intent.getStringExtra("ADDRESS"));

        }

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        btn_callCB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + dt_sdtCB.getText()));
                startActivity(intent);

            }
        });

        btn_messCB.setOnClickListener(new View.OnClickListener() {
              @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                     intent.setData(Uri.parse("sms:"+dt_sdtCB.getText())); // Thay bằng số điện thoại cần gửi
                     intent.putExtra("sms_body", "Xin chào, đây là tin nhắn tự động!"); // Nội dung tin nhắn
                      startActivity(intent);
                 }
             }
        );

        btnSuaCB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Detail_cbnv_Activity.this, activity_add_CB.class);
                intent.putExtra("KEY_UI", "EDIT");
                intent.putExtra("ID_IT", dt_idCB.getText());
                intent.putExtra("NAME_IT", dt_nameCB.getText());
                intent.putExtra("PHONE_IT", dt_sdtCB.getText());
                intent.putExtra("ADDRESS_IT", dt_addressCB.getText());
                intent.putExtra("DATE_IT", dt_dateCB.getText());
                startActivity(intent);
            }
        });
        btnXoaCB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbSQLite db = new dbSQLite(Detail_cbnv_Activity.this);
                db.open();
                db.deleteCBNV(dt_idCB.getText().toString());
                finish();
            }
        });
    }


}