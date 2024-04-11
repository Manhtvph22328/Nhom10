package com.example.duannhom10.SQL;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Database extends SQLiteOpenHelper {

    public static final String SQL_SanPham = "create table SANPHAM ("+
            " maSP INTEGER primary key AUTOINCREMENT,"+
            " tenSP TEXT not null,"+
            " moTa TEXT not null,"+
            " maDm INTEGER REFERENCES DANHMUC(maDm),"+
            " soLuong INTEGER not null,"+
            " Gia INTEGER not null);";

    public static final String SQL_NhanVien = "create table NHANVIEN ("+
            " maNv INTEGER primary key AUTOINCREMENT,"+
            " tenNv TEXT not null,"+
            " namSinh TEXT not null,"+
            " email TEXT not null,"+
            " matKhau TEXT not null);";
    public static final String SQL_KhachHang = "create table KHACHHANG ("+
            " maKh INTEGER primary key,"+
            " tenKh TEXT not null,"+
            " diaChi TEXT not null,"+
            " soDt INTEGER not null);";
    public static final String SQL_DanhMuc = "create table DANHMUC ("+
            " maDm INTEGER primary key AUTOINCREMENT,"+
            " tenDm TEXT not null);";

    public static final String SQL_HoaDon = "create table HOADON ("+
            " maHD INTEGER primary key AUTOINCREMENT,"+
            " maNv INTEGER REFERENCES NHANVIEN(maNv),"+
            " maKh INTEGER REFERENCES KHACHHANG(maKh),"+
            " maSP INTEGER REFERENCES SANPHAM(maSP),"+
            " Tien INTEGER not null,"+
            " thanhToan TEXT not null,"+
            " Ngay DATE not null);";

    public static final String Insert_Admin = "INSERT INTO NHANVIEN(tenNv, namSinh, email, matKhau) VALUES"+
            "('admin', '01/01/1990', 'admin@example.com','aaaa')";

    public Database(@Nullable Context context) {
        super(context, "oppohello.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_SanPham);
        db.execSQL(SQL_NhanVien);
        db.execSQL(SQL_KhachHang);
        db.execSQL(SQL_DanhMuc);
        db.execSQL(SQL_HoaDon);
        db.execSQL(Insert_Admin);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i2) {
        db.execSQL("DROP TABLE IF EXISTS SANPHAM");
        db.execSQL("DROP TABLE IF EXISTS NHANVIEN");
        db.execSQL("DROP TABLE IF EXISTS KHACHHANG");
        db.execSQL("DROP TABLE IF EXISTS DANHMUC");
        db.execSQL("DROP TABLE IF EXISTS HOADON");
        onCreate(db);
    }
}
