package com.example.duannhom10.Model;

public class SanPham {
    private int maSp;
    private String tenSp;
    private String moTa;
    private int maDm;
    private int soLuong;
    private int gia;

    public SanPham() {
    }

    public SanPham(int maSp, String tenSp, String moTa, int maDm, int soLuong, int gia) {
        this.maSp = maSp;
        this.tenSp = tenSp;
        this.moTa = moTa;
        this.maDm = maDm;
        this.soLuong = soLuong;
        this.gia = gia;
    }

    public SanPham(int anInt, String string, int anInt1, int anInt2) {
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

    public int getMaDm() {
        return maDm;
    }

    public void setMaDm(int maDm) {
        this.maDm = maDm;
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
