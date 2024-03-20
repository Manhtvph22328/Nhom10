package com.example.duannhom10.Fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.duannhom10.Adapter.NhanVienAdapter;
import com.example.duannhom10.Model.NhanVien;
import com.example.duannhom10.R;
import com.example.duannhom10.SQL.NhanVienDao;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class NhanVienFragment extends Fragment{

    private RecyclerView recyclerView;
    private ImageView img ;
    private FloatingActionButton actionButton;
    private NhanVienDao nhanVienDao;
    private NhanVienAdapter adapter;
    private int day,month,year;

    public NhanVienFragment() {
        // Required empty public constructor
    }
    private ArrayList<NhanVien> list = new ArrayList<>();

    public static NhanVienFragment newInstance() {
        NhanVienFragment fragment = new NhanVienFragment();
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
        return inflater.inflate(R.layout.fragment_nhan_vien, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recycleView_NV);
        actionButton = view.findViewById(R.id.floating_nhanvien);
        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog_Nv();
            }
        });
    }

    public void Dialog_Nv() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater layoutInflater = getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.dialog_nv, null);

        EditText maNV = view.findViewById(R.id.dialog_Nv_maNv);
        EditText tennv = view.findViewById(R.id.dialog_Nv_tenNv);
        EditText mail = view.findViewById(R.id.dialog_Nv_mail);
        EditText namSinh = view.findViewById(R.id.dialog_Nv_namSNv);
        EditText matKhau = view.findViewById(R.id.dialog_Nv_mkNv);

        builder.setView(view);
        AlertDialog alertDialog = builder.create();

        Calendar calendar = Calendar.getInstance();
        day = calendar.get(Calendar.DAY_OF_MONTH);
        month = calendar.get(Calendar.MONTH);
        year = calendar.get(Calendar.YEAR);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

        namSinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        calendar.set(year,month,dayOfMonth);
                        namSinh.setText(simpleDateFormat.format(calendar.getTime()));
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });

        view.findViewById(R.id.btnthemnv).setOnClickListener(v -> {
            if (tennv.length() == 0 || mail.length() == 0 || namSinh.length() == 0 || matKhau.length() == 0 ){
                Toast.makeText(getActivity(), "Không được để trống", Toast.LENGTH_SHORT).show();
            }
            else {

                nhanVienDao = new NhanVienDao(getActivity());
                NhanVien nhanVien = new NhanVien();

                nhanVien.setHoTen(tennv.getText().toString());
                nhanVien.setNamSinh(namSinh.getText().toString());
                nhanVien.setEmail(mail.getText().toString());
                nhanVien.setMatKhau(matKhau.getText().toString());

                int kq = nhanVienDao.Insert(nhanVien);
                if (kq == -1) {
                    Toast.makeText(getActivity(), "Thêm thất bại", Toast.LENGTH_SHORT).show();
                }
                if (kq == 1) {
                    Toast.makeText(getActivity(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                    Log.e("nhanvien",""+list.size());
                }
            }
            onResume();
            alertDialog.cancel();
        });
        view.findViewById(R.id.btnhuynv).setOnClickListener(v -> {
            alertDialog.cancel();
        });
        alertDialog.show();
    }
    public void onResume(){
        super.onResume();
        nhanVienDao = new NhanVienDao(getActivity());
        list.clear();

            list = nhanVienDao.getAllNhanVien();

        adapter = new NhanVienAdapter(getActivity());
        adapter.setData(list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }
}