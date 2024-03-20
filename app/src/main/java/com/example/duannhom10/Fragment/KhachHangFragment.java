package com.example.duannhom10.Fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.duannhom10.Adapter.KhachHangAdapter;
import com.example.duannhom10.Model.KhachHang;
import com.example.duannhom10.R;
import com.example.duannhom10.SQL.KhachHangDao;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


public class KhachHangFragment extends Fragment{

    private RecyclerView recyclerView;
    private FloatingActionButton actionButton;
    private ArrayList<KhachHang> list = new ArrayList<>();
    private KhachHangAdapter adapter;
    private KhachHangDao khachHangDao;

    public KhachHangFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_khach_hang, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recycleView_Kh);
        actionButton = view.findViewById(R.id.floatingactionKh);
        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_Kh();
            }
        });
    }
    @SuppressLint("MissingInflatedId")
    public void dialog_Kh(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog_kh,null);

        EditText ed_tenTV = v.findViewById(R.id.dialog_Kh_tenKh);
        EditText ed_diaChi = v.findViewById(R.id.dialog_Kh_diachi);
        EditText ed_soDt = v.findViewById(R.id.dialog_Kh_sodt);

        ImageButton btn_luu,btn_huy;
        btn_luu = v.findViewById(R.id.btnthemkh);
        btn_huy = v.findViewById(R.id.btnhuykh);
        builder.setView(v);
        AlertDialog alertDialog = builder.create();

        btn_luu.setOnClickListener(v1 ->{
            if(ed_tenTV.length()==0 || ed_diaChi.length()==0 || ed_soDt.length()==0 ){
                Toast.makeText(getActivity(), "Không để trống", Toast.LENGTH_SHORT).show();
            }else{
                khachHangDao = new KhachHangDao(getActivity());
                KhachHang khachHang = new KhachHang();
                khachHang.setTenKh(ed_tenTV.getText().toString());
                khachHang.setDiaChi(ed_diaChi.getText().toString());
                khachHang.setSoDt(Integer.parseInt(ed_soDt.getText().toString()));

                int kq = khachHangDao.Insert(khachHang);
                if (kq == -1) {
                    Toast.makeText(getActivity(), "Thêm thất bại", Toast.LENGTH_SHORT).show();
                }
                if (kq == 1) {
                    Toast.makeText(getActivity(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                }
                onResume();
                alertDialog.cancel();
            }
        });
        btn_huy.setOnClickListener(v2 ->{
            alertDialog.cancel();
        });
        alertDialog.show();
    }

    @Override
    public void onResume() {
        super.onResume();
        khachHangDao = new KhachHangDao(getActivity());
        list.clear();
        list= khachHangDao.getAllKhachHang();
        adapter = new KhachHangAdapter(getActivity());
        adapter.setData(list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }
}