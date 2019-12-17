package com.duanmot.myapplication.Adapter;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.duanmot.myapplication.NguyenLieu_HuongDanNauAnFragment;
import com.duanmot.myapplication.PhanLoaiMonAnFragment;
import com.duanmot.myapplication.ThongTinMonAnFragment;

public class AdapterFragment_TaoMonAn extends FragmentStatePagerAdapter {
    int count;

    public AdapterFragment_TaoMonAn(FragmentManager fm, int count) {
        super(fm);
        this.count = count;
    }


    @Override
    public Fragment getItem(int position) {
        Fragment fragment;

        switch (position) {
            case 0:
                fragment = new ThongTinMonAnFragment();

                break;
            case 1:
                fragment = new NguyenLieu_HuongDanNauAnFragment();

                break;
            case 2:
                fragment = new PhanLoaiMonAnFragment();

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
