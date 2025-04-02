package com.example.tlusinhvien;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class StudentViewHolder extends RecyclerView.ViewHolder {
    private ImageView imgavt;
    private TextView txtName;
    private TextView txtSid;
    public StudentViewHolder(@NonNull View itemView){
        super(itemView);
        imgavt = itemView.findViewById(R.id.img_avata);
        txtName = itemView.findViewById(R.id.txt_student_name);
        txtSid = itemView.findViewById(R.id.txt_student_id);
    }

    public Void bind(student std){
        imgavt.setImageResource(std.getAvatar());
        txtName.setText(std.getFullname());
        txtSid.setText(std.getSdi());
        return null;
    }

}
