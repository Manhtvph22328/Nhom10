package com.example.duannhom10.SQL;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.duannhom10.Model.DanhMuc;

import java.util.ArrayList;

public class DanhMucDao {
    private Context context;
    private SQLiteDatabase sqLiteDatabase;
    private Database database;

    public DanhMucDao(Context context) {
        this.context = context;
        database = new Database(context);
        sqLiteDatabase = database.getWritableDatabase();
    }
    public int Insert(DanhMuc danhMuc){
        ContentValues values = new ContentValues();
        values.put("tenDm", danhMuc.getTenDm());
        long kq = sqLiteDatabase.insert("DANHMUC",null,values);
        if (kq<=0){
            return -1;
        }
        return 1;
    }
    public ArrayList<DanhMuc> getAllDanhMuc(){
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT *FROM DANHMUC",null);
        ArrayList<DanhMuc> list = new ArrayList<>();
        if (cursor.getCount()!=0){
            cursor.moveToFirst();
            do {
                list.add(new DanhMuc(cursor.getInt(0),cursor.getString(1)));
            }while (cursor.moveToNext());
        }
        return list;
    }
    public int delete(DanhMuc danhMuc){
        int kq = sqLiteDatabase.delete("DANHMUC","maDm=?",new String[]{String.valueOf(danhMuc.getMaDm())});
        if (kq<=0){
            return -1;
        }
        return 1;
    }
    public int update(DanhMuc danhMuc){
        ContentValues values = new ContentValues();
        values.put("maDm",danhMuc.getMaDm());
        values.put("tenDm", danhMuc.getTenDm());
        int kq = sqLiteDatabase.update("DANHMUC",values,"maDm=?",new String[]{String.valueOf(danhMuc.getMaDm())});
        if (kq<=0){
            return -1;
        }
        return 1;
    }
    public DanhMuc getID(int id){
        Cursor cursor= sqLiteDatabase.rawQuery("SELECT *FROM DANHMUC WHERE maDm= ?",new String[]{String.valueOf(id)});
        ArrayList<DanhMuc> list = new ArrayList<>();
        cursor.moveToFirst();
        list.add(new DanhMuc(cursor.getInt(0),cursor.getString(1)));
        return list.get(0);
    }
}
