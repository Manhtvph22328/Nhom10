package com.example.duannhom10.Model;

import java.util.Date;

public class HoaDon {
    private int maHd;
    private int maNv;
    private int maKh;
    private int maSp;
    private int tien;
    private String thanhToan;
    private String ngay;

    public HoaDon() {
    }

    public HoaDon(int maHd, int maNv, int maKh, int maSp, int tien, String thanhToan, String ngay) {
        this.maHd = maHd;
        this.maNv = maNv;
        this.maKh = maKh;
        this.maSp = maSp;
        this.tien = tien;
        this.thanhToan = thanhToan;
        this.ngay = ngay;
    }

    public int getMaHd() {
        return maHd;
    }

    public void setMaHd(int maHd) {
        this.maHd = maHd;
    }

    public int getMaNv() {
        return maNv;
    }

    public void setMaNv(int maNv) {
        this.maNv = maNv;
    }

    public int getMaKh() {
        return maKh;
    }

    public void setMaKh(int maKh) {
        this.maKh = maKh;
    }

    public int getMaSp() {
        return maSp;
    }

    public void setMaSp(int maSp) {
        this.maSp = maSp;
    }

    public int getTien() {
        return tien;
    }

    public void setTien(int tien) {
        this.tien = tien;
    }

    public String getThanhToan() {
        return thanhToan;
    }

    public void setThanhToan(String thanhToan) {
        this.thanhToan = thanhToan;
    }

    public String getNgay() {
        return ngay;
    }

    public void setNgay(String ngay) {
        this.ngay = ngay;
    }
}
