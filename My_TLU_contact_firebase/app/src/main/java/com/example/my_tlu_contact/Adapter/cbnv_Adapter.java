package com.example.my_tlu_contact.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.my_tlu_contact.CBNV.CBNV;
import com.example.my_tlu_contact.CBNV.CBNV_ViewHolder;
import com.example.my_tlu_contact.Detail_cbnv_Activity;
import com.example.my_tlu_contact.DonVi.DonVi;
import com.example.my_tlu_contact.R;

import java.util.List;

public class cbnv_Adapter extends RecyclerView.Adapter<CBNV_ViewHolder>{
    CBNV[] cbnv;

    public cbnv_Adapter(CBNV[] cbnv) {
        this.cbnv = cbnv;
    }

    @NonNull
    @Override
    public CBNV_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cbnv, parent,false);
        return new CBNV_ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CBNV_ViewHolder holder, int position) {
        CBNV cb = cbnv[position];
        holder.bind(cb);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent detailIntent = new Intent(v.getContext(), Detail_cbnv_Activity.class);
                detailIntent.putExtra("ID",cb.getMaCB());
                detailIntent.putExtra("NAME",cb.getTenCB());
                detailIntent.putExtra("DATE",cb.getNgaysinhCB());
                detailIntent.putExtra("PHONE",cb.getSdtCB());
                detailIntent.putExtra("ADDRESS",cb.getDiachiCB());
                v.getContext().startActivity(detailIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return cbnv.length;
    }
    public void updateData(CBNV[] newArray) {
        this.cbnv = newArray;  // Cập nhật mảng mới
        notifyDataSetChanged(); // Cập nhật RecyclerView
    }
    public void updateData_searchCB(List<CBNV> newList) {
        this.cbnv = newList.toArray(new CBNV[0]); // Chuyển ArrayList về mảng
        notifyDataSetChanged();
    }
}
