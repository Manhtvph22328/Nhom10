package com.example.duannhom10;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.duannhom10.Fragment.DoimkFragment;
import com.example.duannhom10.Model.NhanVien;
import com.example.duannhom10.SQL.NhanVienDao;

import java.util.ArrayList;
import java.util.List;
import java.text.ParseException;
public class Login extends AppCompatActivity {

    private ImageButton btndn;
    private NhanVienDao nhanVienDao;
    private EditText ed_tk, ed_mk;
    private CheckBox chk_luu;
    private List<NhanVien> arrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ed_tk = findViewById(R.id.ed_login_TK);
        ed_mk = findViewById(R.id.ed_login_MK);
        chk_luu = findViewById(R.id.chk_luuMK);
        btndn = findViewById(R.id.btndangnhap);

        nhanVienDao = new NhanVienDao(Login.this);
        DoimkFragment doiMkFragment = new DoimkFragment();
        List<String> list = new ArrayList<>();
        list = readPreference();

        if (list.size() > 0) {
            ed_tk.setText(list.get(0));
            ed_mk.setText(list.get(1));
            chk_luu.setChecked(Boolean.parseBoolean(list.get(2)));
        }

            arrayList = nhanVienDao.getAllNhanVien();

        Log.e("DU LIEU DATABASE ", ""+arrayList.size());
        btndn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tk = ed_tk.getText().toString();
                String mk = ed_mk.getText().toString();
                boolean status = chk_luu.isChecked();

                    for (int i = 0; i < arrayList.size(); i++) {
                        NhanVien nhanVien = arrayList.get(i);
                        Log.e("User",nhanVien.getMaNv()+ ""+nhanVien.getMatKhau());
                        if (tk.equals(nhanVien.getMaNv()) && mk.equals(nhanVien.getMatKhau())) {
                            savePreference(tk, mk, status);
                            Toast.makeText(Login.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Login.this, MainActivity.class);
                            intent.putExtra("TK", tk);
                            startActivity(intent);
                            return;
                        }
                    }
                if (tk.length() == 0 || mk.length() == 0) {
                    Toast.makeText(Login.this, "Không để trống", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Login.this, "Sai thông tin", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void savePreference(String tk, String mk, Boolean status) {
        SharedPreferences sharedPreferences = getSharedPreferences("MY_FILE", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (!status) {
            editor.clear();
        } else {
            editor.putString("TK", tk);
            editor.putString("MK", mk);
            editor.putBoolean("CHK", status);
        }
        editor.commit();
    }
    private List<String> readPreference() {
        List<String> list = new ArrayList<>();
        SharedPreferences s = getSharedPreferences("MY_FILE", MODE_PRIVATE);
        list.add(s.getString("TK", ""));
        list.add(s.getString("MK", ""));
        list.add(String.valueOf(s.getBoolean("CHK", false)));
        return list;
    }
    @Override
    protected void onResume() {
        super.onResume();
        arrayList = nhanVienDao.getAllNhanVien();
    }
}