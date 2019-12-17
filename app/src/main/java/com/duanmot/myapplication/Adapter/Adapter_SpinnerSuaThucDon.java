package com.duanmot.myapplication.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.duanmot.myapplication.R;


public class Adapter_SpinnerSuaThucDon extends BaseAdapter {

    Context context;

    int imgListThucDon[];
    String[] countryNames;

    public Adapter_SpinnerSuaThucDon(Context context, int[] flags, String[] countryNames) {
        this.context = context;
        this.imgListThucDon = flags;
        this.countryNames = countryNames;
    }

    @Override
    public int getCount() {
        return imgListThucDon.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolder holder;

        if (view == null) {
            holder = new ViewHolder();
            LayoutInflater inf = ((Activity) context).getLayoutInflater();
            view = inf.inflate(R.layout.item_spinner_thucdon, null);

            holder.tvItemThucDon = view.findViewById(R.id.tvItemThucDon);
            holder.imgAnhItemThucDon = view.findViewById(R.id.imgAnhItemThucDon);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        holder.imgAnhItemThucDon.setImageResource(imgListThucDon[i]);
        holder.tvItemThucDon.setText(countryNames[i]);

        return view;
    }

    class ViewHolder {
        public TextView tvItemThucDon;
        public ImageView imgAnhItemThucDon;
    }
}
