package com.example.duannhom10.SQL;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.duannhom10.Model.SanPham;

import java.util.ArrayList;

public class SanPhamDao {
    private Context contextt;
    private SQLiteDatabase sqLiteDatabase;
    private Database database;

    public SanPhamDao(Context contextt) {
        this.contextt = contextt;
        database = new Database(contextt);
        sqLiteDatabase = database.getWritableDatabase();
    }
    public int Insert(SanPham sp){
        ContentValues values = new ContentValues();
        values.put("tenSP",sp.getTenSp());
        values.put("moTa",sp.getMoTa());
        values.put("maDm",sp.getMaDm());
        values.put("soLuong",sp.getSoLuong());
        values.put("Gia",sp.getGia());

        long kq = sqLiteDatabase.insert("SANPHAM",null,values);
        if(kq<=0){
            return -1;
        }
        return 1;
    }
    public ArrayList<SanPham> getAllSP(){
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT *FROM SANPHAM",null);
        ArrayList<SanPham> list = new ArrayList<>();
        if(cursor.getCount()!=0){
            cursor.moveToFirst();
            do{
                list.add(new SanPham(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getInt(3),cursor.getInt(4),cursor.getInt(5)));
            }while (cursor.moveToNext());
        }
        return list;
    }
    public int delete(SanPham sp){
        int kq = sqLiteDatabase.delete("SANPHAM","maSP=?",new String[]{String.valueOf(sp.getMaSp())});
        if(kq<=0){
            return -1;
        }
        return 1;
    }
    public int update(SanPham sp){
        ContentValues values = new ContentValues();
        values.put("tenSP",sp.getTenSp());
        values.put("moTa",sp.getMoTa());
        values.put("maDm",sp.getMaDm());
        values.put("soLuong",sp.getSoLuong());
        values.put("Gia",sp.getGia());

        int kq = sqLiteDatabase.update("SANPHAM",values,"maSP=?",new String[]{String.valueOf(sp.getMaSp())});
        if(kq<=0){
            return -1;
        }
        return 1;
    }
    public SanPham getID(int id){
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM SANPHAM WHERE maSP=?",new String[]{String.valueOf(id)});
        ArrayList<SanPham> list = new ArrayList<>();
        cursor.moveToFirst();
        list.add(new SanPham(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getInt(3),cursor.getInt(4),cursor.getInt(5)));
        SanPham sp = list.get(0);
        return sp;

    }
}
