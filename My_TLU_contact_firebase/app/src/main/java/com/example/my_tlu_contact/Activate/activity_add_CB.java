package com.example.my_tlu_contact.Activate;

import static java.lang.Math.random;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.my_tlu_contact.CBNV.CBNV;
import com.example.my_tlu_contact.CBNV.CBNV_Activity;
import com.example.my_tlu_contact.DonVi.DonVi;
import com.example.my_tlu_contact.DonVi.DonVi_Activity;
import com.example.my_tlu_contact.R;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class activity_add_CB extends AppCompatActivity {
    private EditText edt_idCB,edt_nameCB,edt_diachiCB,edt_sdtCB,edt_date;

    private Button btn_item_addCB,btn_item_cancelCB;
    private ImageButton btnBackCB;
    private TextView txtTitle,txtIDcb;
    private DatabaseReference dbReference;
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
                    Random randomidcb = new Random();
                    int randomNumber_cb = 1000000 + randomidcb.nextInt(9000000);
                    String idCanBo ="CB" + String.valueOf(randomNumber_cb).trim();
                    String namecb = edt_nameCB.getText().toString();
                    String diachicb = edt_diachiCB.getText().toString();
                    String sdtcb = edt_sdtCB.getText().toString();
                    String datecb = edt_date.getText().toString();

                    if (namecb.isEmpty() || diachicb.isEmpty() || sdtcb.isEmpty() || datecb.isEmpty()) {
                        Toast.makeText(activity_add_CB.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    }
                   else {
                        FirebaseDatabase database = FirebaseDatabase.getInstance("https://tlucontact-b051d-default-rtdb.asia-southeast1.firebasedatabase.app");
                        DatabaseReference CBNVRef = database.getReference("canbo");

                        // Tạo một đối tượng DonVi mới
                        CBNV cbnv = new CBNV(idCanBo, namecb, datecb, sdtcb, diachicb);

                        // Đẩy dữ liệu lên Firebase theo mã đơn vị (maDV)
                        CBNVRef.child(cbnv.getMaCB()).setValue(cbnv)
                                .addOnSuccessListener(aVoid -> Log.d("Firebase", "Thêm đơn vị thành công!"))
                                .addOnFailureListener(e -> Log.e("Firebase", "Lỗi khi thêm đơn vị: " + e.getMessage()));
                        Toast.makeText(activity_add_CB.this, "Thêm Đơn Vị Thành Công", Toast.LENGTH_SHORT).show();
                        finish();
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

            edt_idCB.setKeyListener(null); // Chỉ đọc, không cho phép chỉnh sửa

            btn_item_addCB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String idCB_new = edt_idCB.getText().toString();
                    String nameCB_new = edt_nameCB.getText().toString();
                    String diachiCB_new = edt_diachiCB.getText().toString();
                    String sdtCB_new = edt_sdtCB.getText().toString();
                    String dateCB_new = edt_date.getText().toString();
                    if (nameCB_new.isEmpty() || diachiCB_new.isEmpty() || sdtCB_new.isEmpty() || dateCB_new.isEmpty()) {
                        Toast.makeText(activity_add_CB.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    }
                    else{

                    FirebaseDatabase database = FirebaseDatabase.getInstance("https://tlucontact-b051d-default-rtdb.asia-southeast1.firebasedatabase.app");
                    DatabaseReference donViRef = database.getReference("canbo").child(idCB_new);

                    // Tạo map chứa các giá trị cần cập nhật
                    Map<String, Object> updates = new HashMap<>();
                    updates.put("tenCB", nameCB_new);
                    updates.put("sdtCB", sdtCB_new);
                    updates.put("diachiCB", diachiCB_new);
                    updates.put("ngaysinhCB", dateCB_new);

                    // Cập nhật lên Firebase
                    donViRef.updateChildren(updates)
                            .addOnSuccessListener(aVoid -> Log.d("Firebase", "Cập nhật thành công!"))
                            .addOnFailureListener(e -> Log.e("Firebase", "Lỗi khi cập nhật: " + e.getMessage()));
                    Toast.makeText(activity_add_CB.this, "Cập Nhật Đơn Vị Thành Công", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(activity_add_CB.this, CBNV_Activity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish(); // Đóng activity hiện tại
                    }
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