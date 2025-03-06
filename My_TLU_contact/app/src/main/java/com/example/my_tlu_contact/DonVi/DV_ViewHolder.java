package com.example.my_tlu_contact.DonVi;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.my_tlu_contact.R;

public class DV_ViewHolder extends RecyclerView.ViewHolder {
    public TextView dvTEN;
    public TextView dvSDT;
    public TextView dvDIACHI;
    public DV_ViewHolder(@NonNull View itemView) {
        super(itemView);
        dvTEN = itemView.findViewById(R.id.dv_name);
        dvSDT = itemView.findViewById(R.id.dv_sdt);
        dvDIACHI = itemView.findViewById(R.id.dv_diachi);

    }
    public Void bind(DonVi dv){
        dvTEN.setText(dv.getDv_name());
        dvSDT.setText(dv.getDv_sdt());
        dvDIACHI.setText(dv.getDv_diachi());
        return null;
    }


}
