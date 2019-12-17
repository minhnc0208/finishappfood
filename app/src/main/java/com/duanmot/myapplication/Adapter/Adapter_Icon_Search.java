package com.duanmot.myapplication.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.duanmot.myapplication.R;
import com.duanmot.myapplication.Search_LoaiMonAn_BanhVaBanhNgot;
import com.duanmot.myapplication.Search_LoaiMonAn_Chay;
import com.duanmot.myapplication.Search_LoaiMonAn_Chinh;
import com.duanmot.myapplication.Search_LoaiMonAn_KhaiVi;
import com.duanmot.myapplication.Search_LoaiMonAn_Lau;
import com.duanmot.myapplication.Search_LoaiMonAn_NhanhVaDe;
import com.duanmot.myapplication.Search_LoaiMonAn_Nhau;
import com.duanmot.myapplication.Search_LoaiMonAn_Salad;
import com.duanmot.myapplication.Search_LoaiMonAn_Sang;
import com.duanmot.myapplication.Search_LoaiMonAn_Toi;
import com.duanmot.myapplication.Search_LoaiMonAn_TotChoSucKhoe;
import com.duanmot.myapplication.Search_LoaiMonAn_Trua;
import com.duanmot.myapplication.Search_LoaiMonAn_Vat;
import com.duanmot.myapplication.Search_Loai_NuocCham;
import com.duanmot.myapplication.Search_LoaiMonAn_Spaghetti;
import com.duanmot.myapplication.Search_Loai_ThucUong;
import com.duanmot.myapplication.model.MonAn;
import com.duanmot.myapplication.model.PhanLoaiMonAn;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class Adapter_Icon_Search extends RecyclerView.Adapter<Adapter_Icon_Search.IconSearchViewHolder> {


    Context context;


    int anh[];
    String[] ten;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReferenceMonAn;
    DatabaseReference databaseReferenceLoaiMonAn;

    ArrayList<MonAn> dsMonAnSang;

    public Adapter_Icon_Search(Context context, int[] anh, String[] ten) {
        this.context = context;
        this.anh = anh;
        this.ten = ten;
    }

    public class IconSearchViewHolder extends RecyclerView.ViewHolder {
        CircleImageView civIconSearch;
        TextView tvTenIconSearch;

        public IconSearchViewHolder(@NonNull View itemView) {
            super(itemView);
            civIconSearch = itemView.findViewById(R.id.civIconSearch);
            tvTenIconSearch = itemView.findViewById(R.id.tvTenIconSearch);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    int position = getAdapterPosition();
                    switch (position) {
                        case 0:
                            ansang();
                            break;
                        case 1:
                            antrua();
                            break;
                        case 2:
                            antoi();
                            break;
                        case 3:
                            anvat();
                            break;
                        case 4:
                            khaivi();
                            break;
                        case 5:
                            anchay();
                            break;
                        case 6:
                            thucuong();
                            break;
                        case 7:
                            salad();
                            break;
                        case 8:
                            nuoccham();
                            break;
                        case 9:
                            spaghetti();
                            break;
                        case 10:
                            monchinh();
                            break;
                        case 11:
                            nhanhvade();
                            break;
                        case 12:
                            totchosuckhoe();
                            break;
                        case 13:
                            banhvabanhngot();
                            break;
                        case 14:

                            lau();

                            break;
                        case 15:

                            monnhau();

                            break;

                    }
                }
            });

        }

    }

    private void monnhau() {

        Intent intent = new Intent(context, Search_LoaiMonAn_Nhau.class);
        context.startActivity(intent);

    }

    private void lau() {

        Intent intent = new Intent(context, Search_LoaiMonAn_Lau.class);
        context.startActivity(intent);

    }

    private void banhvabanhngot() {

        Intent intent = new Intent(context, Search_LoaiMonAn_BanhVaBanhNgot.class);
        context.startActivity(intent);

    }

    private void totchosuckhoe() {

        Intent intent = new Intent(context, Search_LoaiMonAn_TotChoSucKhoe.class);
        context.startActivity(intent);
    }

    private void nhanhvade() {

        Intent intent = new Intent(context, Search_LoaiMonAn_NhanhVaDe.class);
        context.startActivity(intent);
    }

    private void monchinh() {

        Intent intent = new Intent(context, Search_LoaiMonAn_Chinh.class);
        context.startActivity(intent);

    }

    private void spaghetti() {
        Intent intent = new Intent(context, Search_LoaiMonAn_Spaghetti.class);
        context.startActivity(intent);

    }

    private void nuoccham() {

        Intent intent = new Intent(context, Search_Loai_NuocCham.class);
        context.startActivity(intent);

    }

    private void salad() {

        Intent intent = new Intent(context, Search_LoaiMonAn_Salad.class);
        context.startActivity(intent);

    }

    private void thucuong() {
        Intent intent = new Intent(context, Search_Loai_ThucUong.class);
        context.startActivity(intent);
    }

    private void anchay() {

        Intent intent = new Intent(context, Search_LoaiMonAn_Chay.class);
        context.startActivity(intent);

    }

    private void khaivi() {

        Intent intent = new Intent(context, Search_LoaiMonAn_KhaiVi.class);
        context.startActivity(intent);
    }

    private void anvat() {

        Intent intent = new Intent(context, Search_LoaiMonAn_Vat.class);
        context.startActivity(intent);

    }

    private void antoi() {
        Intent intent = new Intent(context, Search_LoaiMonAn_Toi.class);
        context.startActivity(intent);

    }

    private void antrua() {

        Intent intent = new Intent(context, Search_LoaiMonAn_Trua.class);
        context.startActivity(intent);

    }

    private void ansang() {

        Intent intent = new Intent(context, Search_LoaiMonAn_Sang.class);
        context.startActivity(intent);

    }


    @NonNull
    @Override
    public IconSearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_icon_search, parent, false);

        IconSearchViewHolder iconSearchViewHolder = new IconSearchViewHolder(view);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReferenceMonAn = firebaseDatabase.getReference().child("TaoMonAn");
        databaseReferenceLoaiMonAn = firebaseDatabase.getReference("PhanLoaiMonAn");

        return iconSearchViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull IconSearchViewHolder holder, int position) {

             holder.civIconSearch.setImageResource(anh[position]);
             holder.tvTenIconSearch.setText(ten[position]);

    }

    @Override
    public int getItemCount() {
        return ten.length;
    }


}
