package com.example.duannhom10.SQL;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.duannhom10.Model.KhachHang;

import java.util.ArrayList;

public class KhachHangDao {
    private Context contextt;
    private SQLiteDatabase sqLiteDatabase;
    private Database database;

    public KhachHangDao(Context contextt) {
        this.contextt = contextt;
        database = new Database(contextt);
        sqLiteDatabase = database.getWritableDatabase();
    }
    public int Insert(KhachHang khachHang){
        ContentValues values = new ContentValues();
        values.put("maKh",khachHang.getMaKh());
        values.put("hoTen",khachHang.getTenKh());
        values.put("diaChi",khachHang.getDiaChi());
        values.put("SoDt",khachHang.getSoDt());

        long kq = sqLiteDatabase.insert("KHACHHANG",null,values);
        if(kq<=0){
            return -1;
        }
        return 1;
    }
    public ArrayList<KhachHang> getAllKhachHang(){
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT *FROM KHACHHANG",null);
        ArrayList<KhachHang> list = new ArrayList<>();
        if(cursor.getCount()!=0){
            cursor.moveToFirst();
            do{
                list.add(new KhachHang(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getInt(3)));
            }while (cursor.moveToNext());
        }
        return list;
    }
    public int delete(KhachHang khachHang){
        int kq = sqLiteDatabase.delete("KHACHHANG","maKh=?",new String[]{String.valueOf(khachHang.getMaKh())});
        if(kq<=0){
            return -1;
        }
        return 1;
    }
    public int update(KhachHang khachHang){
        ContentValues values = new ContentValues();
        values.put("maKh",khachHang.getMaKh());
        values.put("hoTen",khachHang.getTenKh());
        values.put("diaChi",khachHang.getDiaChi());
        values.put("SoDt",khachHang.getSoDt());

        int kq = sqLiteDatabase.update("KHACHHANG",values,"maKh=?",new String[]{String.valueOf(khachHang.getMaKh())});
        if(kq<=0){
            return -1;
        }
        return 1;
    }
    public KhachHang getID(int id){
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM KHACHHANG WHERE maKh=?",new String[]{String.valueOf(id)});
        ArrayList<KhachHang> list = new ArrayList<>();

        if(cursor.getCount()!=0){
            cursor.moveToFirst();
            list.add(new KhachHang(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getInt(3)));
        }
        return list.get(0);
    }
}
