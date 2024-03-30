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
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

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
        holder.ngay.setText(new SimpleDateFormat("dd/MM/yyyy").format(HD.getNgay()));
        holder.del.setOnClickListener(v -> {
            dialogDelete(HD);
        });
        holder.itemView.setOnClickListener(v -> {
            dialogUpdate(HD);
        });
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
        private ImageView del ;
        private Spinner spinner;
        public HoaDonViewHolder(@NonNull View itemView) {
            super(itemView);
            ma = itemView.findViewById(R.id.tv_Dh_mahd);
            Nv = itemView.findViewById(R.id.tv_Hd_maNv);
            sP = itemView.findViewById(R.id.tv_Hd_Sanpham);
            Kh = itemView.findViewById(R.id.tv_Hd_makhach);
            tien = itemView.findViewById(R.id.tv_Hd_tien);
            ngay = itemView.findViewById(R.id.tv_Hd_ngay);

            spinner = itemView.findViewById(R.id.spn_dialog_Hd_ttoan);
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                    itemView.getContext(),
                    R.array.hinhthuc,
                    android.R.layout.simple_spinner_item
            );
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);
        }
    }
    private void dialogDelete(HoaDon HD) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Thông báo");
        builder.setMessage("Bạn có muốn xóa không");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                hoaDonDao.delete(HD);
                arrayList = hoaDonDao.getAllHoaDon();
                setData(arrayList);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.show();
    }

    @SuppressLint("MissingInflatedId")
    public void dialogUpdate(HoaDon HD) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog_hd, null);
        TextView NgayMua, tongtien,maHD, soLg;
        maHD = v.findViewById(R.id.dialog_Hd_maDh);
        spn_tenNV = v.findViewById(R.id.spn_dialog_Hd_Nv);
        spn_tenSP = v.findViewById(R.id.spn_dialog_Hd_Sp);
        spn_tenKh = v.findViewById(R.id.spn_dialog_Hd_Kh);
        NgayMua = v.findViewById(R.id.dialog_Hd_ngay);
        tongtien = v.findViewById(R.id.dialog_Hd_Gia);
        nhanVienDao = new NhanVienDao(context);
        sanPhamDao = new SanPhamDao(context);
        khachHangDao = new KhachHangDao(context);

        maHD.setText(String.valueOf(HD.getMaHd()));

        List<String> tenNv = new ArrayList<>();
        for (NhanVien list : nhanVienDao.getAllNhanVien()) {
            tenNv.add(list.getMaNv() + "." + list.getHoTen());
        }
        Spn_Adapter(spn_tenNV, tenNv);

        for (int i = 0; i < tenNv.size(); i++) {
            String chuoi = tenNv.get(i);
            String[] chuoi2 = chuoi.split("\\.");
            if (HD.getMaNv() == Integer.parseInt(chuoi2[0])) {
                spn_tenNV.setSelection(i);
            }
        }

        List<String> sanpham = new ArrayList<>();
        for (SanPham listSP : sanPhamDao.getAllSP()) {
            sanpham.add(listSP.getMaSp() + "." + listSP.getTenSp());
        }
        Spn_Adapter(spn_tenSP, sanpham);

        for (int i = 0; i < sanpham.size(); i++) {
            String chuoi = sanpham.get(i);
            String[] chuoi2 = chuoi.split("\\.");
            if (HD.getMaSp() == Integer.parseInt(chuoi2[0])) {
                spn_tenSP.setSelection(i);
            }
        }

        List<String>  kh = new ArrayList<>();
        for (KhachHang listKh : khachHangDao.getAllKhachHang()) {
            kh.add(listKh.getMaKh() + "." + listKh.getTenKh());
        }
        Spn_Adapter(spn_tenKh, kh);

        for (int i = 0; i < kh.size(); i++) {
            String chuoi = kh.get(i);
            String[] chuoi2 = chuoi.split("\\.");
            if (HD.getMaKh() == Integer.parseInt(chuoi2[0])) {
                spn_tenKh.setSelection(i);
            }
        }

        NgayMua.setText("Ngày: " + new SimpleDateFormat("dd/MM/yyyy").format(HD.getNgay()));
        spn_tenSP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SanPham sp1 = sanPhamDao.getID(split(spn_tenSP));
                tongtien.setText(String.valueOf(sp1.getGia()));
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        builder.setView(v);
        AlertDialog alertDialog = builder.create();
        v.findViewById(R.id.btnthemhd).setOnClickListener(v1 -> {
            hoaDonDao = new HoaDonDao(context);
            HD.setMaNv(split(spn_tenNV));
            HD.setMaSp(split(spn_tenSP));
            HD.setMaKh(split(spn_tenKh));
            HD.setTien(Integer.parseInt(tongtien.getText().toString()));
            HD.setNgay(new Date());
            Log.e("Log.e", HD.toString());
            int kq = hoaDonDao.update(HD);

            if (kq == -1) {
                Toast.makeText(context, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
            }
            if (kq == 1) {
                Toast.makeText(context, "Cập nhât thành công", Toast.LENGTH_SHORT).show();
            }
            arrayList = hoaDonDao.getAllHoaDon();
            setData(arrayList);
            alertDialog.cancel();
        });
        v.findViewById(R.id.btnhuyhd).setOnClickListener(v1 -> {
            alertDialog.cancel();
        });
        alertDialog.show();
    }
    public void Spn_Adapter(Spinner spn, List<String> list) {

        if (list !=null){
            ArrayAdapter<String> adapterSach = new ArrayAdapter<>(context, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, list);
            spn.setAdapter(adapterSach);

        }else {
            Toast.makeText(context, "Hiện không có dữ liệu, vui lòng thêm dữ liệu để xuất hóa đơn", Toast.LENGTH_SHORT).show();}
    }

    public int split(Spinner spn) {

        if (spn.getSelectedItem() != null) {
            String chuoi = (String) spn.getSelectedItem();
            String[] chuoi2 = chuoi.split("\\.");
            return Integer.parseInt(chuoi2[0]);
        } else {
            Toast.makeText(context, "Nhân viên hoặc sản phẩm hiện đang ko có ,bạn cần thêm dữ liệu   ,", Toast.LENGTH_SHORT).show();
            return -1;
        }
    }
}
