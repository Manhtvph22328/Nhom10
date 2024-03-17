package com.example.duannhom10.Model;

import java.util.Date;

public class NhanVien {
    private int maNv;
    private String hoTen;
    private Date namSinh;
    private String email;
    private String matKhau;

    public NhanVien() {
    }

    public NhanVien(int maNv, String hoTen, Date namSinh, String email, String matKhau) {
        this.maNv = maNv;
        this.hoTen = hoTen;
        this.namSinh = namSinh;
        this.email = email;
        this.matKhau = matKhau;
    }

    public int getMaNv() {
        return maNv;
    }

    public void setMaNv(int maNv) {
        this.maNv = maNv;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public Date getNamSinh() {
        return namSinh;
    }

    public void setNamSinh(Date namSinh) {
        this.namSinh = namSinh;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }
}
