package com.duanmot.myapplication.Adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.duanmot.myapplication.NguyenLieu_HuongDanNauAnFragment;
import com.duanmot.myapplication.PhanLoaiMonAnFragment;
import com.duanmot.myapplication.SuaNguyenLieu_HuongDanNauAnFragment;
import com.duanmot.myapplication.SuaPhanLoaiMonAnFragment;
import com.duanmot.myapplication.SuaThongTinMonAnFragment;
import com.duanmot.myapplication.ThongTinMonAnFragment;

public class AdapterFragment_SuaMonAn extends FragmentStatePagerAdapter {
    int count;

    public AdapterFragment_SuaMonAn(FragmentManager fm, int count) {
        super(fm);
        this.count = count;
    }


    @Override
    public Fragment getItem(int position) {
        Fragment fragment;

        switch (position) {
            case 0:
                fragment = new SuaThongTinMonAnFragment();

                break;
            case 1:
                fragment = new SuaNguyenLieu_HuongDanNauAnFragment();

                break;
            case 2:
                fragment = new SuaPhanLoaiMonAnFragment();

                break;

            default:
                return null;

        }
        return fragment;
    }

    @Override
    public int getCount() {
        return count;
    }
}
