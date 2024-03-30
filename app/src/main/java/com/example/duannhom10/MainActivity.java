package com.example.duannhom10;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.duannhom10.Fragment.DanhMucFragment;
import com.example.duannhom10.Fragment.DoanhThu.DoanhThuFragment;
import com.example.duannhom10.Fragment.DoimkFragment;
import com.example.duannhom10.Fragment.HoaDonFragment;
import com.example.duannhom10.Fragment.KhachHangFragment;
import com.example.duannhom10.Fragment.NhanVienFragment;
import com.example.duannhom10.Fragment.SanPhamFragment;
import com.example.duannhom10.Fragment.TopFragment;
import com.example.duannhom10.Model.NhanVien;
import com.example.duannhom10.SQL.NhanVienDao;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private FrameLayout frameLayout;
    private TextView tv_header;
    private NhanVienDao nhanVienDao;
    private DoimkFragment doiMkFragment = new DoimkFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawerLayout);
        frameLayout = findViewById(R.id.frameLayout);
        toolbar = findViewById(R.id.toolBar);
        navigationView = findViewById(R.id.navigationView);

        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, 0, 0);
        toggle.syncState();

        Intent intent = getIntent();
        String maNv = intent.getStringExtra("TK");
        Bundle bundle = new Bundle();
        bundle.putString("TK", maNv);
        doiMkFragment.setArguments(bundle);

        nhanVienDao = new NhanVienDao(this);
        NhanVien nhanVien = nhanVienDao.getId(Integer.parseInt(maNv));

        MenuItem item = navigationView.getMenu().findItem(R.id.ql_nhanvien);
        if (nhanVien.getHoTen().equals("admin")){
            item.setEnabled(true);
        }else {
            item.setEnabled(false);
        }

        View header = navigationView.getHeaderView(0);
        tv_header = header.findViewById(R.id.tv_header);

        replaceFragment(new SanPhamFragment());
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(navigationView)) {
            drawerLayout.closeDrawer(navigationView);
        } else {
            super.onBackPressed();
        }
    }
    public void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameLayout, fragment);
        transaction.commit();
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.ql_sanpham) {
            toolbar.setTitle("Sản phẩm");
            replaceFragment(new SanPhamFragment());
        } else if (id == R.id.ql_danhMuc) {
            toolbar.setTitle("Danh mục");
            replaceFragment(new DanhMucFragment());
        } else if (id == R.id.ql_hoadon) {
            toolbar.setTitle("Hóa đơn");
            replaceFragment(new HoaDonFragment());
        } else if (id == R.id.ql_nhanvien) {
            toolbar.setTitle("Nhân viên");
            replaceFragment(new NhanVienFragment());
        }else if (id == R.id.ql_khachhang) {
            toolbar.setTitle("Khách hàng");
            replaceFragment(new KhachHangFragment());
        } else if (id == R.id.tk_top10) {
            toolbar.setTitle("Top 10");
            replaceFragment(new TopFragment());
        } else if (id == R.id.tk_doanhThu) {
            toolbar.setTitle("Doanh thu");
            replaceFragment(new DoanhThuFragment());
        } else if (id == R.id.ngd_doiMK) {
            toolbar.setTitle("Đổi mật khẩu");
            replaceFragment(doiMkFragment);
        } else if (id == R.id.ngd_dangXuat) {
            finish();
        }
        drawerLayout.closeDrawer(navigationView);
        return true;
    }
}