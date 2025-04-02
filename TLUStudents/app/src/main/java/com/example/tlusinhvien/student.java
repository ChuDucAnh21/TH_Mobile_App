package com.example.tlusinhvien;

public class student {
    private String sdi;
    private String fullname;
    private int avatar;

    public student(String sdi, String fullname, int avatar) {
        this.sdi = sdi;
        this.fullname = fullname;
        this.avatar = avatar;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getSdi() {
        return sdi;
    }

    public void setSdi(String sdi) {
        this.sdi = sdi;
    }

    public int getAvatar() {
        return avatar;
    }

    public void setAvatar(int avatar) {
        this.avatar = avatar;
    }
}
