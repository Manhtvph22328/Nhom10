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
            " maDm TEXT not null,"+
            " soLuong INTEGER not null,"+
            " giaSP INTEGER not null);";

    public static final String SQL_NhanVien = "create table NHANVIEN ("+
            " maNv INTEGER primary key AUTOINCREMENT,"+
            " tenNv TEXT not null,"+
            " email TEXT not null,"+
            " namSinh Date not null,"+
            " matKhau INTEGER not null);";
    public static final String SQL_KhachHang = "create table KHACHHANG ("+
            " maKh INTEGER primary key AUTOINCREMENT,"+
            " tenKh TEXT not null,"+
            " diChi TEXT not null,"+
            " dienThoai INTEGER not null);";

    public static final String SQL_Admin = "create table ADMIN ("+
            " maAD INTEGER primary key AUTOINCREMENT,"+
            " ten TEXT not null,"+
            " matKhau INTEGER not null);";
    public static final String SQL_DanhMuc = "create table DANHMUC ("+
            " maDm INTEGER primary key AUTOINCREMENT,"+
            " tenDm INTEGER not null);";

    public static final String SQL_HoaDon = "create table HOADON ("+
            " maHD INTEGER primary key AUTOINCREMENT,"+
            " maNv INTEGER not null,"+
            " maKh INTEGER not null,"+
            " maSP INTEGER not null,"+
            " thanhToan TEXT not null,"+
            " Ngay INTEGER not null,"+
            " Tien INTEGER not null);";

    public static final String Insert_Admin = "INSERT INTO ADMIN(maAD, hoTen, matKhau)VALUES"+
            "('admin','admin','aaaa')";

    public Database(@Nullable Context context) {
        super(context, "oppohello.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_SanPham);
        db.execSQL(SQL_NhanVien);
        db.execSQL(SQL_KhachHang);
        db.execSQL(SQL_Admin);
        db.execSQL(SQL_DanhMuc);
        db.execSQL(SQL_HoaDon);
        db.execSQL(Insert_Admin);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i2) {
        db.execSQL("DROP TABLE IF EXISTS SANPHAM");
        db.execSQL("DROP TABLE IF EXISTS NHANVIEN");
        db.execSQL("DROP TABLE IF EXISTS KHACHHANG");
        db.execSQL("DROP TABLE IF EXISTS ADMIN");
        db.execSQL("DROP TABLE IF EXISTS DANHMUC");
        db.execSQL("DROP TABLE IF EXISTS HOADON");
        onCreate(db);
    }
}
