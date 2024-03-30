package com.example.duannhom10.Fragment.DoanhThu;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.duannhom10.CheckUser;
import com.example.duannhom10.Fragment.DanhMucFragment;
import com.example.duannhom10.Login;
import com.example.duannhom10.MainActivity;
import com.example.duannhom10.R;

public class DoanhThuFragment extends Fragment {

    Button btndth;
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
        btndth = view.findViewById(R.id.btndoanhthu);
        btndth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireActivity(), CheckUser.class);
                startActivity(intent);
            }
        });
    }
}