package com.example.duannhom10.Fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.duannhom10.Adapter.HoaDonAdapter;
import com.example.duannhom10.Model.HoaDon;
import com.example.duannhom10.Model.KhachHang;
import com.example.duannhom10.Model.NhanVien;
import com.example.duannhom10.Model.SanPham;
import com.example.duannhom10.R;
import com.example.duannhom10.SQL.HoaDonDao;
import com.example.duannhom10.SQL.KhachHangDao;
import com.example.duannhom10.SQL.NhanVienDao;
import com.example.duannhom10.SQL.SanPhamDao;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class HoaDonFragment extends Fragment implements View.OnClickListener{

    private RecyclerView recyclerView;
    private FloatingActionButton actionButton;
    private HoaDonAdapter adapter;
    private ArrayList<HoaDon> list = new ArrayList<>();
    private HoaDonDao hoaDonDao;
    private NhanVienDao nhanVienDao;
    private SanPhamDao spDAO;
    private KhachHangDao KhDao;
    private Spinner spn_tenNv,spn_tenSp, spn_tenKh, spn_ttoan;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public HoaDonFragment() {
        // Required empty public constructor
    }

    public static HoaDonFragment newInstance(String param1, String param2) {
        HoaDonFragment fragment = new HoaDonFragment();
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
        return inflater.inflate(R.layout.fragment_hoa_don, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recycleView_Hd);
        actionButton = view.findViewById(R.id.floatingaction_Hd);
        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                diaLog_HD();
            }
        });

        EditText ed_timKiem = view.findViewById(R.id.ed_timKiem);
        Button btn_timKiem = view.findViewById(R.id.btn_timKiem);
        btn_timKiem.setOnClickListener(v -> {

            try {
                hoaDonDao = new HoaDonDao(getActivity());
                list = hoaDonDao.getAllByMaHd(Integer.parseInt(ed_timKiem.getText().toString()));
                adapter = new HoaDonAdapter(getActivity());
                adapter.setData(list);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerView.setAdapter(adapter);
            }catch (NumberFormatException e){
                Toast.makeText(getActivity(), "giá trị nhập vào là số  không phải kí tự ", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void diaLog_HD(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog_hd,null);
        EditText gia, ngay;
        spn_tenNv = v.findViewById(R.id.spn_dialog_Hd_Nv);
        spn_tenSp = v.findViewById(R.id.spn_dialog_Hd_Sp);
        spn_tenKh = v.findViewById(R.id.spn_dialog_Hd_Kh);
        spn_ttoan = v.findViewById(R.id.spn_dialog_Hd_ttoan);
        gia = v.findViewById(R.id.dialog_Hd_Gia);
        ngay = v.findViewById(R.id.dialog_Hd_ngay);

        nhanVienDao = new NhanVienDao(getContext());
        spDAO = new SanPhamDao(getActivity());
        KhDao = new KhachHangDao(getActivity());
        List<String> tenNV = new ArrayList<>();
        for(NhanVien nhanVien : nhanVienDao.getAllNhanVien()) {
            tenNV.add(nhanVien.getMaNv()+"."+nhanVien.getHoTen());
        }
        Spn_Adapter(spn_tenNv,tenNV);

        List<String> sanpham = new ArrayList<>();
        for(SanPham listSp: spDAO.getAllSP() ){
            sanpham.add(listSp.getMaSp()+"."+listSp.getTenSp());
        }
        Spn_Adapter(spn_tenSp,sanpham);

        List<String> khach = new ArrayList<>();
        for(KhachHang listKh: KhDao.getAllKhachHang() ){
            khach.add(listKh.getMaKh()+"."+listKh.getTenKh());
        }
        Spn_Adapter(spn_tenKh,khach);

        spn_ttoan = v.findViewById(R.id.spn_dialog_Hd_ttoan);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                v.getContext(),
                R.array.hinhthuc,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_ttoan.setAdapter(adapter);

        builder.setView(v);
        AlertDialog alertDialog = builder.create();

        ngay.setText("Ngày mua: "+new SimpleDateFormat("dd/MM/yyyy").format(new Date()));

        spn_tenSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SanPham sp1 = spDAO.getID(split(spn_tenSp));
                gia.setText(String.valueOf(sp1.getGia()));
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        v.findViewById(R.id.btnthemhd).setOnClickListener(v1 -> {
            hoaDonDao = new HoaDonDao(getActivity());
            HoaDon hoaDon = new HoaDon();
            hoaDon.setMaNv(split(spn_tenNv));
            hoaDon.setMaSp(split(spn_tenSp));
            hoaDon.setMaKh(split(spn_tenKh));
            hoaDon.setTien(Integer.parseInt(gia.getText().toString()));
            hoaDon.setThanhToan(spn_ttoan.getSelectedItemPosition());
            hoaDon.setNgay(new Date());
            int kq = hoaDonDao.Insert(hoaDon);
            if (kq == -1) {
                Toast.makeText(getActivity(), "Thêm thất bại", Toast.LENGTH_SHORT).show();
            }
            if (kq == 1) {
                Toast.makeText(getActivity(), "Thêm thành công", Toast.LENGTH_SHORT).show();
            }
            onResume();
            alertDialog.cancel();
        });
        v.findViewById(R.id.btnhuyhd).setOnClickListener(v1 -> {
            alertDialog.cancel();
        });
        alertDialog.show();
    }
    public void Spn_Adapter(Spinner spn, List<String> list){
        if (list !=null){
            ArrayAdapter<String> adapterSp = new ArrayAdapter<>(getActivity(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, list);

            spn.setAdapter(adapterSp);

        }else {
            Toast.makeText(getActivity(), "Hien tai dang ko co du lieu,Vui long them du lieu de xuat hoa don", Toast.LENGTH_SHORT).show();}
    }
//    public int split(Spinner spn) {
//        if (spn.getSelectedItem() != null) {
//            String chuoi = (String) spn.getSelectedItem();
//            String[] chuoi2 = chuoi.split("\\.");
//            if (chuoi2.length > 0 && chuoi2[0].matches("\\d+")) {
//                return Integer.parseInt(chuoi2[0]);
//            } else {
//                // Xử lý trường hợp không phải số ở đây, ví dụ thông báo hoặc xử lý khác
//                return -1;
//            }
//        } else {
//            Toast.makeText(getActivity(), "Thành viên hoặc sản phẩm hiện đang không có, bạn cần thêm dữ liệu.", Toast.LENGTH_SHORT).show();
//            return -1;
//        }
//    }
    public int split(Spinner spn){
        if (spn.getSelectedItem() != null) {
            String chuoi = (String) spn.getSelectedItem();
            String[] chuoi2 = chuoi.split("\\.");
            return Integer.parseInt(chuoi2[0]);
        } else {

            Toast.makeText(getActivity(), "Thành viên hoặc san pham hiện đang ko có ,bạn cần thêm dữ liệu   ,", Toast.LENGTH_SHORT).show();
            return -1;
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        hoaDonDao = new HoaDonDao(getActivity());
        list.clear();
        list = hoaDonDao.getAllHoaDon();
        adapter = new HoaDonAdapter(getActivity());
        adapter.setData(list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
    }
}