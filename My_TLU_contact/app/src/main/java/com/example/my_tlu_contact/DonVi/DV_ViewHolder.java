package com.example.my_tlu_contact.DonVi;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.my_tlu_contact.R;

public class DV_ViewHolder extends RecyclerView.ViewHolder {
    public TextView idDV;
    public TextView nameDV;
    public TextView diachiDV;
    public DV_ViewHolder(@NonNull View itemView) {
        super(itemView);
        idDV = itemView.findViewById(R.id.dv_id);
        nameDV = itemView.findViewById(R.id.dv_name);
        diachiDV = itemView.findViewById(R.id.dv_diachi);

    }
    public Void bind(DonVi dv){
        idDV.setText(dv.getMaDV());
        nameDV.setText(dv.getNameDV());
        diachiDV.setText(dv.getDiachiDV());
        return null;
    }


}
