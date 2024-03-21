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

import com.example.duannhom10.Model.DanhMuc;
import com.example.duannhom10.R;
import com.example.duannhom10.SQL.DanhMucDao;

import java.util.ArrayList;

public class DanhMucAdapter extends RecyclerView.Adapter<DanhMucAdapter.DanhMucViewHolder>{
    private Context context ;
    private ArrayList<DanhMuc> arrayList ;
    private DanhMucDao danhMucDao ;

    public DanhMucAdapter(Context context) {
        this.context = context;
    }
    public  void  setData (ArrayList<DanhMuc>arrayList ){
        this.arrayList = arrayList ;
        danhMucDao = new DanhMucDao(context);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DanhMucViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dm,parent,false);
        return new DanhMucViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DanhMucViewHolder holder, int position) {
        DanhMuc dm = arrayList.get(position);
        if (dm ==null){
            return;
        }
        holder.ma.setText(" " + dm.getMaDm());
        holder.ten.setText(" " + dm.getTenDm());

        holder.del.setOnClickListener(v -> {
            dialogDelete(dm);
        });
        holder.itemView.setOnClickListener(v -> {
            dialogUpdate(dm);
        });
    }

    @Override
    public int getItemCount() {
        if (arrayList != null){
            return  arrayList.size();
        }
        return 0;
    }


    public  class  DanhMucViewHolder extends RecyclerView.ViewHolder {
        private TextView ma , ten;
        private ImageView img,del ;
        public DanhMucViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.item_Dm_img);
            ma = itemView.findViewById(R.id.tv_Dm_ma);
            ten = itemView.findViewById(R.id.tv_Dm_ten);
            del = itemView.findViewById(R.id.item_dm_del);
        }
    }
    private void dialogDelete(DanhMuc dm) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Thông báo");
        builder.setMessage("Bạn có muốn xóa không");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                danhMucDao.delete(dm);
                arrayList = danhMucDao.getAllDanhMuc();
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
    public void dialogUpdate(DanhMuc dm){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog_dm,null);

        EditText ed_ten = v.findViewById(R.id.dialog_Dm_maDm);
        EditText ed_ma = v.findViewById(R.id.dialog_Dm_tenDm);
        ed_ma.setText(String.valueOf(dm.getMaDm()));
        ed_ten.setText(String.valueOf(dm.getTenDm()));
        builder.setView(v);
        AlertDialog alertDialog = builder.create();
        v.findViewById(R.id.btnthemdm).setOnClickListener(v1 ->{
            danhMucDao = new DanhMucDao(context);
            dm.setTenDm(ed_ten.getText().toString());
            int kq = danhMucDao.update(dm);
            if (kq == -1) {
                Toast.makeText(context, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
            }
            if (kq == 1) {
                Toast.makeText(context, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                arrayList = danhMucDao.getAllDanhMuc();
                setData(arrayList);
            }
            alertDialog.cancel();
        });
        v.findViewById(R.id.btnhuydm).setOnClickListener(v1 -> {
            alertDialog.cancel();
        });
        alertDialog.show();
    }
}
