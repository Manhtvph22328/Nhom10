package com.example.duannhom10.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duannhom10.Model.DanhMuc;
import com.example.duannhom10.Model.SanPham;
import com.example.duannhom10.R;
import com.example.duannhom10.SQL.DanhMucDao;
import com.example.duannhom10.SQL.SanPhamDao;

import java.util.ArrayList;
import java.util.List;

public class SanPhamAdapter extends RecyclerView.Adapter<SanPhamAdapter.SanPViewHodelder>implements Filterable {
    private Context context;
    private ArrayList<SanPham> listSP;
    private ArrayList<SanPham> arrayList;
    private DanhMucDao danhMucDao;
    private SanPhamDao sanPhamDao;
    private ImageView img ;

    public SanPhamAdapter(Context context) {
        this.context = context;
    }

    public void setData(ArrayList<SanPham> arrayList){
        this.arrayList = arrayList;
        this.listSP = arrayList;
        sanPhamDao = new SanPhamDao(context);
        danhMucDao = new DanhMucDao(context);
        notifyDataSetChanged();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String tenSp = constraint.toString();
                ArrayList<SanPham> list = new ArrayList<>();
                if(tenSp.isEmpty()){
                    arrayList = listSP;
                }
                for(SanPham list2: listSP){
                    if(list2.getTenSp().toLowerCase().contains(tenSp.toLowerCase())){
                        list.add(list2);
                    }
                }
                arrayList = list;
                FilterResults filterResults = new FilterResults();
                filterResults.values = arrayList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                arrayList = (ArrayList<SanPham>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    @NonNull
    @Override
    public SanPViewHodelder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_sp,parent,false);
        return new SanPViewHodelder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SanPhamAdapter.SanPViewHodelder holder, int position) {
        SanPham sanPham = arrayList.get(position);
        if(sanPham == null){
            return;
        }
        holder.ma.setText(""+sanPham.getMaSp());
        holder.ten.setText(""+sanPham.getTenSp());
        holder.gia.setText(""+ sanPham.getGia());
        holder.soLg.setText(""+ sanPham.getSoLuong());
        holder.moTa.setText(""+ sanPham.getMoTa());
        DanhMuc danhMuc = danhMucDao.getID(sanPham.getMaDm());
        holder.mota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogMoTa(sanPham);
            }
        });
        holder.dmuc.setText("" + (danhMuc != null ? danhMuc.getTenDm() : "Danh muc không tồn tại"));
        holder.del.setOnClickListener(v -> {
            dialogDelete(sanPham);
        });

        holder.itemView.setOnClickListener(v -> {
            if (sanPham == null) {
                Toast.makeText(context, "Cần phải thêm dữ liệu cho danh muc", Toast.LENGTH_SHORT).show();
            } else {
                dialogUpdate(sanPham);
            }

        });
    }

    @Override
    public int getItemCount() {
        if (arrayList != null){
            return arrayList.size();
        }
        return 0;
    }

    public  class  SanPViewHodelder extends RecyclerView.ViewHolder {
        private TextView ma ,ten, gia , dmuc, soLg, moTa;
        private ImageButton del, mota;
        private ImageView img;
        public SanPViewHodelder(@NonNull View itemView) {
            super(itemView);
            img =itemView.findViewById(R.id.item_Sp_img);
            ma = itemView.findViewById(R.id.tv_Sp_ma);
            ten = itemView.findViewById(R.id.tv_Sp_ten);
            gia = itemView.findViewById(R.id.tv_Sp_gia);
            dmuc = itemView.findViewById(R.id.tv_Sp_dm);
            soLg = itemView.findViewById(R.id.tv_Sp_solg);
            moTa = itemView.findViewById(R.id.tv_Sp_mota);
            mota = itemView.findViewById(R.id.item_Sp_ChiTiet);
            del = itemView.findViewById(R.id.item_Sp_del);
        }
    }

    private void dialogMoTa(SanPham sp) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Chi tiết sản phẩm");
        builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SanPham sanPham;
//                sanPham = arrayList.get();
//                if(sanPham == null){
//                    return;
//                }
//                holder.ma.setText(""+sanPham.getMaSp());
//                holder.ten.setText(""+sanPham.getTenSp());
//                holder.gia.setText(""+ sanPham.getGia());
//                holder.soLg.setText(""+ sanPham.getSoLuong());
//                holder.moTa.setText(""+ sanPham.getMoTa());
            }
        });
        builder.show();
    }
    private void dialogDelete(SanPham sp) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Thông báo");
        builder.setMessage("Bạn có muốn xóa không");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                sanPhamDao.delete(sp);
                arrayList = sanPhamDao.getAllSP();
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
    public void dialogUpdate(SanPham sanPham) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog_sp, null);
        EditText ed_maSP,ed_tenSP,ed_giaSP, ed_moTa, ed_soLuong;
        Spinner spn_dmuc;
        ImageButton btn_luu,btn_huy;
        ImageView imageView ;

        imageView =v.findViewById(R.id.dialog_img_Sp);
        ed_maSP = v.findViewById(R.id.dialog_Sp_maSp);
        ed_tenSP = v.findViewById(R.id.dialog_Sp_maSp);
        ed_giaSP = v.findViewById(R.id.dialog_Sp_maSp);
        ed_moTa = v.findViewById(R.id.dialog_Sp_mota);
        ed_soLuong = v.findViewById(R.id.dialog_Sp_soLuong);
        spn_dmuc = v.findViewById(R.id.spn_dialog_Sp_Dm);
        btn_luu = v.findViewById(R.id.btnthemsp);
        btn_huy = v.findViewById(R.id.btnhuysp);
        danhMucDao = new DanhMucDao(context);
        List<String> dmucc =  new ArrayList<>();


        for(DanhMuc listDmuc: danhMucDao.getAllDanhMuc()){
            dmucc.add(listDmuc.getMaDm()+"."+listDmuc.getTenDm());
        }
        ArrayAdapter<String> adapterDanhm = new ArrayAdapter<>(context, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,dmucc);
        spn_dmuc.setAdapter(adapterDanhm);


        ed_maSP.setText(String.valueOf(sanPham.getMaSp()));
        ed_tenSP.setText(sanPham.getTenSp());
        ed_moTa.setText(sanPham.getMoTa());
        ed_soLuong.setText(sanPham.getSoLuong());
        ed_giaSP.setText(String.valueOf(sanPham.getGia()));
        for(int i =0;i<dmucc.size();i++){
            String chuoi = dmucc.get(i);
            String[] chuoi2 = chuoi.split("\\.");
            if(sanPham.getMaDm()==Integer.parseInt(chuoi2[0])){
                spn_dmuc.setSelection(i);
            }
        }
        builder.setView(v);
        AlertDialog alertDialog = builder.create();
        btn_luu.setOnClickListener(v1 -> {
            String DanhMuc = (String)spn_dmuc.getSelectedItem();
            String[] maDm= DanhMuc.split("\\.");

            sanPham.setTenSp(ed_tenSP.getText().toString());
            sanPham.setGia(Integer.parseInt(ed_giaSP.getText().toString()));
            sanPham.setMaDm(Integer.parseInt(maDm[0]));
            sanPham.setMoTa(ed_moTa.getText().toString());
            sanPham.setSoLuong(Integer.parseInt(ed_soLuong.getText().toString()));
            int kq = sanPhamDao.update(sanPham);
            if (kq == -1) {
                Toast.makeText(context, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
            }
            if (kq == 1) {
                arrayList = sanPhamDao.getAllSP();
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
