package com.example.my_tlu_contact.CBNV;

public class CBNV {
    private String maCB,tenCB,sdtCB;

    public CBNV(String maCB, String tenCB, String sdtCB) {
        this.maCB = maCB;
        this.tenCB = tenCB;
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

    public String getSdtCB() {
        return sdtCB;
    }

    public void setSdtCB(String sdtCB) {
        this.sdtCB = sdtCB;
    }
}
