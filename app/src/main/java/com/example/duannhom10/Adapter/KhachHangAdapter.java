package com.example.duannhom10.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duannhom10.Model.KhachHang;
import com.example.duannhom10.R;
import com.example.duannhom10.SQL.KhachHangDao;

import java.util.ArrayList;

public class KhachHangAdapter extends RecyclerView.Adapter<KhachHangAdapter.KhViewHolder> {

    private ArrayList<KhachHang> arrayList;
    private KhachHangDao khachHangDao ;
    private Context context ;
    public KhachHangAdapter (Context context ){this.context = context ;}
    public  void  setData (ArrayList<KhachHang>arrayList ){
        this.arrayList = arrayList ;
        khachHangDao = new KhachHangDao(context);
    }
    @NonNull
    @Override
    public KhViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_kh ,parent,false);
        return new KhViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull KhachHangAdapter.KhViewHolder holder, int position) {
        KhachHang khachHang = arrayList.get(position);
        if (khachHang == null){
            return;
        }
        holder.ma.setText(" " + khachHang.getMaKh());
        holder.ten.setText(" " + khachHang.getTenKh());
        holder.dienthoai.setText("" + khachHang.getSoDt());
        holder.diaChi.setText("" + khachHang.getDiaChi());

        holder.del.setOnClickListener(v -> {
            dialogDelete(khachHang);
        });
        holder.itemView.setOnClickListener(v -> {
            dialogUpdate(khachHang);
        });
    }

    @Override
    public int getItemCount() {
        if (arrayList != null){
            return arrayList.size();
        }
        return 0;
    }
    public  class  KhViewHolder extends RecyclerView.ViewHolder {
        private TextView ma , ten , dienthoai, diaChi ;
        private ImageView img ,del;
        public KhViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.item_Kh_img);
            ma = itemView.findViewById(R.id.tv_Kh_ma);
            ten = itemView.findViewById(R.id.tv_Kh_ten);
            dienthoai = itemView.findViewById(R.id.tv_Kh_soDt);
            diaChi = itemView.findViewById(R.id.tv_Kh_diachi);
            del = itemView.findViewById(R.id.item_kh_del);
        }
    }
    private void dialogDelete(KhachHang khachHang) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Thông báo");
        builder.setMessage("Bạn có muốn xóa không");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                khachHangDao.delete(khachHang);
                arrayList = khachHangDao.getAllKhachHang();
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
    public void dialogUpdate(KhachHang khachHang){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog_kh,null);

        EditText ed_maTV = v.findViewById(R.id.dialog_Kh_maKh);
        EditText ed_tenTV = v.findViewById(R.id.dialog_Kh_tenKh);
        EditText ed_diachi = v.findViewById(R.id.dialog_Kh_diachi);
        EditText ed_soDt = v.findViewById(R.id.dialog_Kh_sodt);

        ImageButton btn_luu,btn_huy;
        btn_luu = v.findViewById(R.id.btnthemkh);
        btn_huy = v.findViewById(R.id.btnhuykh);
        ed_maTV.setText(String.valueOf(khachHang.getMaKh()));
        ed_diachi.setText(khachHang.getDiaChi());
        ed_tenTV.setText(khachHang.getTenKh());
        ed_soDt.setText(String.valueOf(khachHang.getSoDt()));

        builder.setView(v);
        AlertDialog alertDialog = builder.create();
        btn_luu.setOnClickListener(v1 ->{
            khachHangDao = new KhachHangDao(context);
            khachHang.setTenKh(ed_tenTV.getText().toString());
            khachHang.setDiaChi(ed_diachi.getText().toString());
            khachHang.setSoDt(Integer.parseInt(ed_soDt.getText().toString()));

            int kq = khachHangDao.update(khachHang);
            //
            if (kq == -1) {
                Toast.makeText(context, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
            }
            if (kq == 1) {
                arrayList = khachHangDao.getAllKhachHang();
                setData(arrayList);
                Toast.makeText(context, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
            }
            alertDialog.cancel();
        });
        btn_huy.setOnClickListener(v1 -> {
            alertDialog.cancel();
        });
        alertDialog.show();
    }
}
