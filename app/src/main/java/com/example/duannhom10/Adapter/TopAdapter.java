package com.example.duannhom10.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duannhom10.Model.SanPham;
import com.example.duannhom10.R;

import java.util.ArrayList;

public class TopAdapter extends RecyclerView.Adapter<TopAdapter.topViewHolder> {

    private Context context;
    private ArrayList<SanPham> arrayList;

    public TopAdapter(Context context) {
        this.context = context;
    }
    public void setData(ArrayList<SanPham> arrayList){
        this.arrayList = arrayList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public topViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_top, parent,false);
        return new topViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TopAdapter.topViewHolder holder, int position) {
        SanPham sanPham = arrayList.get(position);
        if (sanPham == null){
            return;
        }
        holder.tvma.setText("Mã Sản phẩm: "+sanPham.getMaSp());
        holder.tvten.setText("Tên Sản phẩm: "+sanPham.getTenSp());
        holder.tvgia.setText("Giá: "+String.valueOf(sanPham.getGia()));
        holder.tvSl.setText("Số luong: "+sanPham.getSoLuong());
    }

    @Override
    public int getItemCount() {
        if (arrayList != null) {
            return arrayList.size();
        }
        return 0;
    }
    public class topViewHolder extends RecyclerView.ViewHolder{

        TextView tvma, tvten, tvgia, tvSl;
        public topViewHolder(@NonNull View itemView){
            super(itemView);
            tvma = itemView.findViewById(R.id.tv_Top_ma);
            tvten = itemView.findViewById(R.id.tv_Top_Sp);
            tvgia = itemView.findViewById(R.id.tv_Top_gia);
            tvSl = itemView.findViewById(R.id.tv_Top_slg);
        }
    }
}
