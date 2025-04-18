package com.example.bt_second_class;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class StarterActivity extends AppCompatActivity {
    private ListView lstStarterFoods;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_starter);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        lstStarterFoods = (ListView) findViewById(R.id.lstFood);
        String[] disher={"food1","food2","food3","food4","food5"};
        ArrayAdapter<String> myApdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,disher);
        lstStarterFoods.setAdapter(myApdapter);
    }
}