package com.example.my_tlu_contact.Activate;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.my_tlu_contact.Database.dbSQLite;
import com.example.my_tlu_contact.DonVi.DonVi_Activity;
import com.example.my_tlu_contact.R;

import java.util.Random;

public class activity_add_DV extends AppCompatActivity {
    private EditText edt_idDV,edt_nameDV,edt_diachiDV,edt_sdtDV,edt_emailDV,edt_websiteDV;
    private TextView txtTitleDV,txtIDdv;
    private Button btn_Item_addDV,btn_Item_cancelDV;
    private ImageButton btnBackDV;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_dv);

            edt_idDV = findViewById(R.id.edt_idDV);
            edt_nameDV = findViewById(R.id.edt_nameDV);
            edt_diachiDV = findViewById(R.id.edt_diachiDV);
            edt_sdtDV = findViewById(R.id.edt_phoneDV);
            edt_emailDV = findViewById(R.id.edt_emailDV);
            edt_websiteDV = findViewById(R.id.edt_webDV);
            btn_Item_addDV = findViewById(R.id.btn_itemDV_add);
            btn_Item_cancelDV = findViewById(R.id.btn_itemDV_cancle);
            txtTitleDV = findViewById(R.id.txt_title_addDV);
            btnBackDV = findViewById(R.id.btn_backAddDV);
            txtIDdv = findViewById(R.id.txtIDdv);


        Intent intent = getIntent();
        String UI_dv = intent.getStringExtra("KEY_");
        if (UI_dv != null && UI_dv.equals("ADD_")) {
            edt_idDV.setVisibility(View.GONE);
            txtIDdv.setVisibility(View.GONE);
            btn_Item_addDV.setText("Thêm Mới");
            txtTitleDV.setText("Thêm Đơn Vị");
            btn_Item_addDV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String id = edt_idDV.getText().toString().toString().trim();
                    String name = edt_nameDV.getText().toString();
                    String address = edt_diachiDV.getText().toString();
                    String phone = edt_sdtDV.getText().toString();
                    String email = edt_emailDV.getText().toString();
                    String website = edt_websiteDV.getText().toString();
                    dbSQLite db = new dbSQLite(activity_add_DV.this);
                    db.open();

                    Random random = new Random();
                    int num = 10000000 + random.nextInt(90000000); // Sinh số từ 10000000 đến 99999999
                    String idRamDom =  String.valueOf(num); // Chuyển thành chuỗi
                    // Kiểm tra xem mã cán bộ đã tồn tại hay chưa
                    Boolean check = db.checkMaDV(idRamDom);
                    if(website.isEmpty()){
                        website = "Chưa có thông tin";
                    }
                    if( name.isEmpty() || address.isEmpty() || phone.isEmpty() || email.isEmpty()) {
                        Toast.makeText(activity_add_DV.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    }
                    else if (check == true) {
                        Toast.makeText(activity_add_DV.this, "Mã đơn vị đã tồn tại", Toast.LENGTH_SHORT).show();
                    }
                    else {

                        db.addDV(idRamDom, name, address, phone, email, website);

                        finish();
                    }
                }
            });
        }
       if (UI_dv != null && UI_dv.equals("EDIT")) {
            btn_Item_addDV.setText("Cập Nhật");
           txtTitleDV.setText("Sửa Cán Bộ");

            String id = intent.getStringExtra("ID_");
            String name = intent.getStringExtra("NAME_");
            String address = intent.getStringExtra("ADDRESS_");
            String phone = intent.getStringExtra("PHONE_");
            String email = intent.getStringExtra("EMAIL_");
            String website = intent.getStringExtra("WEB_");

            edt_idDV.setText(id);
            edt_nameDV.setText(name);
            edt_diachiDV.setText(address);
            edt_sdtDV.setText(phone);
            edt_emailDV.setText(email);
            edt_websiteDV.setText(website);

            btn_Item_addDV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   String idDV_new = edt_idDV.getText().toString();
                    String nameDV_new = edt_nameDV.getText().toString();
                    String addressDV_new = edt_diachiDV.getText().toString();
                    String phoneDV_new = edt_sdtDV.getText().toString();
                    String emailDV_new = edt_emailDV.getText().toString();
                    String websiteDV_new = edt_websiteDV.getText().toString();

                    dbSQLite db = new dbSQLite(activity_add_DV.this);
                    db.open();
                    db.updateDV(idDV_new, nameDV_new, addressDV_new, phoneDV_new, emailDV_new, websiteDV_new);
                    Intent intent = new Intent(activity_add_DV.this, DonVi_Activity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                    finish();
                }
            });
       }



            btn_Item_cancelDV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
       btnBackDV.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               finish();
           }
       });

    }
}