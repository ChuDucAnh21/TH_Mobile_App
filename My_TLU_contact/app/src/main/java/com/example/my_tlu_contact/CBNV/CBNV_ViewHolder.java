package com.example.my_tlu_contact.CBNV;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.my_tlu_contact.DonVi.DonVi;
import com.example.my_tlu_contact.R;

public class CBNV_ViewHolder extends RecyclerView.ViewHolder{
    public TextView cbnvMa;
    public TextView cbnvTen;
    public TextView cbnvSdt;
    public CBNV_ViewHolder(@NonNull View itemView) {
        super(itemView);
        cbnvMa = itemView.findViewById(R.id.cbnv_ma);
        cbnvTen = itemView.findViewById(R.id.cbnv_name);
        cbnvSdt = itemView.findViewById(R.id.cbnv_sdt);

    }
    public Void bind(CBNV cb){
        cbnvMa.setText(cb.getMaCB());
        cbnvTen.setText(cb.getTenCB());
        cbnvSdt.setText(cb.getSdtCB());
        return null;
    }
}
