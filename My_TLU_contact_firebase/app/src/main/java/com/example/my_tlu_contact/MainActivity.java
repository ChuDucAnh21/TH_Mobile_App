package com.example.my_tlu_contact;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.my_tlu_contact.CBNV.CBNV_Activity;
import com.example.my_tlu_contact.DonVi.DonVi_Activity;
import com.example.my_tlu_contact.User_Login.Login_Activity;
import com.example.my_tlu_contact.User_Login.detail_user_activity;

public class MainActivity<dbSqlite> extends AppCompatActivity {
   private CardView crv_dv,crv_cbnv;
   private ImageButton imgbtn_account;
   private String ROLE,ID,NAME,PHONE,ADDRESS,EMAIL,DATE;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new role_().setRole(layRole());


        crv_dv = findViewById(R.id.crv_dv);
        crv_cbnv = findViewById(R.id.crv_cbnv);
        imgbtn_account = findViewById(R.id.imgbtn_account);


        crv_dv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myintent = new Intent(MainActivity.this, DonVi_Activity.class);
                startActivity(myintent);
            }
        });
        crv_cbnv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myintent = new Intent(MainActivity.this, CBNV_Activity.class);
                startActivity(myintent);
            }
        });


        imgbtn_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showUserDialog();
            }
        });


    }
    private void showUserDialog() {
        String[] options = {"Thông tin cá nhân", "Cài đặt", "Đăng xuất"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Tùy chọn");
        builder.setItems(options, (dialog, which) -> {
            switch (which) {
                case 0:
                    Toast.makeText(this, "Thông tin cá nhân", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, detail_user_activity.class);
                    intent.putExtra("ID_USER",ID);
                    intent.putExtra("NAME_USER", NAME);
                    intent.putExtra("PHONE_USER", PHONE);
                    intent.putExtra("ADDRESS_USER", ADDRESS);
                    intent.putExtra("EMAIL_USER", EMAIL);
                    intent.putExtra("DATE_USER", DATE);
                    startActivity(intent);
                    break;
                case 1:
                    Toast.makeText(this, "Cài đặt", Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    Toast.makeText(this, "Đăng xuất", Toast.LENGTH_SHORT).show();
                    Intent intentt = new Intent(MainActivity.this, Login_Activity.class);
                    startActivity(intentt);
                    break;
            }
        });
        builder.show();
    }

    private String layRole() {
        Intent intent = getIntent();
        ROLE = intent.getStringExtra("USER_ROLE_login");
        return ROLE;
    }
}
