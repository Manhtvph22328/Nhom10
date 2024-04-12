package com.example.duannhom10;

import static android.app.PendingIntent.getActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.duannhom10.Fragment.DoanhThu.DoanhThuFragment;
import com.example.duannhom10.Fragment.DoanhThu.ThongkeCuaHang;
import com.example.duannhom10.Fragment.SanPhamFragment;
import com.example.duannhom10.Model.NhanVien;

import com.example.duannhom10.SQL.NhanVienDao;

import java.util.ArrayList;
import java.util.List;

public class CheckUser extends AppCompatActivity {
    private Button btnok;
    private EditText ed_ma;
    private NhanVienDao nhanVienDao;
    private List<NhanVien> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_user);
        ed_ma = findViewById(R.id.ed_maUser);
        btnok = findViewById(R.id.btnxacnhanuser);

        nhanVienDao = new NhanVienDao(CheckUser.this);
        List<String> list = new ArrayList<>();

        if (list.size() > 0) {
            ed_ma.setText(list.get(0));
        }
        arrayList = nhanVienDao.getAllNhanVien();

        Log.e("DU LIEU DATABASE ", ""+arrayList.size());
        btnok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ma = ed_ma.getText().toString();

                for (int i = 0; i < arrayList.size(); i++) {
                    NhanVien nhanVien = arrayList.get(i);
                    Log.e("admin",nhanVien.getMaNv());
                    if (ma.equals(nhanVien.getMaNv())) {
                        Toast.makeText(CheckUser.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(CheckUser.this, ThongkeCuaHang.class);
                        intent.putExtra("TK", ma);
                        startActivity(intent);
                        return;
                    }
                }
                if (ma.length() == 0) {
                    Toast.makeText(CheckUser.this, "Không để trống", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(CheckUser.this, "Sai thông tin", Toast.LENGTH_SHORT).show();
                }
            }
        });
        findViewById(R.id.btnhome1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CheckUser.this, DoanhThuFragment.class);
                startActivity(intent);
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        arrayList = nhanVienDao.getAllNhanVien();
    }
}