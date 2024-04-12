package com.example.duannhom10.Fragment.DoanhThu;

import static android.app.PendingIntent.getActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.example.duannhom10.CheckUser;
import com.example.duannhom10.Fragment.SanPhamFragment;
import com.example.duannhom10.MainActivity;
import com.example.duannhom10.R;
import com.example.duannhom10.SQL.HoaDonDao;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ThongkeCuaHang extends AppCompatActivity {
    private EditText ed_tuNgay,ed_denNgay;
    private TextView tv_doanhThu;
    private int day,month,year;
    private HoaDonDao hoaDonDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thongke_cua_hang);

        ed_tuNgay = findViewById(R.id.ed_DT_tuNgay);
        ed_denNgay = findViewById(R.id.ed_DT_denNgay);
        tv_doanhThu = findViewById(R.id.tvdoanhthu);

        Calendar calendar = Calendar.getInstance();
        day = calendar.get(Calendar.DAY_OF_MONTH);
        month = calendar.get(Calendar.MONTH);
        year = calendar.get(Calendar.YEAR);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        ed_tuNgay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(ThongkeCuaHang.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        calendar.set(year,month,dayOfMonth);
                        ed_tuNgay.setText(simpleDateFormat.format(calendar.getTime()));
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });
        ed_denNgay.setOnClickListener(v -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(ThongkeCuaHang.this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    calendar.set(year,month,dayOfMonth);
                    ed_denNgay.setText(simpleDateFormat.format(calendar.getTime()));
                }
            },year,month,day);
            datePickerDialog.show();
        });
        findViewById(R.id.btndoanhthu).setOnClickListener(v -> {
            hoaDonDao = new HoaDonDao(ThongkeCuaHang.this);
            tv_doanhThu.setText(String.valueOf(hoaDonDao.Doanhthu(ed_tuNgay.getText().toString(),ed_denNgay.getText().toString())));
        });
        findViewById(R.id.btnhome).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ThongkeCuaHang.this, SanPhamFragment.class);
                startActivity(intent);
            }
        });
    }
}