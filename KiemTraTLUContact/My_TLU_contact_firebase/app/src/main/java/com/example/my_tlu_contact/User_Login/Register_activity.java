package com.example.my_tlu_contact.User_Login;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.my_tlu_contact.R;
import com.google.firebase.auth.FirebaseAuth;

public class Register_activity extends AppCompatActivity {

    private EditText etEmail, etPassword, etConfirmPassword,et_maxacthuc;

    Button btnRegister,btnHuyRegister;

    private FirebaseAuth mAuth;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();

        etEmail = findViewById(R.id.etEmail_register);
        etPassword = findViewById(R.id.etPassword_register);
        etConfirmPassword = findViewById(R.id.etPassword_register_confirm);


        btnRegister = findViewById(R.id.btnRegister_register);
        btnHuyRegister= findViewById(R.id.btnHuyRegister);



        btnRegister.setOnClickListener(v -> {
            String email = etEmail.getText().toString();
            String password = etPassword.getText().toString();
            String confirmPassword = etConfirmPassword.getText().toString();
            if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!password.equals(confirmPassword)) {
                Toast.makeText(this, "Mật khẩu không khớp!", Toast.LENGTH_SHORT).show();
                return;
            }

            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(this, "Đăng ký thành công! Hãy xác thực email.", Toast.LENGTH_SHORT).show();
                            sendEmailVerification();
                            finish();
                        } else {
                            Toast.makeText(this, "Lỗi: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        });


        btnHuyRegister.setOnClickListener(v -> {
            finish();
        });
    }
    private void sendEmailVerification() {
        if (mAuth.getCurrentUser() != null) {
            mAuth.getCurrentUser().sendEmailVerification()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(this, "Email xác thực đã gửi!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(this, "Lỗi khi gửi email xác thực!", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }
}