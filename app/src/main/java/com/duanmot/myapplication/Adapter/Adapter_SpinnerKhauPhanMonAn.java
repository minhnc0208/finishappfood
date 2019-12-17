package com.duanmot.myapplication.Adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.duanmot.myapplication.R;


import java.util.ArrayList;

public class Adapter_SpinnerKhauPhanMonAn extends BaseAdapter {

    Context context;
    ArrayList<String> dsKhauPhan;

    public Adapter_SpinnerKhauPhanMonAn(Context context, ArrayList<String> dsKhauPhan) {
        this.context = context;

        this.dsKhauPhan = dsKhauPhan;
    }

    @Override
    public int getCount() {
        return dsKhauPhan.size();
    }

    @Override
    public Object getItem(int i) {
        return dsKhauPhan.get(i);
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
            view = inf.inflate(R.layout.item_spinner_khauphan_monan, null);

            holder.tvItemKhauPhan = view.findViewById(R.id.tvItemKhauPhan);


            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        holder.tvItemKhauPhan.setText(dsKhauPhan.get(i));

        return view;
    }

    class ViewHolder {
        public TextView tvItemKhauPhan;

    }
}
