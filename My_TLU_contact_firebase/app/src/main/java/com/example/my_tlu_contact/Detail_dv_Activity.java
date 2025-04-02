package com.example.my_tlu_contact;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.my_tlu_contact.Activate.activity_add_DV;
import com.example.my_tlu_contact.User_Login.Login_Activity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Detail_dv_Activity extends AppCompatActivity {

    private TextView dt_idDV,dt_nameDv,dt_diachiDV,dt_sdtDV,dt_emailDV,dt_websiteDV;
    private ImageButton btn_backDv,btn_callDv,btn_messDv;
    private Button btnSuaDv,btnXoaDv;
    private static final int REQUEST_CALL_PHONE = 1;
    private static final int REQUEST_SEND_SMS = 1;

    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_dv);

        dt_idDV =(TextView) findViewById(R.id.dt_idDV);
        dt_nameDv = (TextView)findViewById(R.id.dt_nameDV);
        dt_diachiDV =(TextView) findViewById(R.id.dt_diachiDV);
        dt_sdtDV =(TextView) findViewById(R.id.dt_sdtDV);
        dt_emailDV =(TextView) findViewById(R.id.dt_emailDV);
        dt_websiteDV =(TextView) findViewById(R.id.dt_websiteDV);
        btnSuaDv = findViewById(R.id.btn_suaItemDV);
        btnXoaDv = findViewById(R.id.btn_xoaItemDV);
        btn_callDv = findViewById(R.id.btn_callDV);
        btn_messDv = findViewById(R.id.btn_messDV);

//        Toast.makeText(this, role_.getRole() + "hello nhe", Toast.LENGTH_SHORT).show();

        String data = role_.getRole();
        if (data != null) {
            if (data.equals("ADMIN")) {
                btnSuaDv.setVisibility(View.VISIBLE);
                btnXoaDv.setVisibility(View.VISIBLE);
            } else if (data.equals("USER")) {
                btnSuaDv.setVisibility(View.GONE);
                btnXoaDv.setVisibility(View.GONE);
            }
        }
        //Nhan du lieu tu Intent
        Intent intent = getIntent();

        if(intent != null){
            dt_idDV.setText(intent.getStringExtra("ID"));
            dt_nameDv.setText(intent.getStringExtra("NAME"));
            dt_diachiDV.setText(intent.getStringExtra("ADDRESS"));
            dt_sdtDV.setText(intent.getStringExtra("PHONE"));
            dt_emailDV.setText(intent.getStringExtra("EMAIL"));
            dt_websiteDV.setText(intent.getStringExtra("WEB"));

        }
        btn_backDv = findViewById(R.id.dt_arrowDV);
        btn_backDv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_callDv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:" + dt_sdtDV.getText()));
                    startActivity(intent);

            }
        });

        btn_messDv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("sms:"+dt_sdtDV.getText())); // Thay bằng số điện thoại cần gửi
                intent.putExtra("sms_body", "Xin chào, đây là tin nhắn tự động!"); // Nội dung tin nhắn
                startActivity(intent);
            }
        }
        );
        btnSuaDv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Detail_dv_Activity.this, activity_add_DV.class);
                intent.putExtra("KEY_", "EDIT");
                intent.putExtra("ID_", dt_idDV.getText());
                intent.putExtra("NAME_", dt_nameDv.getText());
                intent.putExtra("ADDRESS_", dt_diachiDV.getText());
                intent.putExtra("PHONE_", dt_sdtDV.getText());
                intent.putExtra("EMAIL_", dt_emailDV.getText());
                intent.putExtra("WEB_", dt_websiteDV.getText());
                startActivity(intent);
            }
        });
        btnXoaDv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
// Lấy reference tới database
                FirebaseDatabase database = FirebaseDatabase.getInstance("https://tlucontact-b051d-default-rtdb.asia-southeast1.firebasedatabase.app");
                DatabaseReference usersRef = database.getReference("donvi");  // Thay "users" bằng đường dẫn của bạn

// ID của bản ghi bạn muốn xóa
                String userId = dt_idDV.getText().toString().trim();  // Thay "user1" bằng ID của bản ghi bạn muốn xóa

// Lấy reference tới bản ghi có ID là userId
                DatabaseReference userRef = usersRef.child(userId);

// Xóa bản ghi
                userRef.removeValue()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    // Thông báo xóa thành công
                                    Log.d("Firebase", "Xóa bản ghi với ID " + userId + " thành công!");
                                } else {
                                    // Thông báo lỗi nếu có
                                    Log.e("Firebase", "Lỗi khi xóa bản ghi: " + task.getException().getMessage());
                                }
                            }
                        });

                finish();

            }
        });

    }


}