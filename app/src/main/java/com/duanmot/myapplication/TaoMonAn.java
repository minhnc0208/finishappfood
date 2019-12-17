package com.duanmot.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.duanmot.myapplication.Adapter.AdapterFragment_TaoMonAn;
import com.duanmot.myapplication.model.PhanLoaiMonAn;
import com.google.android.material.tabs.TabLayout;

public class TaoMonAn extends AppCompatActivity {
    public static ViewPager viewPager;
    TabLayout tabLayout;
    ImageView imgBackTCT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tao_mon_an);

        viewPager = findViewById(R.id.viewpagerTMA);
        tabLayout = findViewById(R.id.tablayoutTMA);
        imgBackTCT = findViewById(R.id.imgBackTCT);

        imgBackTCT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        AdapterFragment_TaoMonAn adapterFragment_taoMonAn = new AdapterFragment_TaoMonAn(getSupportFragmentManager(),
                tabLayout.getTabCount());
        viewPager.setAdapter(adapterFragment_taoMonAn);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}


    //Intent intent = new Intent(TaoMonAn.this, DanhSachMonAnNguoiDung.class);
//                startActivity(intent);
//        viewPager.beginFakeDrag();// tắt tính năng vuốt. Bật tính năng vuốt là:  viewPager.endFakeDrag();

//        tabLayout.addTab(tabLayout.newTab().setText("Thông tin món ăn"));
//        tabLayout.addTab(tabLayout.newTab().setText("Nguyên liệu & Hướng dẫn nấu ăn"));
//        tabLayout.addTab(tabLayout.newTab().setText("Phân loại món ăn"));