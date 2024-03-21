package com.example.duannhom10.Fragment;

import android.app.AlertDialog;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.duannhom10.Adapter.DanhMucAdapter;
import com.example.duannhom10.Model.DanhMuc;
import com.example.duannhom10.R;
import com.example.duannhom10.SQL.DanhMucDao;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class DanhMucFragment extends Fragment {

    private RecyclerView recyclerView;
    private FloatingActionButton actionButton;
    private DanhMucDao danhMucDao;
    private DanhMucAdapter adapter;
    private ImageView img;
    private ArrayList<DanhMuc> list = new ArrayList<>();

    public DanhMucFragment() {
        // Required empty public constructor
    }

    public static DanhMucFragment newInstance(String param1, String param2) {
        DanhMucFragment fragment = new DanhMucFragment();
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
        return inflater.inflate(R.layout.fragment_danh_muc, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recy_Dm);
        actionButton = view.findViewById(R.id.floating_Dm);

        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_Dm();
            }
        });
    }

    public void dialog_Dm(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog_dm,null);

        EditText ed_ten = v.findViewById(R.id.dialog_Dm_tenDm);
        builder.setView(v);
        AlertDialog alertDialog = builder.create();

        v.findViewById(R.id.btnthemdm).setOnClickListener(v1 ->{
            if (ed_ten.length() == 0){
                Toast.makeText(getActivity(), "không được để trống ", Toast.LENGTH_SHORT).show();
            }else {
                danhMucDao = new DanhMucDao(getActivity());
                DanhMuc dm = new DanhMuc();
                dm.setTenDm(ed_ten.getText().toString());
                int kq = danhMucDao.Insert(dm);
                if (kq == -1) {
                    Toast.makeText(getActivity(), "Thêm thất bại", Toast.LENGTH_SHORT).show();
                }
                if (kq == 1) {
                    Toast.makeText(getActivity(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                }
            }
            onResume();
            alertDialog.cancel();
        });
        v.findViewById(R.id.btnhuydm).setOnClickListener(v1 -> {
            alertDialog.cancel();
        });

        alertDialog.show();
    }
    @Override
    public void onResume() {
        super.onResume();
        danhMucDao= new DanhMucDao(getActivity());
        list.clear();
        list = danhMucDao.getAllDanhMuc();
        adapter = new DanhMucAdapter(getActivity());
        adapter.setData(list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }
}