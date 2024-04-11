package com.example.duannhom10.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duannhom10.Model.HoaDon;
import com.example.duannhom10.Model.KhachHang;
import com.example.duannhom10.Model.NhanVien;
import com.example.duannhom10.Model.SanPham;
import com.example.duannhom10.R;
import com.example.duannhom10.SQL.HoaDonDao;
import com.example.duannhom10.SQL.KhachHangDao;
import com.example.duannhom10.SQL.NhanVienDao;
import com.example.duannhom10.SQL.SanPhamDao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HoaDonAdapter extends RecyclerView.Adapter<HoaDonAdapter.HoaDonViewHolder>{
    private ArrayList<HoaDon> arrayList;
    private Context context;
    private HoaDonDao hoaDonDao;
    private NhanVienDao nhanVienDao;
    private KhachHangDao khachHangDao;
    private SanPhamDao sanPhamDao;
    private Spinner spn_tenNV, spn_tenSP, spn_tenKh;

    public HoaDonAdapter(Context context) {
        this.context = context;
    }

    public void setData(ArrayList<HoaDon> arrayList) {
        this.arrayList = arrayList;
        for (int i = 0; i < arrayList.size(); i++) {
            Log.e("HD",arrayList.get(i).toString());
        }
        hoaDonDao = new HoaDonDao(context);
        nhanVienDao = new NhanVienDao(context);
        sanPhamDao = new SanPhamDao(context);
        khachHangDao = new KhachHangDao(context);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public HoaDonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hd,parent,false);
        return new HoaDonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HoaDonAdapter.HoaDonViewHolder holder, int position) {
        HoaDon HD = arrayList.get(position);
        if (HD == null) {
            return;
        }
        holder.ma.setText(String.valueOf(HD.getMaHd()));

        NhanVien nhanVien = nhanVienDao.getId(HD.getMaNv());
        holder.Nv.setText(nhanVien.getHoTen());
        SanPham sanPham = sanPhamDao.getID(HD.getMaSp());
        holder.sP.setText(sanPham.getTenSp());
        KhachHang khachHang = khachHangDao.getID(HD.getMaKh());
        holder.Kh.setText(khachHang.getTenKh());

        holder.tien.setText(String.valueOf(HD.getTien()));

        if (holder.spinner != null) {
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                    holder.itemView.getContext(),
                    R.array.hinhthuc,
                    android.R.layout.simple_spinner_item
            );
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            holder.spinner.setAdapter(adapter);
        }

        holder.ngay.setText(new SimpleDateFormat("dd/MM/yyyy").format(HD.getNgay()));
    }

    @Override
    public int getItemCount() {
        if (arrayList != null){
            return arrayList.size();
        }
        return 0;
    }

    public class  HoaDonViewHolder extends  RecyclerView.ViewHolder{
        private TextView ma , Nv , sP, Kh, tien , ngay;
        private Spinner spinner;
        public HoaDonViewHolder(@NonNull View itemView) {
            super(itemView);
            ma = itemView.findViewById(R.id.tv_Dh_mahd);
            Nv = itemView.findViewById(R.id.tv_Hd_maNv);
            sP = itemView.findViewById(R.id.tv_Hd_Sanpham);
            Kh = itemView.findViewById(R.id.tv_Hd_makhach);
            tien = itemView.findViewById(R.id.tv_Hd_tien);
            spinner = itemView.findViewById(R.id.spn_dialog_Hd_ttoan);
            ngay = itemView.findViewById(R.id.tv_Hd_ngay);
        }
    }
}
