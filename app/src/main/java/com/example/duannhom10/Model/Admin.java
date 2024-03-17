package com.example.duannhom10.Model;

public class Admin {
    private int maAd;
    private String tenAd;
    private String matKhau;

    public Admin() {
    }

    public Admin(int maAd, String tenAd, String matKhau) {
        this.maAd = maAd;
        this.tenAd = tenAd;
        this.matKhau = matKhau;
    }

    public int getMaAd() {
        return maAd;
    }

    public void setMaAd(int maAd) {
        this.maAd = maAd;
    }

    public String getTenAd() {
        return tenAd;
    }

    public void setTenAd(String tenAd) {
        this.tenAd = tenAd;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }
}
