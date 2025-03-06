package com.example.my_tlu_contact;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.my_tlu_contact.CBNV.CBNV_Activity;
import com.example.my_tlu_contact.DonVi.DonVi_Activity;

public class MainActivity extends AppCompatActivity {
   private CardView crv_dv,crv_cbnv;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        crv_dv = findViewById(R.id.crv_dv);
        crv_cbnv = findViewById(R.id.crv_cbnv);
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

    }
}