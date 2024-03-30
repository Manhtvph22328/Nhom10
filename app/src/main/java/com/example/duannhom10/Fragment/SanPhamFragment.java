package com.example.duannhom10.Fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.example.duannhom10.Adapter.SanPhamAdapter;
import com.example.duannhom10.Model.DanhMuc;
import com.example.duannhom10.Model.SanPham;
import com.example.duannhom10.R;
import com.example.duannhom10.SQL.DanhMucDao;
import com.example.duannhom10.SQL.SanPhamDao;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class SanPhamFragment extends Fragment {

    private FloatingActionButton floating;
    private SanPhamDao spDao;
    private RecyclerView recyclerView;
    private SanPhamAdapter adapter;
    private DanhMucDao danhMucDao;
    private ArrayList<SanPham> list = new ArrayList<>();
    private static final int SELECT_IMAGE = 100;
    private ImageView img ;
    private ViewFlipper viewFlipper;

    EditText ed_timKiem;
    Button bnt_timKiem;

    public SanPhamFragment() {
        // Required empty public constructor
    }

    public static SanPhamFragment newInstance(String param1, String param2) {
        SanPhamFragment fragment = new SanPhamFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        spDao = new SanPhamDao(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_san_pham, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recycleView_SanPham);
        floating = view.findViewById(R.id.floatingaction_SanPham);
        viewFlipper = view.findViewById(R.id.viewFlipper);
        viewFlipper();
        floating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_SanPham();
            }
        });
        ed_timKiem = view.findViewById(R.id.ed_timKiemSp);
        bnt_timKiem = view.findViewById(R.id.btn_timKiemSp);

        bnt_timKiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ed_timKiem.getText().toString().isEmpty()){
                    Toast.makeText(getActivity(), "bạn phải nhập thông tin tên sản phẩm để tìm kiếm thông tin ", Toast.LENGTH_SHORT).show();
                }
                adapter.getFilter().filter(ed_timKiem.getText().toString().trim());
            }
        });
    }
    public void dialog_SanPham() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog_sp, null);
        EditText ed_tenSp,ed_giaSp, ed_moTa, ed_soLg;
        Spinner spn_DmucSp;
        ImageButton btn_luu,btn_huy;

        img = v.findViewById(R.id.dialog_img_Sp);
        ed_tenSp = v.findViewById(R.id.dialog_Sp_tenSp);
        ed_giaSp = v.findViewById(R.id.dialog_Sp_Gia);
        ed_moTa = v.findViewById(R.id.dialog_Sp_mota);
        ed_soLg = v.findViewById(R.id.dialog_Sp_soLuong);
        spn_DmucSp = v.findViewById(R.id.spn_dialog_Sp_Dm);
        btn_luu = v.findViewById(R.id.btnthemsp);
        btn_huy = v.findViewById(R.id.btnhuysp);

        danhMucDao = new DanhMucDao(getActivity());
        List<String> dmucSp =  new ArrayList<>();
        for(DanhMuc listDmucSp: danhMucDao.getAllDanhMuc()){
            dmucSp.add(listDmucSp.getMaDm()+"."+listDmucSp.getTenDm());
        }
        ArrayAdapter<String> adapterDmucSp = new ArrayAdapter<>(getActivity(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,dmucSp);
        spn_DmucSp.setAdapter(adapterDmucSp);
        builder.setView(v);
        AlertDialog alertDialog = builder.create();
        img.setOnClickListener(v2->{
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent,SELECT_IMAGE);
        });

        btn_luu.setOnClickListener(v1 -> {
            if(ed_tenSp.length() ==0 || ed_giaSp.length()==0) {
                Toast.makeText(getActivity(), "Không để trống", Toast.LENGTH_SHORT).show();
            }else {
                SanPham sp = new SanPham();
                String DmSp = (String) spn_DmucSp.getSelectedItem();
                if (DmSp != null) {

                    String[] maDm = DmSp.split("\\.");
                    sp.setTenSp(ed_tenSp.getText().toString());
                    sp.setMoTa(ed_moTa.getText().toString());
                    sp.setGia(Integer.parseInt(ed_giaSp.getText().toString()));
                    sp.setSoLuong(Integer.parseInt(ed_soLg.getText().toString()));
                    sp.setMaDm(Integer.parseInt(maDm[0]));

                    int kq = spDao.Insert(sp);
                    if (kq == -1) {
                        Toast.makeText(getActivity(), "Thêm thất bại", Toast.LENGTH_SHORT).show();
                    }
                    if (kq == 1) {
                        Toast.makeText(getActivity(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                    }
                    onResume();
                    alertDialog.cancel();
                }   else {
                    Toast.makeText(getContext(), "Vui lòng thêm danh mục trước rồi mới thêm sản phẩm ", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btn_huy.setOnClickListener(v1 -> {
            alertDialog.cancel();
        });
        alertDialog.show();
    }
    private void viewFlipper() {
        List<String> banner = new ArrayList<>();
        banner.add("https://fecredit.com.vn/wp-content/uploads/2021/06/OPPO-Banner_1176x364-1.png");
        banner.add("https://fptshop.com.vn/uploads/originals/2018/3/17/636569258867110213_Banner-OPPOF5-H1.jpg");
        banner.add("https://daygiare.com/public/storage/photo/2019/11/08/adayroi-danh-gia-khuyen-mai-oppo-chinh-hang-uu-dai-len-toi-20-0.png");
        for (int i = 0; i < banner.size(); i++) {
            ImageView imageView = new ImageView(getContext());
            Glide.with(getContext()).load(banner.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);
        }
        viewFlipper.setFlipInterval(3000);
        viewFlipper.setAutoStart(true);
        Animation slide_in = AnimationUtils.loadAnimation(getContext(), R.anim.slide_in);
        Animation slide_out = AnimationUtils.loadAnimation(getContext(), R.anim.slide_out);
        viewFlipper.setInAnimation(slide_in);
        viewFlipper.setOutAnimation(slide_out);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SELECT_IMAGE && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();
            img.setImageURI(uri);
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        SanPhamDao sanPhamDao = new SanPhamDao(getActivity());
        list.clear();
        list = sanPhamDao.getAllSP();
        adapter = new SanPhamAdapter(getActivity());
        adapter.setData(list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }
}