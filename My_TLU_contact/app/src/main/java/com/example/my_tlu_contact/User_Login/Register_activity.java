package com.example.my_tlu_contact.User_Login;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.my_tlu_contact.Database.dbSQLite;
import com.example.my_tlu_contact.R;

public class Register_activity extends AppCompatActivity {

    private EditText etEmail, etPassword, etConfirmPassword,etten,etsdt,etngaysinh,etdiachi;

    Button btnRegister,btnHuyRegister;
    dbSQLite db;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etEmail = findViewById(R.id.etEmail_register);
        etPassword = findViewById(R.id.etPassword_register);
        etConfirmPassword = findViewById(R.id.etPassword_register_confirm);
        etten = findViewById(R.id.etTen);
        etngaysinh = findViewById(R.id.etNgaySInh);
        etsdt = findViewById(R.id.etSDT);
        etdiachi = findViewById(R.id.etDiaChi);


        btnRegister = findViewById(R.id.btnRegister_register);
        btnHuyRegister= findViewById(R.id.btnHuyRegister);
        db = new dbSQLite(this);



        btnRegister.setOnClickListener(v -> {
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();
            String confirmPassword = etConfirmPassword.getText().toString().trim();
            String ten = etten.getText().toString().trim();
            String ngaysinh = etngaysinh.getText().toString().trim();
            String sdt = etsdt.getText().toString().trim();
            String diachi = etdiachi.getText().toString().trim();


            if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!password.equals(confirmPassword)) {
                Toast.makeText(this, "Mật khẩu nhập lại không khớp!", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(this, "Email không hợp lệ!", Toast.LENGTH_SHORT).show();
                return;
            }

            boolean success = db.registerUser(ten,sdt,ngaysinh,diachi,email, password, "user");
            if (success) {
                Toast.makeText(this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show();
                finish(); // Quay về màn hình login
            } else {
                Toast.makeText(this, "Lỗi! Email đã tồn tại.", Toast.LENGTH_SHORT).show();
            }
        });
        btnHuyRegister.setOnClickListener(v -> {
            finish();
        });
    }
}