package com.example.my_tlu_contact.DonVi;

//ma_cong_ty (Mã công ty)
//ten_cong_ty (Tên công ty)
//dia_chi (Địa chỉ công ty)
//so_dien_thoai (Số điện thoại công ty)
//email (Email liên hệ)
//website (Trang web công ty)
public class DonVi {
    private  String maDV,nameDV,diachiDV,sdtDV,emaiDV,websiteDV;

    public DonVi(String maDV, String nameDV, String sdtDV, String diachiDV ,String emaiDV, String websiteDV) {
        this.maDV = maDV;
        this.nameDV = nameDV;
        this.diachiDV = diachiDV;
        this.sdtDV = sdtDV;
        this.emaiDV = emaiDV;
        this.websiteDV = websiteDV;
    }

    public String getMaDV() {
        return maDV;
    }

    public void setMaDV(String maDV) {
        this.maDV = maDV;
    }

    public String getNameDV() {
        return nameDV;
    }

    public void setNameDV(String nameDV) {
        this.nameDV = nameDV;
    }

    public String getDiachiDV() {
        return diachiDV;
    }

    public void setDiachiDV(String diachiDV) {
        this.diachiDV = diachiDV;
    }

    public String getSdtDV() {
        return sdtDV;
    }

    public void setSdtDV(String sdtDV) {
        this.sdtDV = sdtDV;
    }

    public String getEmaiDV() {
        return emaiDV;
    }

    public void setEmaiDV(String emaiDV) {
        this.emaiDV = emaiDV;
    }

    public String getWebsiteDV() {
        return websiteDV;
    }

    public void setWebsiteDV(String websiteDV) {
        this.websiteDV = websiteDV;
    }
}
