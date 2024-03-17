package com.example.duannhom10.SQL;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.duannhom10.Model.Admin;

import java.util.ArrayList;

public class AdminDao {
    private Context context;
    private SQLiteDatabase sqLiteDatabase;
    private Database database;

    public AdminDao(Context context) {
        this.context = context;
        database = new Database(context);
        sqLiteDatabase = database.getWritableDatabase();
    }

    public int Insert(Admin admin){
        ContentValues values = new ContentValues();
        values.put("maAd", admin.getMaAd());
        values.put("hoTen", admin.getTenAd());
        values.put("matKhau", admin.getMatKhau());
        long kq = sqLiteDatabase.insert("ADMIN", null, values);
        if (kq<=0){
            return -1;
        }
        return 1;
    }

    public ArrayList<Admin> getAllAdmin(){
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT *FROM ADMIN",null);
        ArrayList<Admin> list = new ArrayList<>();
        if (cursor.getCount()!=0){
            cursor.moveToFirst();
            do {
                list.add(new Admin(cursor.getInt(0),cursor.getString(1),cursor.getString(2)));
            }while (cursor.moveToNext());
        }
        return list;
    }
    public int delete(Admin admin){
        int kq = sqLiteDatabase.delete("ADMIN", "maTT=?", new String[]{String.valueOf(admin.getMaAd())});
        if (kq<=0){
            return -1;
        }
        return 1;
    }
    public int update(Admin admin){
        ContentValues values = new ContentValues();
        values.put("maTT", admin.getMaAd());
        values.put("hoTen", admin.getTenAd());
        values.put("matKhau", admin.getMatKhau());
        int kq = sqLiteDatabase.update("THUTHU", values, "maTT=?", new String[]{String.valueOf(admin.getMaAd())});
        if (kq <=0){
            return -1;
        }
        return 1;
    }
    public Admin getId(String id){
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM ADMIN WHERE maAd=?", new String[]{id});
        ArrayList<Admin> list = new ArrayList<>();
        if (cursor.getCount()!=0){
            cursor.moveToFirst();
            list.add(new Admin(cursor.getInt(0),cursor.getString(1),cursor.getString(2)));
        }
        if (!list.isEmpty()){
            return list.get(0);
        }else {
            return null;
        }

    }
}
