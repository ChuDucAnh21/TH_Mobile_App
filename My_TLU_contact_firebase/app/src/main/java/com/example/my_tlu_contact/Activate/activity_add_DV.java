package com.example.my_tlu_contact.Activate;

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

import com.example.my_tlu_contact.DonVi.DonVi;
import com.example.my_tlu_contact.DonVi.DonVi_Activity;
import com.example.my_tlu_contact.R;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
                    Random randomiddv = new Random();
                    int randomNumber_dv = 100000 + randomiddv.nextInt(900000); // Tạo số ngẫu nhiên từ 100000 đến 999999
                    String id_dv = "DV" + String.valueOf(randomNumber_dv).trim();
                    String name_dv = edt_nameDV.getText().toString();
                    String address_dv = edt_diachiDV.getText().toString();
                    String phone_dv = edt_sdtDV.getText().toString();
                    String email_dv = edt_emailDV.getText().toString();
                    String website_dv = edt_websiteDV.getText().toString();

                    FirebaseDatabase database = FirebaseDatabase.getInstance("https://tlucontact-b051d-default-rtdb.asia-southeast1.firebasedatabase.app");
                    DatabaseReference donViRef = database.getReference("donvi");

                    // Tạo một đối tượng DonVi mới
                    DonVi donVi = new DonVi(id_dv, name_dv, phone_dv, address_dv, email_dv, website_dv);

                    // Đẩy dữ liệu lên Firebase theo mã đơn vị (maDV)
                    donViRef.child(donVi.getMaDV()).setValue(donVi)
                            .addOnSuccessListener(aVoid -> Log.d("Firebase", "Thêm đơn vị thành công!"))
                            .addOnFailureListener(e -> Log.e("Firebase", "Lỗi khi thêm đơn vị: " + e.getMessage()));
                    Toast.makeText(activity_add_DV.this, "Thêm Đơn Vị Thành Công", Toast.LENGTH_SHORT).show();
                    finish();

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

           edt_idDV.setKeyListener(null); // Chỉ đọc, không cho phép chỉnh sửa

            btn_Item_addDV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String nameDV_new = edt_nameDV.getText().toString();
                    String addressDV_new = edt_diachiDV.getText().toString();
                    String phoneDV_new = edt_sdtDV.getText().toString();
                    String emailDV_new = edt_emailDV.getText().toString();
                    String websiteDV_new = edt_websiteDV.getText().toString();
                    // Kiểm tra xem các trường có rỗng hay không
                    if (nameDV_new.isEmpty() || addressDV_new.isEmpty() || phoneDV_new.isEmpty() || emailDV_new.isEmpty() || websiteDV_new.isEmpty()) {
                        Toast.makeText(activity_add_DV.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    else {
                        FirebaseDatabase database = FirebaseDatabase.getInstance("https://tlucontact-b051d-default-rtdb.asia-southeast1.firebasedatabase.app");
                        DatabaseReference donViRef = database.getReference("donvi").child(edt_idDV.getText().toString());

                        // Tạo map chứa các giá trị cần cập nhật
                        Map<String, Object> updates = new HashMap<>();
                        updates.put("nameDV", nameDV_new);
                        updates.put("sdtDV", phoneDV_new);
                        updates.put("diachiDV", addressDV_new);
                        updates.put("emailDV", emailDV_new);
                        updates.put("websiteDV", websiteDV_new);

                        // Cập nhật lên Firebase
                        donViRef.updateChildren(updates)
                                .addOnSuccessListener(aVoid -> Log.d("Firebase", "Cập nhật thành công!"))
                                .addOnFailureListener(e -> Log.e("Firebase", "Lỗi khi cập nhật: " + e.getMessage()));
                        Toast.makeText(activity_add_DV.this, "Cập Nhật Đơn Vị Thành Công", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(activity_add_DV.this, DonVi_Activity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish(); // Đóng activity hiện tại
                    }
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