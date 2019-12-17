package com.duanmot.myapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.duanmot.myapplication.R;

import java.util.ArrayList;

public class Adapter_NguyenLieu_ChiTiet_MonAn extends RecyclerView.Adapter<Adapter_NguyenLieu_ChiTiet_MonAn.MyViewHolder> {

    Context context;
    ArrayList<String> dsNguyenLieu;

    public Adapter_NguyenLieu_ChiTiet_MonAn(Context context, ArrayList<String> dsNguyenLieu) {
        this.context = context;
        this.dsNguyenLieu = dsNguyenLieu;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView tvItemNguyenLieuCTMAH;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvItemNguyenLieuCTMAH = itemView.findViewById(R.id.tvItemNguyenLieuCTMAH);

        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyclerview_nguyenlieu_chitiet_monan_home, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tvItemNguyenLieuCTMAH.setText("- " +dsNguyenLieu.get(position).trim());
    }

    @Override
    public int getItemCount() {
        return dsNguyenLieu.size();
    }
}
