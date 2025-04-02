package com.example.my_tlu_contact.User_Login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.my_tlu_contact.Database.dbSQLite;
import com.example.my_tlu_contact.MainActivity;
import com.example.my_tlu_contact.R;

public class Login_Activity extends AppCompatActivity {
    EditText etEmail, etPassword;
    Button btnLogin, btnRegister;
    dbSQLite dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister_register);
        dbHelper = new dbSQLite(this);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmail.getText().toString().trim();
                String password = etPassword.getText().toString().trim();

                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(Login_Activity.this, "Vui lòng nhập email và mật khẩu!", Toast.LENGTH_SHORT).show();
                    return;
                }

                User user = dbHelper.loginUser(email, password);
                User user_info = dbHelper.getUserByEmail(email);
                if (user != null) {
                    Toast.makeText(Login_Activity.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                    // Chuyển màn hình theo quyền từ SQLite
                    if (user.getRole().equals("admin")) {
                        Intent intent = new Intent(Login_Activity.this, MainActivity.class);
                        intent.putExtra("ROLE_", "ADMIN"); // Truyền id rỗng để thêm mới
                        intent.putExtra("USER_NAME", user_info.getName_user());
                        intent.putExtra("USER_ID", user_info.getId());
                        intent.putExtra("USER_EMAIL", user_info.getUsername());
                        intent.putExtra("USER_PHONE", user_info.getSdt_user());
                        intent.putExtra("USER_ADDRESS", user_info.getDiachi_user());
                        intent.putExtra("USER_BIRTHDAY", user_info.getNgaysinh_user());

                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(Login_Activity.this, MainActivity.class);
                        intent.putExtra("ROLE_", "ADMIN"); // Truyền id rỗng để thêm mới
                        intent.putExtra("USER_NAME", user_info.getName_user());
                        intent.putExtra("USER_ID", user_info.getId());
                        intent.putExtra("USER_EMAIL", user_info.getUsername());
                        intent.putExtra("USER_PHONE", user_info.getSdt_user());
                        intent.putExtra("USER_ADDRESS", user_info.getDiachi_user());
                        intent.putExtra("USER_BIRTHDAY", user_info.getNgaysinh_user());
                        startActivity(intent);
                    }
                    finish();
                } else {
                    Toast.makeText(Login_Activity.this, "Sai email hoặc mật khẩu!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnRegister.setOnClickListener(v -> startActivity(new Intent(this, Register_activity.class)));
    }

    public static class DataHolder {
        private static DataHolder instance;
        private String data;

        private DataHolder() {}

        public static synchronized DataHolder getInstance() {
            if (instance == null) {
                instance = new DataHolder();
            }
            return instance;
        }

        public void setData(String data) {
            this.data = data;
        }

        public String getData() {
            return data;
        }
    }
}