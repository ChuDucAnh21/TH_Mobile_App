package com.example.tlusinhvien;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rcvStudents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        student[] students = {
                new student("001", "Nguyen Van A", R.drawable.sach),
                new student("002", "Nguyen Van B", R.drawable.sach1),
                new student("003", "Nguyen Van C", R.drawable._04_main),
                new student("004", "Nguyen Van D", R.drawable._04_starter),
        };
        rcvStudents = (RecyclerView) findViewById(R.id.rcv_students);
        StudentAdapter myAdapter = new StudentAdapter(students);
        rcvStudents.setAdapter(myAdapter);
    }
}