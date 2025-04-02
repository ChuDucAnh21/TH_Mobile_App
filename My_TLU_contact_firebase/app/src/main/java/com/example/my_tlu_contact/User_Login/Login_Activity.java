package com.example.my_tlu_contact.User_Login;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.my_tlu_contact.MainActivity;
import com.example.my_tlu_contact.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login_Activity extends AppCompatActivity {
    EditText etEmail, etPassword, etOTP;
    TextView sendcodeOTP;

    Button btnLogin, btnRegister;
    private FirebaseAuth auth;
    private Handler uiHandler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        auth = FirebaseAuth.getInstance();
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister_register);

        btnLogin.setOnClickListener(v -> {
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                return;
            }

            auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            FirebaseUser user = auth.getCurrentUser();
                            if (user != null) {
                                String userEmail = user.getEmail();

                                // Check if it's a TLU institutional account (@tlu.edu.vn)
                                boolean isTluAccount = userEmail != null && userEmail.endsWith("@tlu.edu.vn");

                                // TLU accounts bypass email verification check
                                if (isTluAccount || user.isEmailVerified()) {
                                    // Allow login for TLU accounts regardless of verification status
                                    // or for verified regular user accounts
                                    Toast.makeText(this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();

                                    // Create intent with the appropriate user role
                                    Intent intent = new Intent(this, MainActivity.class);

                                    // Set user role based on email domain
                                    if (isTluAccount) {
                                        intent.putExtra("USER_ROLE_login", "ADMIN");
                                    } else {
                                        intent.putExtra("USER_ROLE_login", "USER");
                                    }

                                    startActivity(intent);
                                    finish(); // Close the login activity
                                } else {
                                    // For non-TLU accounts that aren't verified, send verification email
                                    user.sendEmailVerification()
                                            .addOnCompleteListener(verificationTask -> {
                                                if (verificationTask.isSuccessful()) {
                                                    Toast.makeText(this, "Vui lòng kiểm tra email và xác thực tài khoản!", Toast.LENGTH_LONG).show();
                                                } else {
                                                    Toast.makeText(this, "Lỗi khi gửi email xác thực!", Toast.LENGTH_SHORT).show();
                                                }
                                            });

                                    auth.signOut(); // Đăng xuất nếu chưa xác thực
                                }
                            }
                        } else {
                            Toast.makeText(this, "Lỗi: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        });

        btnRegister.setOnClickListener(v -> startActivity(new Intent(this, Register_activity.class)));
    }
}
