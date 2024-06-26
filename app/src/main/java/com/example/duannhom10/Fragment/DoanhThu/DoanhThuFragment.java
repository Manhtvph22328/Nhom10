package com.example.duannhom10.Fragment.DoanhThu;

import static android.content.Context.MODE_PRIVATE;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.duannhom10.CheckUser;
import com.example.duannhom10.Fragment.DanhMucFragment;
import com.example.duannhom10.Login;
import com.example.duannhom10.MainActivity;
import com.example.duannhom10.Model.DanhMuc;
import com.example.duannhom10.Model.NhanVien;
import com.example.duannhom10.R;
import com.example.duannhom10.SQL.DanhMucDao;
import com.example.duannhom10.SQL.NhanVienDao;

import java.util.ArrayList;
import java.util.List;

public class DoanhThuFragment extends Fragment {

    ImageButton btnad, btnnv;
    public DoanhThuFragment() {
        // Required empty public constructor
    }


    public static DoanhThuFragment newInstance(String param1, String param2) {
        DoanhThuFragment fragment = new DoanhThuFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_doanh_thu, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnad = view.findViewById(R.id.btnAddt);
        btnnv = view.findViewById(R.id.btnnviendt);

        btnad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), CheckUser.class);
                startActivity(intent);
            }
        });

        btnnv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), CheckUser.class);
                startActivity(intent);
            }
        });
    }
}