package com.example.my_tlu_contact.DonVi;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.my_tlu_contact.Adapter.dv_Adapter;
import com.example.my_tlu_contact.R;

public class DonVi_Activity extends AppCompatActivity {
   private RecyclerView rcv_donvi;
   private Button btnback;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_don_vi);
        
        DonVi[] donvi={
                new DonVi("Cty A", "0869380901", "Thanh Xuan, Ha Noi"),
             new DonVi("Cty B", "0912345678", "Cau Giay, Ha Noi"),
             new DonVi("Cty C", "0987654321", "Hai Ba Trung, Ha Noi"),
             new DonVi("Cty D", "0909090909", "Dong Da, Ha Noi"),
             new DonVi("Cty E", "0888888888", "Hoan Kiem, Ha Noi"),
                new DonVi("Cty A", "0869380901", "Thanh Xuan, Ha Noi"),
                new DonVi("Cty B", "0912345678", "Cau Giay, Ha Noi"),
                new DonVi("Cty C", "0987654321", "Hai Ba Trung, Ha Noi"),
                new DonVi("Cty D", "0909090909", "Dong Da, Ha Noi"),
                new DonVi("Cty E", "0888888888", "Hoan Kiem, Ha Noi"),
        };
        rcv_donvi = (RecyclerView) findViewById(R.id.rcv_cbnv);
        dv_Adapter dv_myAdapter = new dv_Adapter(donvi);
        rcv_donvi.setAdapter(dv_myAdapter);
        btnback = findViewById(R.id.btn_back_dv);
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}