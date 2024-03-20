package com.example.duannhom10.Model;

public class NhanVien {
    private String maNv;
    private String hoTen;
    private String namSinh;
    private String email;
    private String matKhau;

    public NhanVien() {
    }

    public NhanVien(String maNv, String hoTen, String namSinh, String email, String matKhau) {
        this.maNv = maNv;
        this.hoTen = hoTen;
        this.namSinh = namSinh;
        this.email = email;
        this.matKhau = matKhau;
    }

    public String getMaNv() {
        return maNv;
    }

    public void setMaNv(String maNv) {
        this.maNv = maNv;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getNamSinh() {
        return namSinh;
    }

    public void setNamSinh(String namSinh) {
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
