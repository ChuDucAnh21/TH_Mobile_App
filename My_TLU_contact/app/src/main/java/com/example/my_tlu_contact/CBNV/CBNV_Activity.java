package com.example.my_tlu_contact.CBNV;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.my_tlu_contact.Adapter.cbnv_Adapter;
import com.example.my_tlu_contact.Adapter.dv_Adapter;
import com.example.my_tlu_contact.R;

public class CBNV_Activity extends AppCompatActivity {
   private RecyclerView rcv_cbnv;
   Button btnback;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cbnv);
        CBNV[] cbnv = {
                new CBNV("001", "Nguyen Thi Hoa", "0869380901"),
                new CBNV("002", "Tran Van Nam", "0912345678"),
                new CBNV("003", "Le Thi Binh", "0987654321"),
                new CBNV("004", "Pham Hong Son", "0909090909"),
                new CBNV("005", "Dang Minh Tu", "0888888888")
        };
        rcv_cbnv = (RecyclerView) findViewById(R.id.rcv_cbnv);
        cbnv_Adapter cbnv_myAdapter = new cbnv_Adapter(cbnv);
        rcv_cbnv.setAdapter(cbnv_myAdapter);
        btnback = findViewById(R.id.btn_backcbnv);
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}