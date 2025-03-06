package com.example.app_04;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    EditText edtA, edtB, edtKQ;
    Button btnCong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtA = findViewById(R.id.edtA);
        edtB = findViewById(R.id.edtB);
        edtKQ = findViewById(R.id.edtKQ);
        btnCong = findViewById(R.id.btntong); // Corrected ID

        btnCong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strA = edtA.getText().toString();
                String strB = edtB.getText().toString();

                // Input Validation (Important!)
                if (strA.isEmpty() || strB.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Vui lòng nhập đầy đủ giá trị", Toast.LENGTH_SHORT).show();
                    return; // Stop execution if input is missing
                }

                try {
                    int a = Integer.parseInt(strA);
                    int b = Integer.parseInt(strB);
                    int c = a + b;
                    edtKQ.setText(String.valueOf(c)); // Use String.valueOf()
                } catch (NumberFormatException e) {
                    Toast.makeText(MainActivity.this, "Giá trị nhập vào không hợp lệ", Toast.LENGTH_SHORT).show();
                }

                Intent myIntent = new Intent(Intent.ACTION_CALL);
                myIntent.setData(Uri.parse("tel:0869380901"));
//                Intent myIntent = new Intent(MainActivity.this,ActivitySecond.class);
                startActivity(myIntent);
            }


        });
    }
}