package com.example.duannhom10.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duannhom10.Model.NhanVien;
import com.example.duannhom10.R;
import com.example.duannhom10.SQL.NhanVienDao;

import java.text.ParseException;
import java.util.ArrayList;

public class NhanVienAdapter extends RecyclerView.Adapter<NhanVienAdapter.NvienViewHolder> {
    private ArrayList<NhanVien> arrayList;
    private NhanVienDao nhanVienDAO ;
    private Context context ;
    public NhanVienAdapter(Context context ){this.context = context ;}
    public  void  setData (ArrayList<NhanVien>arrayList ){
        this.arrayList = arrayList ;
        nhanVienDAO = new NhanVienDao(context);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NvienViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_nv ,parent,false);
        return new NvienViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NhanVienAdapter.NvienViewHolder holder, int position) {
        NhanVien nhanVien = arrayList.get(position);
        if (nhanVien == null){
            return;
        }
        holder.ma.setText(" " + nhanVien.getMaNv());
        holder.ten.setText(" " + nhanVien.getHoTen());
        holder.namSinh.setText("" + nhanVien.getNamSinh());
        holder.mail.setText("" + nhanVien.getEmail());
        holder.matkhau.setText(""+nhanVien.getMatKhau());

        holder.del.setOnClickListener(v -> {
            dialogDelete(nhanVien);
        });
        holder.itemView.setOnClickListener(v -> {
            dialogUpdate(nhanVien);
        });
    }

    @Override
    public int getItemCount() {
        if (arrayList != null ){
            return  arrayList.size();
        }
        return 0;
    }
    public class  NvienViewHolder extends RecyclerView.ViewHolder {
        private TextView ma , ten , mail, namSinh, matkhau ;
        private ImageView img,del;
        public NvienViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.item_Nv_img);
            ma = itemView.findViewById(R.id.tv_Nv_ma);
            ten = itemView.findViewById(R.id.tv_Nv_ten);
            namSinh = itemView.findViewById(R.id.tv_Nv_namSinh);
            mail = itemView.findViewById(R.id.tv_Nv_mail);
            matkhau = itemView.findViewById(R.id.tv_Nv_mk);
            del = itemView.findViewById(R.id.item_Nv_del);
        }
    }
    private void dialogDelete(NhanVien nhanVien) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Thông báo");
        builder.setMessage("Bạn có muốn xóa không");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                nhanVienDAO.delete(nhanVien);
                    arrayList = nhanVienDAO.getAllNhanVien();
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
    public void dialogUpdate(NhanVien nhanVien){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog_nv,null);

        EditText ed_maNV = v.findViewById(R.id.dialog_Nv_maNv);
        EditText ed_tenNV = v.findViewById(R.id.dialog_Nv_tenNv);
        EditText ed_namSinh = v.findViewById(R.id.dialog_Nv_namSNv);
        EditText ed_mail = v.findViewById(R.id.dialog_Nv_mail);
        EditText ed_matKhau = v.findViewById(R.id.dialog_Nv_mkNv);

        ed_maNV.setText(nhanVien.getMaNv());
        ed_tenNV.setText(nhanVien.getHoTen());
        ed_namSinh.setText(nhanVien.getNamSinh());
        ed_mail.setText(nhanVien.getEmail());
        ed_matKhau.setText(nhanVien.getMatKhau());

        builder.setView(v);
        AlertDialog alertDialog = builder.create();
        v.findViewById(R.id.btnthemnv).setOnClickListener(v1 ->{
            nhanVienDAO = new NhanVienDao(context);
            nhanVien.setMaNv(String.valueOf(ed_maNV.getText().toString()));
            nhanVien.setHoTen(String.valueOf(ed_tenNV.getText().toString()));
            nhanVien.setNamSinh(String.valueOf(ed_namSinh.getText().toString()));
            nhanVien.setEmail(String.valueOf(ed_mail.getText().toString()));
            nhanVien.setMatKhau(String.valueOf(ed_matKhau.getText().toString()));

            int kq = nhanVienDAO.update(nhanVien);
            if (kq == -1) {
                Toast.makeText(context, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
            }
            if (kq == 1) {
                Toast.makeText(context, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                    arrayList = nhanVienDAO.getAllNhanVien();
                setData(arrayList);
            }
            alertDialog.cancel();
        });
        v.findViewById(R.id.btnhuynv).setOnClickListener(v1 -> {
            alertDialog.cancel();
        });
        alertDialog.show();
    }
}
