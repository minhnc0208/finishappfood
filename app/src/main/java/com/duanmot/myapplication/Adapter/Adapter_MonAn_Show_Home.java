package com.duanmot.myapplication.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.duanmot.myapplication.Home_ChiTiet_MonAn;
import com.duanmot.myapplication.R;
import com.duanmot.myapplication.model.HuongDanNauAn;

import com.duanmot.myapplication.model.MonAn;
import com.duanmot.myapplication.model.NguyenLieu;
import com.duanmot.myapplication.model.PhanLoaiMonAn;
import com.duanmot.myapplication.model.RatingStar;
import com.duanmot.myapplication.model.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;

import de.hdodenhof.circleimageview.CircleImageView;

public class Adapter_MonAn_Show_Home extends RecyclerView.Adapter<Adapter_MonAn_Show_Home.MyViewHolder> {

    Context context;
    ArrayList<MonAn> dsMonAn;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReferenceMonAn;
    DatabaseReference databaseReferenceNguyenLieu;
    DatabaseReference databaseReferenceHuongDan;
    DatabaseReference databaseReferencePhanLoai;
    DatabaseReference databaseReferenceUser;
    DatabaseReference databaseReferenceLikes;
    DatabaseReference databaseReferenceDanhGia;

    private static final int IMG_WIDTH = 640;
    private static final int IMG_HEIGHT = 480;

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;


    public class MyViewHolder extends RecyclerView.ViewHolder {

        public ImageView imgItemAMAH;
        public TextView tvTenMAH, tvTGTHH, tvDGMAh, tvDisplay_no_of_likes, tvNameUserHome;
        public ImageButton btnLike;
        public CircleImageView civUserHome;

        public MyViewHolder(@NonNull final View itemView) {
            super(itemView);
            imgItemAMAH = itemView.findViewById(R.id.imgItemAMAH);
            tvTenMAH = itemView.findViewById(R.id.tvTenMAH);
            tvTGTHH = itemView.findViewById(R.id.tvTGTHH);
            tvDGMAh = itemView.findViewById(R.id.tvDGMAh);
            btnLike = itemView.findViewById(R.id.btnLike);
            tvDisplay_no_of_likes = itemView.findViewById(R.id.tvDisplay_no_of_likes);
            tvNameUserHome = itemView.findViewById(R.id.tvNameUserHome);
            civUserHome = itemView.findViewById(R.id.civUserHome);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    // tìm vị trí
                    int pos = getAdapterPosition();

                    if (pos != RecyclerView.NO_POSITION) {
                        final MonAn monAn = dsMonAn.get(pos);
                      //  Toast.makeText(context, "Bạn đã chọn: " + monAn.getTenMonAn(), Toast.LENGTH_SHORT).show();

                        databaseReferenceNguyenLieu.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                for (final DataSnapshot dataSnapshotNguyenLieu : dataSnapshot.getChildren()) {

                                    databaseReferenceHuongDan.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                            for (final DataSnapshot dataSnapshotHuongDan : dataSnapshot.getChildren()) {

                                                databaseReferencePhanLoai.addValueEventListener(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                                        for (DataSnapshot dataSnapshotPhanLoai : dataSnapshot.getChildren()) {

                                                            NguyenLieu nguyenLieu = dataSnapshotNguyenLieu.getValue(NguyenLieu.class);

                                                            HuongDanNauAn huongDanNauAn = dataSnapshotHuongDan.getValue(HuongDanNauAn.class);

                                                            PhanLoaiMonAn phanLoaiMonAn = dataSnapshotPhanLoai.getValue(PhanLoaiMonAn.class);

                                                            if (monAn.getMaMonAn().equalsIgnoreCase(nguyenLieu.getMaMonAn()) &&
                                                                    monAn.getMaMonAn().equalsIgnoreCase(huongDanNauAn.getMaMonAn()) &&
                                                                    monAn.getMaMonAn().equalsIgnoreCase(phanLoaiMonAn.getMaMonAn())) {

                                                                String maMonAn = monAn.getMaMonAn();
                                                                String tenMonAn = monAn.getTenMonAn();
                                                                String moTaMonAn = monAn.getMoTa();
                                                                String tgChuanBi = monAn.getThoiGianChuanBi();
                                                                String tgThucHien = monAn.getThoiGianThucHien();
                                                                String doKho = monAn.getDoKho();
                                                                String khauPhan = monAn.getKhauPhan();

                                                                String chuoiNguyenLieu = nguyenLieu.getTenNguyenLieu();

                                                                String[] mangNguyenLieu = chuoiNguyenLieu.split("@");

                                                                ArrayList<String> dsnguyenLieu = new ArrayList<>();
                                                                Collections.addAll(dsnguyenLieu, mangNguyenLieu);

                                                                String chuoiBuocHuongDan = huongDanNauAn.getNoiDung();
                                                                String[] mangBuocHuongDan = chuoiBuocHuongDan.split("@");

                                                                String linkYoutube = monAn.getVideoMonAn();

                                                                if (monAn.getAnhMonAn() != null) {
                                                                    byte[] decodedTring = Base64.decode(monAn.getAnhMonAn(), Base64.DEFAULT);
                                                                    BitmapFactory.Options options = new BitmapFactory.Options();
                                                                    Bitmap decodedByteAnhMonAn = BitmapFactory.decodeByteArray(decodedTring, 0, decodedTring.length, options);

                                                                    decodedByteAnhMonAn = Bitmap.createScaledBitmap(decodedByteAnhMonAn, IMG_WIDTH, IMG_HEIGHT, false);


                                                                    //chuyển bitmap sang byteArray
                                                                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                                                                    decodedByteAnhMonAn.compress(Bitmap.CompressFormat.PNG, 100, stream);
                                                                    byte[] byteArrayAnhMonAn = stream.toByteArray();
//                                                                    decodedByteAnhMonAn.recycle();

                                                                    Intent intent = new Intent(context, Home_ChiTiet_MonAn.class);
                                                                    Bundle bundle = new Bundle();

                                                                    bundle.putString("maMonAn", maMonAn);
                                                                    bundle.putString("tenMonAn", tenMonAn);
                                                                    bundle.putByteArray("image", byteArrayAnhMonAn);
                                                                    bundle.putString("linkYoutube", linkYoutube);
                                                                    bundle.putString("moTaMonAn", moTaMonAn);
                                                                    bundle.putStringArray("mangNguyenLieu", mangNguyenLieu);
                                                                    bundle.putStringArray("mangBuocHuongDan", mangBuocHuongDan);
                                                                    bundle.putString("thoiGianChuanBi", tgChuanBi);
                                                                    bundle.putString("thoiGianThucHien", tgThucHien);
                                                                    bundle.putString("doKho", doKho);
                                                                    bundle.putString("khauPhan", khauPhan);

                                                                    intent.putExtra("dulieu", bundle);
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
                    }
                }
            });


        }
    }


    public Adapter_MonAn_Show_Home(Context context, ArrayList<MonAn> dsMonAn) {
        this.context = context;
        this.dsMonAn = dsMonAn;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_monan_home, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReferenceMonAn = firebaseDatabase.getReference("TaoMonAn");
        databaseReferenceNguyenLieu = firebaseDatabase.getReference("NguyenLieu");
        databaseReferenceHuongDan = firebaseDatabase.getReference("HuongDanNauAn");
        databaseReferencePhanLoai = firebaseDatabase.getReference("PhanLoaiMonAn");
        databaseReferenceUser = firebaseDatabase.getReference("Users");
        databaseReferenceLikes = firebaseDatabase.getReference("Likes");
        databaseReferenceDanhGia = firebaseDatabase.getReference("DanhGia");

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        final MonAn monAn = dsMonAn.get(position);

        if (monAn.getAnhMonAn() != null) {
            byte[] decodedTring = Base64.decode(monAn.getAnhMonAn(), Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedTring, 0, decodedTring.length);

            holder.imgItemAMAH.setImageBitmap(decodedByte);
        }
        holder.tvTenMAH.setText(monAn.getTenMonAn());
        holder.tvTGTHH.setText(monAn.getThoiGianThucHien());

        isLiked(monAn.getMaMonAn(), holder.btnLike);
        nrLikes(holder.tvDisplay_no_of_likes, monAn.getMaMonAn());

        holder.btnLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.btnLike.getTag().equals("like")) {

                    FirebaseDatabase.getInstance().getReference().child("Likes").child(monAn.getMaMonAn()).child(firebaseUser.getUid()).setValue(true);
                } else {

                    FirebaseDatabase.getInstance().getReference().child("Likes").child(monAn.getMaMonAn()).child(firebaseUser.getUid()).removeValue();
                }
            }
        });

        textRatingStar(holder.tvDGMAh, monAn.getMaMonAn());

        tacgia(holder.civUserHome, holder.tvNameUserHome, monAn.getMaNguoiDung());

    }


    private void tacgia(final CircleImageView civUserHome, final TextView tvNameUserHome, String maNguoiDung) {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        Query query = databaseReferenceUser.orderByChild("uid").equalTo(maNguoiDung);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataUsers : dataSnapshot.getChildren()) {

                    if (dataUsers == null) {

                        tvNameUserHome.setText("Mỹ Thực Việt Nam");
                        Picasso.get().load(R.drawable.img_chef).into(civUserHome);

                    } else {

                        String name = "" + dataUsers.child("name").getValue();

                        String image = "" + dataUsers.child("image").getValue();

                        tvNameUserHome.setText(name);

                        try {
                            // if image is received then set
                            Picasso.get().load(image).into(civUserHome);
                        } catch (Exception e) {
                            // if there is any exception while getting image then set default
                            Picasso.get().load(R.drawable.img_chef).into(civUserHome);
                        }
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return dsMonAn.size();
    }

    private void isLiked(String postid, final ImageView imageView) {
        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        DatabaseReference databaseReferenceLike = FirebaseDatabase.getInstance().getReference().child("Likes").child(postid);

        databaseReferenceLike.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(firebaseUser.getUid()).exists()) {
                    imageView.setImageResource(R.drawable.heart_48px_red);
                    imageView.setTag("Liked");
                } else {
                    imageView.setImageResource(R.drawable.heart);
                    imageView.setTag("like");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    private void nrLikes(final TextView likes, String postid) {
        DatabaseReference databaseReferenceLike = FirebaseDatabase.getInstance().getReference().child("Likes").child(postid);
        databaseReferenceLike.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                likes.setText(dataSnapshot.getChildrenCount() + " ");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    private void textRatingStar(final TextView textView, final String id) {
        databaseReferenceDanhGia.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                float total = 0;
                float count = 0;
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    RatingStar ratingStar = dataSnapshot1.getValue(RatingStar.class);
                    double so = ratingStar.getRatingStar();

                    if (ratingStar.getIdMonAn().equals(id)) {
                        total = (float) (total + so);
                        count = count + 1;
                    }
                }

                DecimalFormat decimalFormat = new DecimalFormat("#.##");
                if (total != 0 || count != 0) {
                    textView.setText(Float.valueOf(decimalFormat.format(total / count)) + "");
                } else {
                    textView.setText("0");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
