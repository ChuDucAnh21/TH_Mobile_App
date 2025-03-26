package com.example.my_tlu_contact.Activate;

import static java.lang.Math.random;

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

import com.example.my_tlu_contact.CBNV.CBNV_Activity;
import com.example.my_tlu_contact.Database.dbSQLite;
import com.example.my_tlu_contact.R;

import java.util.Random;

public class activity_add_CB extends AppCompatActivity {
    private EditText edt_idCB,edt_nameCB,edt_diachiCB,edt_sdtCB,edt_date;

    private Button btn_item_addCB,btn_item_cancelCB;
    private ImageButton btnBackCB;
    private TextView txtTitle,txtIDcb;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_cb);
        Intent intent = getIntent();
        String UI = intent.getStringExtra("KEY_UI");


        edt_idCB = findViewById(R.id.edt_idCB);
        edt_nameCB = findViewById(R.id.edt_nameCB);
        edt_diachiCB = findViewById(R.id.edt_diachiCB);
        edt_sdtCB = findViewById(R.id.edt_phoneCB);
        edt_date = findViewById(R.id.edt_dateCB);
        btn_item_addCB = findViewById(R.id.btn_itemCB_add);
        btn_item_cancelCB = findViewById(R.id.btn_itemCB_cancle);
        txtTitle = findViewById(R.id.txt_title_addCB);
        btnBackCB = findViewById(R.id.btn_backAddCB);
        txtIDcb = findViewById(R.id.txt_IDcb);

        //Theem moi CBNV
        if (UI != null && UI.equals("ADD")) {
            btn_item_addCB.setText("Thêm Mới");
            txtTitle.setText("Thêm Cán Bộ");
            txtIDcb.setVisibility(View.GONE);
            edt_idCB.setVisibility(View.GONE);

            btn_item_addCB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String id = edt_idCB.getText().toString();
                    String name = edt_nameCB.getText().toString();
                    String diachi = edt_diachiCB.getText().toString();
                    String sdt = edt_sdtCB.getText().toString();
                    String date = edt_date.getText().toString();
                    dbSQLite db = new dbSQLite(activity_add_CB.this);
                    db.open();


                    Random random = new Random();
                    int num = 10000000 + random.nextInt(90000000); // Sinh số từ 10000000 đến 99999999
                    String idRamDom =  String.valueOf(num); // Chuyển thành chuỗi
                    // Kiểm tra xem mã cán bộ đã tồn tại hay chưa
                    Boolean check = db.checkMaCB(idRamDom);


                    if (name.isEmpty() || diachi.isEmpty() || sdt.isEmpty() || date.isEmpty()) {
                        Toast.makeText(activity_add_CB.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    }
                    else if(check==true){
                        Toast.makeText(activity_add_CB.this, "Mã CBNV đã tồn tại", Toast.LENGTH_SHORT).show();
                    }
                    else {

                        db.addCBNV(idRamDom, name, date, sdt, diachi);
                        finish();
                        Toast.makeText(activity_add_CB.this, "Thêm Mới Thành Công", Toast.LENGTH_SHORT).show();

                    }
                }
            });
        }

        //Sua CBNV
        else if (UI != null && UI.equals("EDIT")) {
            btn_item_addCB.setText("Cập Nhật");
            txtTitle.setText("Sửa Cán Bộ");

            String id = intent.getStringExtra("ID_IT");
            String name = intent.getStringExtra("NAME_IT");
            String diachi = intent.getStringExtra("ADDRESS_IT");
            String sdt = intent.getStringExtra("PHONE_IT");
            String date = intent.getStringExtra("DATE_IT");
            edt_idCB.setText(id);
            edt_nameCB.setText(name);
            edt_diachiCB.setText(diachi);
            edt_sdtCB.setText(sdt);
            edt_date.setText(date);

            btn_item_addCB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String idCB_new = edt_idCB.getText().toString();
                    String nameCB_new = edt_nameCB.getText().toString();
                    String diachiCB_new = edt_diachiCB.getText().toString();
                    String sdtCB_new = edt_sdtCB.getText().toString();
                    String dateCB_new = edt_date.getText().toString();
                    dbSQLite db = new dbSQLite(activity_add_CB.this);
                    db.open();
                    db.updateCBNV( idCB_new, nameCB_new, dateCB_new, sdtCB_new, diachiCB_new);
                    Intent intent = new Intent(activity_add_CB.this, CBNV_Activity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                    finish();
                }
            });
        }


        //Xoa CBNV
        btn_item_cancelCB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnBackCB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}