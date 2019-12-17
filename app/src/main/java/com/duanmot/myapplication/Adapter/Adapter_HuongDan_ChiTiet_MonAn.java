package com.duanmot.myapplication.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.duanmot.myapplication.R;

import java.util.ArrayList;

public class Adapter_HuongDan_ChiTiet_MonAn extends RecyclerView.Adapter<Adapter_HuongDan_ChiTiet_MonAn.MyViewHolder> {

    Context context;
    ArrayList<String> dsBuocHD;
    int soBuoc = 0;
    String[] listSoBuoc;

    public Adapter_HuongDan_ChiTiet_MonAn(Context context, ArrayList<String> dsBuocHD, String[] listSoBuoc) {
        this.context = context;
        this.dsBuocHD = dsBuocHD;
        this.listSoBuoc = listSoBuoc;
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvBuocMACT, tvItemNoiDungBuocHuongDan;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvBuocMACT = itemView.findViewById(R.id.tvBuocMACT);
            tvItemNoiDungBuocHuongDan = itemView.findViewById(R.id.tvItemNoiDungBuocHuongDan);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyclerview_huongdan_chitiet_monan_home, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        soBuoc = soBuoc + 1;
        holder.tvBuocMACT.setText(listSoBuoc[position]);
        holder.tvItemNoiDungBuocHuongDan.setText(dsBuocHD.get(position).trim());
    }
    @Override
    public int getItemCount() {
        return dsBuocHD.size();
    }
}
