package com.example.my_tlu_contact.User_Login;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.my_tlu_contact.R;

public class detail_user_activity extends AppCompatActivity {
 private Button btn_backusser;
 private TextView dt_nameuser,dt_sdtuser,dt_addressuser,dt_dateuser,dt_iduser,dt_emailuser;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_detail_user);


        dt_iduser =(TextView) findViewById(R.id.dt_iduser);
        dt_nameuser = (TextView)findViewById(R.id.dt_nameuser);
        dt_sdtuser =(TextView) findViewById(R.id.dt_sdtuser);
        dt_addressuser =(TextView) findViewById(R.id.dt_diachiuser);
        dt_dateuser =(TextView) findViewById(R.id.dt_dateuser);
        dt_emailuser =(TextView) findViewById(R.id.dt_emailuser);
        btn_backusser = findViewById(R.id.btnbackuser);
        btn_backusser.setOnClickListener(v -> {
            finish();
        });

        Intent intent = getIntent();
        if(intent != null){
            dt_iduser.setText(intent.getStringExtra("ID_USER"));
            dt_dateuser.setText(intent.getStringExtra("DATE_USER"));
            dt_nameuser.setText(intent.getStringExtra("NAME_USER"));
            dt_sdtuser.setText(intent.getStringExtra("PHONE_USER"));
            dt_addressuser.setText(intent.getStringExtra("ADDRESS_USER"));
            dt_emailuser.setText(intent.getStringExtra("EMAIL_USER"));
        }


    }
}