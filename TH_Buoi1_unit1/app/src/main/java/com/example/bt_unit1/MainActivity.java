package com.example.bt_unit1;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private Button btn_count,btn_toast;
    private TextView tx_count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_count = findViewById(R.id.btn_count);
        btn_toast = findViewById(R.id.btn_toast);
        tx_count = findViewById(R.id.show_count);

        btn_count.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               countUp();
            }
        });
        btn_toast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast();
            }
        });

    }
        public void countUp(){
            String x  = tx_count.getText().toString();

            int value = Integer.parseInt(x) + 1;

            tx_count.setText(String.valueOf(value));
        }
        public void showToast(){
            Toast.makeText(MainActivity.this,R.string.toast_message, Toast.LENGTH_SHORT).show();

        }



}