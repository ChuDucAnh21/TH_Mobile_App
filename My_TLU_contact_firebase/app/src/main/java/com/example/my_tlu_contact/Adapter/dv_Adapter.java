package com.example.my_tlu_contact.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.my_tlu_contact.Detail_dv_Activity;
import com.example.my_tlu_contact.DonVi.DV_ViewHolder;
import com.example.my_tlu_contact.Detail_cbnv_Activity;
import com.example.my_tlu_contact.DonVi.DonVi;
import com.example.my_tlu_contact.R;

import java.util.ArrayList;
import java.util.List;

public class dv_Adapter extends RecyclerView.Adapter<DV_ViewHolder> {
    private DonVi[] donvi;

    public dv_Adapter(DonVi[] donvi) {
        this.donvi = donvi;
    }

    @NonNull
    @Override
    public DV_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_donvi, parent,false);
        return new DV_ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull DV_ViewHolder holder, int position) {
        DonVi dv = donvi[position];
        holder.bind(dv);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent detailIntent = new Intent(v.getContext(), Detail_dv_Activity.class);
                detailIntent.putExtra("ID",dv.getMaDV());
                detailIntent.putExtra("NAME",dv.getNameDV());
                detailIntent.putExtra("ADDRESS",dv.getDiachiDV());
                detailIntent.putExtra("PHONE",dv.getSdtDV());
                detailIntent.putExtra("WEB",dv.getWebsiteDV());
                detailIntent.putExtra("EMAIL",dv.getEmailDV());
                v.getContext().startActivity(detailIntent);
            }
        });

    }


    @Override
    public int getItemCount() {
        return donvi.length;
    }
    public void updateData(DonVi[] newArray) {
        this.donvi = newArray;  // Cập nhật mảng mới
        notifyDataSetChanged(); // Cập nhật RecyclerView
    }
    public void updateData_searchDV(List<DonVi> newList) {
        this.donvi = newList.toArray(new DonVi[0]); // Chuyển ArrayList về mảng
        notifyDataSetChanged();
    }
}