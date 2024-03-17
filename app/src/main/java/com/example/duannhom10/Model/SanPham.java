package com.example.duannhom10.Model;

public class SanPham {
    private int maSp;
    private String tenSp;
    private String moTa;
    private int soLuong;
    private int gia;

    public SanPham() {
    }

    public SanPham(int maSp, String tenSp, String moTa, int soLuong, int gia) {
        this.maSp = maSp;
        this.tenSp = tenSp;
        this.moTa = moTa;
        this.soLuong = soLuong;
        this.gia = gia;
    }

    public int getMaSp() {
        return maSp;
    }

    public void setMaSp(int maSp) {
        this.maSp = maSp;
    }

    public String getTenSp() {
        return tenSp;
    }

    public void setTenSp(String tenSp) {
        this.tenSp = tenSp;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public int getGia() {
        return gia;
    }

    public void setGia(int gia) {
        this.gia = gia;
    }
}
