package com.duanmot.myapplication.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.duanmot.myapplication.Database.HuongDanNauAnDao;
import com.duanmot.myapplication.Database.NguyenLieuDao;
import com.duanmot.myapplication.Database.PhanLoaiMonAnDao;
import com.duanmot.myapplication.Database.ThongTinMonAnDao;
import com.duanmot.myapplication.R;
import com.duanmot.myapplication.SuaMonAn;
import com.duanmot.myapplication.TaoMonAn;
import com.duanmot.myapplication.ThongTinMonAnFragment;
import com.duanmot.myapplication.model.HuongDanNauAn;
import com.duanmot.myapplication.model.MonAn;
import com.duanmot.myapplication.model.NguyenLieu;
import com.duanmot.myapplication.model.PhanLoaiMonAn;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class Adapter_DSMonAnNguoiDung extends BaseAdapter {

    Context context;
    ArrayList<MonAn> dsMonAn;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReferenceThongTinMonAn;
    DatabaseReference databaseReferenceNguyenLieu;
    DatabaseReference databaseReferenceHuongDan;
    DatabaseReference databaseReferencePhanLoai;

    private static final int IMG_WIDTH = 640;
    private static final int IMG_HEIGHT = 480;


    public Adapter_DSMonAnNguoiDung(Context context, ArrayList<MonAn> dsMonAn) {
        this.context = context;
        this.dsMonAn = dsMonAn;
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReferenceThongTinMonAn = firebaseDatabase.getReference().child("TaoMonAn");
        databaseReferenceNguyenLieu = firebaseDatabase.getReference().child("NguyenLieu");
        databaseReferenceHuongDan = firebaseDatabase.getReference().child("HuongDanNauAn");
        databaseReferencePhanLoai = firebaseDatabase.getReference().child("PhanLoaiMonAn");

    }

    @Override
    public int getCount() {
        return dsMonAn.size();
    }

    @Override
    public Object getItem(int i) {
        return dsMonAn.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {

        final ViewHolder holder;

        if (view == null) {
            holder = new ViewHolder();
            LayoutInflater inf = ((Activity) context).getLayoutInflater();
            view = inf.inflate(R.layout.item_monan_nguoidung, null);

            holder.tvItemMonAnNguoiDung = view.findViewById(R.id.tvItemMonAnNguoiDung);
            holder.imgItemMonAnNguoiDung = view.findViewById(R.id.imgItemMonAnNguoiDung);
            holder.ll_itemMonAn = view.findViewById(R.id.ll_itemMonAn);
            holder.tvMenu_Sua_Xoa = view.findViewById(R.id.tvMenu_Sua_Xoa);
            holder.tvNgayDang = view.findViewById(R.id.tvNgayDang);


            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        final MonAn monAn = dsMonAn.get(i);
        holder.tvItemMonAnNguoiDung.setText(monAn.getTenMonAn());

        if (monAn.getAnhMonAn() != null) {
            byte[] decodedTring = Base64.decode(monAn.getAnhMonAn(), Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedTring, 0, decodedTring.length);

            holder.imgItemMonAnNguoiDung.setImageBitmap(decodedByte);
        }

        holder.tvNgayDang.setText(monAn.getThoiGianTaoMonAn());

        holder.tvMenu_Sua_Xoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PopupMenu popupMenu = new PopupMenu(context, holder.tvMenu_Sua_Xoa);

                popupMenu.inflate(R.menu.menu_sua_xoa_item_monan);

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {

                        switch (menuItem.getItemId()) {
                            case R.id.menu_sua_monan:

                                try {

                                    databaseReferenceNguyenLieu.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                            for (final DataSnapshot dataSnapshotNguyenLieu : dataSnapshot.getChildren()) {

                                                databaseReferenceHuongDan.addValueEventListener(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                                        for (final DataSnapshot dataSnapshotHuongdan : dataSnapshot.getChildren()) {

                                                            databaseReferencePhanLoai.addValueEventListener(new ValueEventListener() {
                                                                @Override
                                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                                                    for (DataSnapshot dataSnapshotPhanLoai : dataSnapshot.getChildren()) {

                                                                        NguyenLieu nguyenLieu = dataSnapshotNguyenLieu.getValue(NguyenLieu.class);

                                                                        HuongDanNauAn huongDanNauAn = dataSnapshotHuongdan.getValue(HuongDanNauAn.class);

                                                                        PhanLoaiMonAn phanLoaiMonAn = dataSnapshotPhanLoai.getValue(PhanLoaiMonAn.class);

                                                                        MonAn ma = dsMonAn.get(i);

                                                                        if (ma.getMaMonAn().equalsIgnoreCase(nguyenLieu.getMaMonAn()) &&
                                                                                ma.getMaMonAn().equalsIgnoreCase(huongDanNauAn.getMaMonAn()) &&
                                                                                ma.getMaMonAn().equalsIgnoreCase(phanLoaiMonAn.getMaMonAn())) {

                                                                            String maMonAn = ma.getMaMonAn();

                                                                            String anhMonAn = ma.getAnhMonAn();
                                                                            String tenMonAn = ma.getTenMonAn();
                                                                            String linkVideos = ma.getVideoMonAn();
                                                                            String moTaMonAn = ma.getMoTa();
                                                                            String tgChuanBi = ma.getThoiGianChuanBi();
                                                                            String tgThucHien = ma.getThoiGianThucHien();
                                                                            String doKho = ma.getDoKho();
                                                                            String khauPhan = ma.getKhauPhan();

                                                                            String chuoiNguyenLieu = nguyenLieu.getTenNguyenLieu();

                                                                            String[] mangNguyenLieu = chuoiNguyenLieu.split("@");

                                                                            String chuoiNoiDungHuongDan = huongDanNauAn.getNoiDung();

                                                                            String[] mangNoiDungHuongDan = chuoiNoiDungHuongDan.split("@");

                                                                            String maNguyenLieu = nguyenLieu.getMaNguyenLieu();

                                                                            String maHuongDan = huongDanNauAn.getMaHDNA();

                                                                            String maPhanLoaiMonAn = phanLoaiMonAn.getmPLMA();
                                                                            String thucDon = phanLoaiMonAn.getThucDon();
                                                                            String loaiMonAn = phanLoaiMonAn.getLoaiMon();
                                                                            String cachThucHien = phanLoaiMonAn.getCachThucHien();
                                                                            String dipMua = phanLoaiMonAn.getMua();
                                                                            String mucDich = phanLoaiMonAn.getMucDich();

                                                                            Intent intent = new Intent(context, SuaMonAn.class);

                                                                            if (ma.getAnhMonAn() != null) {
                                                                                byte[] decodedTring = Base64.decode(anhMonAn, Base64.DEFAULT);
                                                                                BitmapFactory.Options options = new BitmapFactory.Options();
                                                                                Bitmap decodedByteAnhMonAn = BitmapFactory.decodeByteArray(decodedTring, 0, decodedTring.length, options);

                                                                                decodedByteAnhMonAn = Bitmap.createScaledBitmap(decodedByteAnhMonAn, IMG_WIDTH, IMG_HEIGHT, false);

                                                                                //chuyển bitmap sang byteArray
                                                                                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                                                                                decodedByteAnhMonAn.compress(Bitmap.CompressFormat.PNG, 100, stream);
                                                                                byte[] byteArrayAnhMonAn = stream.toByteArray();

                                                                                intent.putExtra("maMonAnSua", maMonAn);
                                                                                intent.putExtra("image", byteArrayAnhMonAn);
                                                                                intent.putExtra("tenMonAn", tenMonAn);
                                                                                intent.putExtra("linkVideos", linkVideos);
                                                                                intent.putExtra("moTaMonAn", moTaMonAn);
                                                                                intent.putExtra("tgChuanBi", tgChuanBi);
                                                                                intent.putExtra("tgThucHien", tgThucHien);
                                                                                intent.putExtra("doKho", doKho);
                                                                                intent.putExtra("khauPhan", khauPhan);

                                                                                intent.putExtra("maNguyenLieuSua", maNguyenLieu);
                                                                                intent.putExtra("mangNguyenLieuSua", mangNguyenLieu);

                                                                                intent.putExtra("maHuongDanSua", maHuongDan);
                                                                                intent.putExtra("mangHuongDanSua", mangNoiDungHuongDan);

                                                                                intent.putExtra("maPhanLoaiMonAn", maPhanLoaiMonAn);
                                                                                intent.putExtra("thucDon", thucDon);
                                                                                intent.putExtra("loaiMonAn", loaiMonAn);
                                                                                intent.putExtra("cachThucHien", cachThucHien);
                                                                                intent.putExtra("dipMua", dipMua);
                                                                                intent.putExtra("mucDich", mucDich);

                                                                                context.startActivity(intent);
                                                                            }
                                                                        }
                                                                    }
                                                                }

                                                                @Override
                                                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                                                }
                                                            });

                                                        }

                                                    }

                                                    @Override
                                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                                    }
                                                });

                                            }

                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                        }
                                    });
                                }catch (Exception e){
                                    e.printStackTrace();
                                }


                                break;

                            case R.id.menu_xoa_monan:

                                final AlertDialog.Builder builder = new AlertDialog.Builder(context);

                                builder.setTitle(" Bạn có muốn xóa món ăn này?");
                                builder.setIcon(R.drawable.ic_delete_black_24dp);
                                builder.setNegativeButton("Đồng ý", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {


                                        ThongTinMonAnDao thongTinMonAnDao = new ThongTinMonAnDao(context);
                                        thongTinMonAnDao.deleteThongTinMonAn(monAn);

                                        databaseReferenceNguyenLieu.addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                                for (final DataSnapshot dataNguyenLieu : dataSnapshot.getChildren()) {

                                                    databaseReferenceHuongDan.addValueEventListener(new ValueEventListener() {
                                                        @Override
                                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                                            for (final DataSnapshot dataHuongDan : dataSnapshot.getChildren()) {

                                                                databaseReferencePhanLoai.addValueEventListener(new ValueEventListener() {
                                                                    @Override
                                                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                                                        for (DataSnapshot dataPhanLoai : dataSnapshot.getChildren()) {


                                                                            NguyenLieu nguyenLieu = dataNguyenLieu.getValue(NguyenLieu.class);

                                                                            HuongDanNauAn huongDanNauAn = dataHuongDan.getValue(HuongDanNauAn.class);

                                                                            PhanLoaiMonAn phanLoaiMonAn = dataPhanLoai.getValue(PhanLoaiMonAn.class);

                                                                            if (monAn.getMaMonAn().equalsIgnoreCase(nguyenLieu.getMaMonAn()) &&
                                                                                    monAn.getMaMonAn().equalsIgnoreCase(huongDanNauAn.getMaMonAn()) &&

                                                                                    monAn.getMaMonAn().equalsIgnoreCase(phanLoaiMonAn.getMaMonAn())) {

                                                                                String keyNguyenLieu = dataNguyenLieu.getKey();
                                                                                String keyHuongDan = dataHuongDan.getKey();
                                                                                String keyPhanLoai = dataPhanLoai.getKey();

                                                                                databaseReferenceNguyenLieu.child(keyNguyenLieu).setValue(null);
                                                                                databaseReferenceHuongDan.child(keyHuongDan).setValue(null);
                                                                                databaseReferencePhanLoai.child(keyPhanLoai).setValue(null);
                                                                                Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();

                                                                            }
                                                                        }

                                                                    }

                                                                    @Override
                                                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                                                    }
                                                                });
                                                            }

                                                        }

                                                        @Override
                                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                                        }
                                                    });
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                            }
                                        });

                                    }
                                });

                                builder.setPositiveButton("Hủy", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                    }
                                });
                                builder.setCancelable(false);
                                builder.show();

                                break;
                        }

                        return false;
                    }
                });
                popupMenu.show();


            }
        });
        return view;
    }

    class ViewHolder {
        public TextView tvItemMonAnNguoiDung, tvMenu_Sua_Xoa, tvNgayDang;
        public ImageView imgItemMonAnNguoiDung;
        public LinearLayout ll_itemMonAn;
    }
}
