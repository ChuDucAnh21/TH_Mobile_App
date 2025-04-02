package com.example.tlusinhvien;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class StudentAdapter extends RecyclerView.Adapter<StudentViewHolder> {
    private student[] students;

    public StudentAdapter(student[] students) {
        this.students = students;
    }


    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_student, parent,false);
        return new StudentViewHolder(v);
    }

    @Override   // set dữ liệu cho các thông tin : id, tên, img,....
    public void onBindViewHolder(@NonNull StudentViewHolder holder, int position) {
        holder.bind(students[position]);
    }

    @Override   // Lấy độ dài mảng studentddeereer hiện thị lên
    public int getItemCount() {
        return students.length;
    }
}
