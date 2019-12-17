package com.duanmot.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.duanmot.myapplication.Adapter.AdapterFragment_SuaMonAn;

import com.google.android.material.tabs.TabLayout;

public class SuaMonAn extends AppCompatActivity {
    public static ViewPager viewpagerSuaTMA;
    TabLayout tablayoutSuaTMA;
    ImageView imgSuaBackTCT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sua_mon_an);

        viewpagerSuaTMA = findViewById(R.id.viewpagerSuaTMA);
        tablayoutSuaTMA = findViewById(R.id.tablayoutSuaTMA);
        imgSuaBackTCT =findViewById(R.id.imgSuaBackTCT);

        imgSuaBackTCT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent intent = new Intent(SuaMonAn.this, DanhSachMonAnNguoiDung.class);
                startActivity(intent);
            }
        });

        AdapterFragment_SuaMonAn adapterFragment_suaMonAn = new AdapterFragment_SuaMonAn(getSupportFragmentManager(),
                tablayoutSuaTMA.getTabCount());
        viewpagerSuaTMA.setAdapter(adapterFragment_suaMonAn);
        viewpagerSuaTMA.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tablayoutSuaTMA));

        tablayoutSuaTMA.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewpagerSuaTMA.setCurrentItem(tab.getPosition());
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


//                Intent intent = new Intent(SuaMonAn.this, DanhSachMonAnNguoiDung.class);
//                startActivity(intent);
//        viewPager.beginFakeDrag();// tắt tính năng vuốt. Bật tính năng vuốt là:  viewPager.endFakeDrag();