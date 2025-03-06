package com.example.my_tlu_contact.DonVi;

public class DonVi {
    private  String dv_name;
    private  String dv_sdt;
    private String dv_diachi;

    public DonVi(String dv_name, String dv_sdt, String dv_diachi) {
        this.dv_name = dv_name;
        this.dv_sdt = dv_sdt;
        this.dv_diachi = dv_diachi;
    }

    public String getDv_name() {
        return dv_name;
    }

    public void setDv_name(String dv_name) {
        this.dv_name = dv_name;
    }

    public String getDv_sdt() {
        return dv_sdt;
    }

    public void setDv_sdt(String dv_sdt) {
        this.dv_sdt = dv_sdt;
    }

    public String getDv_diachi() {
        return dv_diachi;
    }

    public void setDv_diachi(String dv_diachi) {
        this.dv_diachi = dv_diachi;
    }
}
