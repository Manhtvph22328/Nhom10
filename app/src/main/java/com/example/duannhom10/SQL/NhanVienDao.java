package com.example.duannhom10.SQL;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.duannhom10.Model.NhanVien;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class NhanVienDao {
    private Context contextt;
    private SQLiteDatabase sqLiteDatabase;
    private Database database;

    public NhanVienDao(Context context) {
        this.contextt = context;
        database = new Database(contextt);
        sqLiteDatabase = database.getWritableDatabase();
    }
    public int Insert(NhanVien NV){
        ContentValues values = new ContentValues();
        values.put("maNv",NV.getMaNv());
        values.put("tenNv",NV.getHoTen());
        values.put("namSinh",NV.getNamSinh());
        values.put("email",NV.getEmail());
        values.put("matKhau",NV.getMatKhau());
        long kq = sqLiteDatabase.insert("NHANVIEN",null,values);
        if(kq<=0){
            return -1;
        }
        return 1;

    }
    public ArrayList<NhanVien> getAllNhanVien() {
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT *FROM NHANVIEN",null);
        ArrayList<NhanVien> list = new ArrayList<>();
        if(cursor.getCount()!=0){
            cursor.moveToFirst();
            do{
                list.add(new NhanVien(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4)));
            }while (cursor.moveToNext());
        }
        return list;
    }
    public int delete(NhanVien NV){
        int kq = sqLiteDatabase.delete("NHANVIEN","maNv=?",new String[]{String.valueOf(NV.getMaNv())});
        if(kq<=0){
            return -1;
        }
        return 1;
    }
    public int update(NhanVien NV){
        ContentValues values = new ContentValues();
        values.put("maNv",NV.getMaNv());
        values.put("tenNv",NV.getHoTen());
        values.put("namSinh",NV.getNamSinh());
        values.put("email",NV.getEmail());
        values.put("matKhau",NV.getMatKhau());
        int kq = sqLiteDatabase.update("NHANVIEN",values,"maNv=?",new String[]{String.valueOf(NV.getMaNv())});
        if(kq<=0){
            return -1;
        }
        return 1;
    }
    public NhanVien getId(String id) {

        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM NHANVIEN WHERE maNv=?", new String[]{id});
        ArrayList<NhanVien> list = new ArrayList<>();
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            list.add(new NhanVien(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4)));
        }
        if (!list.isEmpty()) {
            return list.get(0);
        } else {

            return null;
        }
    }
}
