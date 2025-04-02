package com.example.my_tlu_contact.CBNV;

public class CBNV {
    private String maCB,tenCB,ngaysinhCB,sdtCB,diachiCB;

    public CBNV() {
    }
    public CBNV(String maCB, String tenCB, String ngaysinhCB,String sdtCB, String diachiCB) {
        this.maCB = maCB;
        this.tenCB = tenCB;
        this.ngaysinhCB = ngaysinhCB;
        this.diachiCB = diachiCB;
        this.sdtCB = sdtCB;
    }

    public String getMaCB() {
        return maCB;
    }

    public void setMaCB(String maCB) {
        this.maCB = maCB;
    }

    public String getTenCB() {
        return tenCB;
    }

    public void setTenCB(String tenCB) {
        this.tenCB = tenCB;
    }

    public String getNgaysinhCB() {
        return ngaysinhCB;
    }

    public void setNgaysinhCB(String ngaysinhCB) {
        this.ngaysinhCB = ngaysinhCB;
    }

    public String getSdtCB() {
        return sdtCB;
    }

    public void setSdtCB(String sdtCB) {
        this.sdtCB = sdtCB;
    }

    public String getDiachiCB() {
        return diachiCB;
    }

    public void setDiachiCB(String diachiCB) {
        this.diachiCB = diachiCB;
    }
}
