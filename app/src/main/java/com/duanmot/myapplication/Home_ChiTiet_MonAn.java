package com.duanmot.myapplication;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.duanmot.myapplication.Adapter.Adapter_Comment;
import com.duanmot.myapplication.Adapter.Adapter_HuongDan_ChiTiet_MonAn;
import com.duanmot.myapplication.Adapter.Adapter_NguyenLieu_ChiTiet_MonAn;
import com.duanmot.myapplication.Database.DanhGiaDao;
import com.duanmot.myapplication.model.Comment;
import com.duanmot.myapplication.model.RatingStar;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Locale;


public class Home_ChiTiet_MonAn extends YouTubeBaseActivity {

    ImageView imgAnhTTMAH, imgBackHome;
    TextView tvMoTaMAH, tvTenMonAnH, tvDisplay_no_of_likesItem, tvCommentCount, tvRatingStar, tvCountText, tvThoiGianChuanBi ,tvThoiGianThucHien, tvDoKho, tvKhauPhan;
    RecyclerView rvNguyenLieuMAH, rvThucHienMAH, rv_comment;
    ImageButton btnLikeItem;
    EditText edtTextComment;
    Button btnAddComment, btnThucHien;
    RatingBar ratingBarItemMA, ratingBarItemMAShow;
    ImageButton btnIconThucHien;

    String[] listSoBuoc = {"Bước 1", "Bước 2", "Bước 3", "Bước 4", "Bước 5", "Bước 6", "Bước 7", "Bước 8", "Bước 9", "Bước 10"};

    YouTubePlayerView youtubePlayerView;

    String API_KEY = "AIzaSyBlaGBM0pvZYCBjH7kKW27aUts7J9KBhhI";
    Bitmap decodedByteAnhMonAn;

    Intent intent;
    Bundle bundle;

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReferenceMonAn;
    DatabaseReference databaseReferenceLikes;
    DatabaseReference databaseReferenceUser;
    DatabaseReference databaseReferenceComment;
    DatabaseReference databaseReferenceRatingStar;
    DatabaseReference databaseReferenceThucHien;

    Adapter_Comment adapter_comment;
    String maMOnAn;
    List<Comment> listComment;
    static String COMMENT_KEY = "Comments";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home__chi_tiet__mon_an);

        imgAnhTTMAH = findViewById(R.id.imgAnhTTMAH);
        tvTenMonAnH = findViewById(R.id.tvTenMonAnH);
        tvMoTaMAH = findViewById(R.id.tvMoTaMAH);

        rvNguyenLieuMAH = findViewById(R.id.rvNguyenLieuMAH);
        rvThucHienMAH = findViewById(R.id.rvThucHienMAH);
        youtubePlayerView = findViewById(R.id.youtubePlayerView);
        imgBackHome = findViewById(R.id.imgBackHome);

        btnLikeItem = findViewById(R.id.btnLikeItem);
        tvDisplay_no_of_likesItem = findViewById(R.id.tvDisplay_no_of_likesItem);

        rv_comment = findViewById(R.id.rv_comment);

        edtTextComment = findViewById(R.id.post_detail_comment);
        btnAddComment = findViewById(R.id.post_detail_add_comment_btn);
        tvCommentCount = findViewById(R.id.tvCommentCount);

        ratingBarItemMA = findViewById(R.id.ratingBarItemMA);
        tvRatingStar = findViewById(R.id.tvRatingStar);
        ratingBarItemMAShow = findViewById(R.id.ratingBarItemMAShow);
        tvCountText = findViewById(R.id.tvCountText);
        btnThucHien = findViewById(R.id.btnThucHien);
        btnIconThucHien = findViewById(R.id.btnIconThucHien);

        tvThoiGianChuanBi = findViewById(R.id.tvThoiGianChuanBi);
        tvThoiGianThucHien = findViewById(R.id.tvThoiGianThucHien);

        tvDoKho = findViewById(R.id.tvDoKho);
        tvKhauPhan = findViewById(R.id.tvKhauPhan);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReferenceMonAn = firebaseDatabase.getReference().child("TaoMonAn");
        databaseReferenceLikes = firebaseDatabase.getReference().child("Likes");
        databaseReferenceUser = firebaseDatabase.getReference("Users");
        databaseReferenceRatingStar = firebaseDatabase.getReference("DanhGia");
        databaseReferenceThucHien = firebaseDatabase.getReference().child("ThucHien");


        imgBackHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        intent = getIntent();
        bundle = intent.getBundleExtra("dulieu");
        if (bundle != null) {

            final String linkYoutube = bundle.getString("linkYoutube");
            byte[] anhMonAn = bundle.getByteArray("image");

            if (!linkYoutube.isEmpty()) {

                playVideo(linkYoutube, youtubePlayerView);
                imgAnhTTMAH.setVisibility(View.GONE);
            } else if (!anhMonAn.equals("")) {

                decodedByteAnhMonAn = BitmapFactory.decodeByteArray(anhMonAn, 0, anhMonAn.length);
                imgAnhTTMAH.setImageBitmap(decodedByteAnhMonAn);

                youtubePlayerView.setVisibility(View.GONE);
            }


            String tenMonAn = bundle.getString("tenMonAn");
            tvTenMonAnH.setText(tenMonAn);

            String moTaMonAn = bundle.getString("moTaMonAn");
            tvMoTaMAH.setText(moTaMonAn);

            // Nguyên liệu
            String[] mangNguyenLieu = bundle.getStringArray("mangNguyenLieu");

            ArrayList<String> dsNguyenLieu = new ArrayList<>();
            Collections.addAll(dsNguyenLieu, mangNguyenLieu);

            LinearLayoutManager layoutManagerNguyenLieu = new LinearLayoutManager(Home_ChiTiet_MonAn.this, LinearLayoutManager.VERTICAL, true);
            rvNguyenLieuMAH.setLayoutManager(layoutManagerNguyenLieu);
            Adapter_NguyenLieu_ChiTiet_MonAn adapter_nguyenLieu_chiTiet_monAn = new Adapter_NguyenLieu_ChiTiet_MonAn(Home_ChiTiet_MonAn.this, dsNguyenLieu);
            rvNguyenLieuMAH.setAdapter(adapter_nguyenLieu_chiTiet_monAn);

            // Các bước nấu ăn
            String[] mangBuocHuongDan = bundle.getStringArray("mangBuocHuongDan");
            ArrayList<String> dsBuocHuongDan = new ArrayList<>();
            Collections.addAll(dsBuocHuongDan, mangBuocHuongDan);
            LinearLayoutManager layoutManagerHuongDan = new LinearLayoutManager(Home_ChiTiet_MonAn.this, LinearLayoutManager.VERTICAL, false);
            rvThucHienMAH.setLayoutManager(layoutManagerHuongDan);

            Adapter_HuongDan_ChiTiet_MonAn adapter_huongDan_chiTiet_monAn = new Adapter_HuongDan_ChiTiet_MonAn(Home_ChiTiet_MonAn.this, dsBuocHuongDan, listSoBuoc);
            rvThucHienMAH.setAdapter(adapter_huongDan_chiTiet_monAn);


            // Thời gian chuẩn bị
            String tgChuanBi = bundle.getString("thoiGianChuanBi");
            String tgThucHien = bundle.getString("thoiGianThucHien");

            tvThoiGianChuanBi.setText(tgChuanBi);
            tvThoiGianThucHien.setText(tgThucHien);

            // Độ khó
            String doKho = bundle.getString("doKho");
            tvDoKho.setText(doKho);

            // Khẩu phần
            String khauPhan = bundle.getString("khauPhan");
            tvKhauPhan.setText(khauPhan);


            // like (heart)
            maMOnAn = bundle.getString("maMonAn");
            isLiked(maMOnAn, btnLikeItem);
            nrLikes(tvDisplay_no_of_likesItem, maMOnAn);

            btnLikeItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (btnLikeItem.getTag().equals("like")) {
                        FirebaseDatabase.getInstance().getReference().child("Likes").child(maMOnAn).child(firebaseUser.getUid()).setValue(true);
                    } else {
                        FirebaseDatabase.getInstance().getReference().child("Likes").child(maMOnAn).child(firebaseUser.getUid()).removeValue();
                    }
                }
            });


            //commnet
            btnAddComment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Query query = databaseReferenceUser.orderByChild("email").equalTo(firebaseUser.getEmail());
                    query.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot dataUsers : dataSnapshot.getChildren()) {

                                String id = "" + dataUsers.child("uid").getValue();
                                String name = "" + dataUsers.child("name").getValue();
                                String image = "" + dataUsers.child("image").getValue();

                                btnAddComment.setVisibility(View.INVISIBLE);
                                databaseReferenceComment = firebaseDatabase.getReference(COMMENT_KEY).child(maMOnAn).push();

                                String comment_comment = edtTextComment.getText().toString().trim();

                                Calendar calendar = Calendar.getInstance(Locale.getDefault());
                                String pTime = DateFormat.format("dd/MM/yyyy hh:mm aa", calendar).toString();

                                Comment comment = new Comment(comment_comment, id, image, name, pTime);

                                databaseReferenceComment.setValue(comment).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        //showMessage("comment added");
                                        edtTextComment.setText("");
                                        btnAddComment.setVisibility(View.VISIBLE);
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                       // showMessage("fail to add comment : " + e.getMessage());
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

            iniRvCommnet();
            countComment(tvCommentCount, maMOnAn);

            //ratingBar
            ratingBarItemMA.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                @Override
                public void onRatingChanged(RatingBar ratingBar, final float v, boolean b) {
                    String idUser = firebaseUser.getUid();

                    RatingStar rS = new RatingStar(idUser, maMOnAn, v);

                    DanhGiaDao danhGiaDao = new DanhGiaDao(Home_ChiTiet_MonAn.this);
                    danhGiaDao.insert(rS);

                }
            });
            textRatingStar();

            // thực hiện món ăn
            thucHien(maMOnAn, btnThucHien, btnIconThucHien);
            btnThucHien.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (btnThucHien.getTag().equals("check")) {
                        FirebaseDatabase.getInstance().getReference().child("ThucHien").child(maMOnAn).child(firebaseUser.getUid()).setValue(true);
                    } else {
                        FirebaseDatabase.getInstance().getReference().child("ThucHien").child(maMOnAn).child(firebaseUser.getUid()).removeValue();
                    }

                }
            });


        }

    }


    private void thucHien(String postid, final Button button, final ImageButton buttonICon) {
        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        DatabaseReference databaseReferenceThucHien = FirebaseDatabase.getInstance().getReference().child("ThucHien").child(postid);

        databaseReferenceThucHien.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(firebaseUser.getUid()).exists()) {
                    button.setText("Đã Thực Hiện");
                    button.setTextColor(Color.RED);
                    button.setTag("checked");

                    buttonICon.setBackgroundResource(R.drawable.ic_check_red_24dp);

                } else {
                    button.setText("Thực Hiện");
                    button.setTextColor(Color.BLACK);
                    button.setTag("check");

                    buttonICon.setBackgroundResource(R.drawable.ic_check_black_24dp);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void textRatingStar() {

        databaseReferenceRatingStar.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                float total = 0;
                int count = 0;
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    RatingStar ratingStar = dataSnapshot1.getValue(RatingStar.class);
                    double so = ratingStar.getRatingStar();

                    if (ratingStar.getIdMonAn().equals(maMOnAn)) {
                        total = (float) (total + so);
                        count = count + 1;

                    }

                }

                DecimalFormat decimalFormat = new DecimalFormat("#.##");
                float numberStar = Float.valueOf(decimalFormat.format(total / count));
                ratingBarItemMAShow.setRating((float) numberStar);
                tvRatingStar.setText(Float.valueOf(decimalFormat.format(total / count)) + " Star");
                tvCountText.setText(count + " Đánh giá");

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    private void countComment(final TextView tv_countComment, String id) {

        databaseReferenceComment = FirebaseDatabase.getInstance().getReference().child("Comments").child(id);
        databaseReferenceComment.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                tv_countComment.setText(dataSnapshot.getChildrenCount() + " Bình luận");

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    private void iniRvCommnet() {

        rv_comment.setLayoutManager(new LinearLayoutManager(Home_ChiTiet_MonAn.this));
        final DatabaseReference databaseReferenceComment = firebaseDatabase.getReference("Comments").child(maMOnAn);
        databaseReferenceComment.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listComment = new ArrayList<>();
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    Comment comment = data.getValue(Comment.class);
                    listComment.add(comment);
                }

                adapter_comment = new Adapter_Comment(getApplicationContext(), listComment);
                rv_comment.setAdapter(adapter_comment);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void playVideo(final String videoId, final YouTubePlayerView youTubePlayerView) {
        //initialize youtube player view
        youTubePlayerView.initialize(API_KEY,
                new YouTubePlayer.OnInitializedListener() {
                    @Override
                    public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                                        YouTubePlayer youTubePlayer, boolean b) {

                        youTubePlayer.cueVideo(videoId);

                    }

                    @Override
                    public void onInitializationFailure(YouTubePlayer.Provider provider,
                                                        YouTubeInitializationResult youTubeInitializationResult) {


                    }
                });

    }

    private void isLiked(String postid, final ImageView imageView) {
        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        DatabaseReference databaseReferenceLike = FirebaseDatabase.getInstance().getReference().child("Likes").child(postid);

        databaseReferenceLike.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(firebaseUser.getUid()).exists()) {
                    imageView.setImageResource(R.drawable.heart_48px_red);
                    imageView.setTag("liked");
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
                likes.setText(dataSnapshot.getChildrenCount() + " likes");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }



    private void showMessage(String message) {

        Toast.makeText(this, message, Toast.LENGTH_LONG).show();

    }

}

