package com.example.duannhom10.Fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.duannhom10.Model.NhanVien;
import com.example.duannhom10.R;
import com.example.duannhom10.SQL.NhanVienDao;


public class DoimkFragment extends Fragment {

    private EditText mkcu, mkmoi, mklai;
    private NhanVienDao nhanVienDao;
    private Context context;

    public DoimkFragment() {
        // Required empty public constructor
    }

    public static DoimkFragment newInstance(String param1, String param2) {
        DoimkFragment fragment = new DoimkFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_doimk, container, false);
    }
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mkcu = view.findViewById(R.id.ed_doimk_mkcu);
        mkmoi = view.findViewById(R.id.ed_doimk_mkmoi);
        mklai = view.findViewById(R.id.ed_doimk_mknhaclai);
        nhanVienDao = new NhanVienDao(getActivity());
        int maNV = Integer.parseInt(getArguments().getString("TK"));
        view.findViewById(R.id.btndoimk).setOnClickListener(v -> {
            NhanVien nv = nhanVienDao.getId(maNV);
            if(mkcu.getText().toString().equals(nv.getMatKhau()) && mkmoi.getText().toString().equals(mklai.getText().toString())){
                nv.setMatKhau(mkmoi.getText().toString());
                int kq = nhanVienDao.update(nv);
                if (kq == -1) {
                    Toast.makeText(getActivity(), "Đổi mật khẩu thất bại", Toast.LENGTH_SHORT).show();
                }
                if (kq == 1) {
                    Toast.makeText(getActivity(), "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                }
            }
            else if(mkcu.length() ==0|| mkmoi.length()==0||mklai.length() == 0){
                Toast.makeText(getActivity(), "Không để trống", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(getActivity(), "Sai thông tin", Toast.LENGTH_SHORT).show();
            }
        });
    }
}