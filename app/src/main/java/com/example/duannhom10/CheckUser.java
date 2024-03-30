package com.example.duannhom10;

import static android.app.PendingIntent.getActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.duannhom10.Fragment.DoanhThu.CuaHangFragment;
import com.example.duannhom10.Model.DanhMuc;
import com.example.duannhom10.Model.NhanVien;
import com.example.duannhom10.SQL.DanhMucDao;
import com.example.duannhom10.SQL.NhanVienDao;

import java.util.ArrayList;
import java.util.List;

public class CheckUser extends AppCompatActivity {
    private ImageButton btnad, btnnv;
    private NhanVienDao nhanVienDao;
    private List<NhanVien> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_user);

        btnad = findViewById(R.id.btnAddt);
        btnnv = findViewById(R.id.btnnviendt);

        nhanVienDao = new NhanVienDao(CheckUser.this);
        List<String> list = new ArrayList<>();
        arrayList = nhanVienDao.getAllNhanVien();

        Log.e("DU LIEU DATABASE ", ""+arrayList.size());

        btnad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_chkUser();
            }
        });
    }
    public void dialog_chkUser(){
        AlertDialog.Builder builder = new AlertDialog.Builder(CheckUser.this);
        LayoutInflater inflater = getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog_checkuser,null);

        EditText ma = v.findViewById(R.id.ed_maUser);
        ImageButton btnad = v.findViewById(R.id.btnAddt);
        ImageButton btnnv = v.findViewById(R.id.btnnviendt);

        builder.setView(v);
        AlertDialog alertDialog = builder.create();
        btnad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tk = ma.getText().toString();

                for (int i = 0; i < arrayList.size(); i++) {
                    NhanVien nhanVien = arrayList.get(i);
                    Log.e("User",nhanVien.getMaNv());
                    if (tk.equals(nhanVien.getMaNv())) {
                        Toast.makeText(CheckUser.this, "Xac Nhan thành công", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(CheckUser.this, CuaHangFragment.class);
                        intent.putExtra("TK", tk);
                        startActivity(intent);
                        alertDialog.dismiss();
                        return;
                    }
                }
                if (tk.length() == 0) {
                    Toast.makeText(CheckUser.this, "Không để trống", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(CheckUser.this, "Sai thông tin", Toast.LENGTH_SHORT).show();
                }
            }
        });
        alertDialog.show();
    }
}