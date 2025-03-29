package com.example.my_tlu_contact.User_Login;

public class User {
    private int id;

    private String name_user,sdt_user,ngaysinh_user,diachi_user;
    private String username;
    private String password;
    private String role; // admin, employee, guest

    public User(int id, String name_user,String sdt_user,String ngaysinh_user,String diachi_user,String username, String password, String role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
        this.name_user = name_user;
        this.sdt_user = sdt_user;
        this.ngaysinh_user = ngaysinh_user;
        this.diachi_user = diachi_user;
    }

    public String getName_user() {
        return name_user;
    }

    public void setName_user(String name_user) {
        this.name_user = name_user;
    }

    public String getSdt_user() {
        return sdt_user;
    }

    public void setSdt_user(String sdt_user) {
        this.sdt_user = sdt_user;
    }

    public String getNgaysinh_user() {
        return ngaysinh_user;
    }

    public void setNgaysinh_user(String ngaysinh_user) {
        this.ngaysinh_user = ngaysinh_user;
    }

    public String getDiachi_user() {
        return diachi_user;
    }

    public void setDiachi_user(String diachi_user) {
        this.diachi_user = diachi_user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
