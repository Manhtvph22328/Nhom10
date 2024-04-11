package com.example.duannhom10.SQL;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.duannhom10.Model.HoaDon;
import com.example.duannhom10.Model.SanPham;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class HoaDonDao {
    private Context contextt;
    private SQLiteDatabase sqLiteDatabase;
    private Database database;
    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");


    public HoaDonDao(Context contextt) {
        this.contextt = contextt;
        database = new Database(contextt);
        sqLiteDatabase = database.getWritableDatabase();
    }

    public int Insert(HoaDon HD) {
        ContentValues values = new ContentValues();
        values.put("maNv", HD.getMaNv());
        values.put("maKh", HD.getMaKh());
        values.put("maSP", HD.getMaSp());
        values.put("Tien", HD.getTien());
        values.put("thanhToan", "");
        values.put("ngay", format.format(HD.getNgay()));

        long kq = sqLiteDatabase.insert("HOADON", null, values);
        if (kq <= 0) {
            return -1;
        }
        return 1;
    }

    public ArrayList<HoaDon> getAllHoaDon() {
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT *FROM HOADON", null);
        ArrayList<HoaDon> list = new ArrayList<>();
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            do {
                try {
                    list.add(new HoaDon(cursor.getInt(0),
                            cursor.getInt(1),
                            cursor.getInt(2),
                            cursor.getInt(3),
                            cursor.getInt(4),
                            cursor.getInt(5),
                            format.parse(cursor.getString(6))));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }while (cursor.moveToNext());
        }
        return list;
    }

    public int delete(HoaDon HD) {
        int kq = sqLiteDatabase.delete("HOADON", "maHd=?", new String[]{String.valueOf(HD.getMaHd())});
        if (kq <= 0) {
            return -1;
        }
        return 1;
    }

    public int update(HoaDon HD) {
        ContentValues values = new ContentValues();
        values.put("maNv", HD.getMaNv());
        values.put("maKh", HD.getMaKh());
        values.put("maSP", HD.getMaSp());
        values.put("Tien", HD.getTien());
        values.put("thanhToan", "");
        values.put("ngay",format.format(HD.getNgay()));

        int kq = sqLiteDatabase.update("HOADON", values, "maHD=?", new String[]{String.valueOf(HD.getMaHd())});
        if (kq <= 0) {
            return -1;
        }
        return 1;
    }

    public ArrayList<SanPham> top(){
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT SANPHAM.maSp,SANPHAM.tenSP, SANPHAM.gia, SANPHAM.soLuong,COUNT(SANPHAM.maSP) FROM SANPHAM JOIN HOADON ON SANPHAM.maSp=HOADON.maSp GROUP BY HOADON.maSP ORDER BY COUNT(SANPHAM.maSp) DESC LIMIT 10", null);
        ArrayList<SanPham> list = new ArrayList<>();
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            do {
                list.add(new SanPham(cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getInt(2),
                        cursor.getInt(3)));
            } while (cursor.moveToNext());
        }
        return list;
    }

    public int Doanhthu(String date1, String date2) {
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT SUM(TongTien) FROM HOADON WHERE Ngay BETWEEN ? AND ?", new String[]{date1, date2});
        int dt = 0;
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            dt = cursor.getInt(0);
        }
        return dt;
    }

    public ArrayList<HoaDon> getAllByMaHd(int ma) {
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM HOADON WHERE maHd=?   ", new String[]{String.valueOf(ma)});
        ArrayList<HoaDon> list = new ArrayList<>();
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            do {
                try {
                    list.add(new HoaDon(cursor.getInt(0),
                            cursor.getInt(1),
                            cursor.getInt(2),
                            cursor.getInt(3),
                            cursor.getInt(4),
                            cursor.getInt(5),
                            format.parse(cursor.getString(6))));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            } while (cursor.moveToNext());
        }
        return list;
    }
}
