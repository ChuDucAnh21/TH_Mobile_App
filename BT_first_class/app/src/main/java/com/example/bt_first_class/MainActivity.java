package com.example.bt_first_class;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
     private RadioButton rdoBnt_nam,rdoBtn_nu;
     private EditText tx_age,tx_feet,tx_inches,tx_heigh;
     private Button btn_resuilt;
     private  TextView tx_BMI,tx_giatri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rdoBnt_nam = findViewById(R.id.rdButton_nam);
        rdoBtn_nu = findViewById(R.id.rdButton_nu);
        tx_age = findViewById(R.id.tx_age);
        tx_feet = findViewById(R.id.tx_feet);
        tx_inches = findViewById(R.id.tx_inches);
        tx_heigh = findViewById(R.id.tx_heigh);
        tx_BMI = findViewById(R.id.tx_BMI);
        tx_giatri = findViewById(R.id.tx_result);
        btn_resuilt = findViewById(R.id.btn_result);
        btn_resuilt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"dfg", Toast.LENGTH_SHORT).show();
            }
        });

    }
}