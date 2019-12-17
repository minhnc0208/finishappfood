package com.duanmot.myapplication;

import android.content.Context;
import android.content.Intent;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.duanmot.myapplication.model.MonAn;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class SuaNguyenLieu_HuongDanNauAnFragment extends Fragment {

    Button btnSuaNLHDNA;
    LinearLayout llSuaContainerNguyenLieu, llSuaContainerHuongDan;

    TextView tvSuaAddNguyenLieu, tvSuaThemBuocHuongDan, tvTenBuoc;

    AutoCompleteTextView textOut, actNoiDung;
    ImageView imgdeleteBuoc;

    String nguyenLieu;
    String chuoiNguyenLieu = "";

    int soBuoc = 0;

    String noiDungBuocHD;
    String chuoiNoiDungBuocHD = " ";

    LayoutInflater layoutInflater;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReferenceMonAn;
    DatabaseReference databaseReferenceHDNA;
    DatabaseReference databaseReferenceNguyenLieu;

    String maMonAn;

    AutoCompleteTextView childTextViewHuongDan;
    AutoCompleteTextView childTextView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_sua_nguyenlieu_huongdannauan, container, false);

        tvSuaAddNguyenLieu = view.findViewById(R.id.tvSuaAddNguyenLieu);
        llSuaContainerNguyenLieu = view.findViewById(R.id.llSuaContainerNguyenLieu);
        btnSuaNLHDNA = view.findViewById(R.id.btnSuaNLHDNA);

        llSuaContainerHuongDan = view.findViewById(R.id.llSuaContainerHuongDan);
        tvSuaThemBuocHuongDan = view.findViewById(R.id.tvSuaThemBuocHuongDan);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReferenceMonAn = firebaseDatabase.getReference("TaoMonAn");
        databaseReferenceNguyenLieu = firebaseDatabase.getReference("NguyenLieu");
        databaseReferenceHDNA = firebaseDatabase.getReference("HuongDanNauAn");

        databaseReferenceMonAn.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    MonAn monAn = dataSnapshot1.getValue(MonAn.class);
                    maMonAn = monAn.getMaMonAn();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        addEditTextThemNguyenLieu();
        addBuocHuongDan();

        suaThongTinNguyenLieu();

        return view;
    }

    private void suaThongTinNguyenLieu() {
        Intent intent = getActivity().getIntent();

        final String maMonAnSua = intent.getStringExtra("maMonAnSua");

        String[] mangNguyenLieuIntent = intent.getStringArrayExtra("mangNguyenLieuSua");

        String[] mangHuongDanIntent = intent.getStringArrayExtra("mangHuongDanSua");

        for (int i = 0; i < mangNguyenLieuIntent.length; i++) {
            layoutInflater = (LayoutInflater) getActivity().getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View addView = layoutInflater.inflate(R.layout.item_themnguyenlieu, null);
            textOut = (AutoCompleteTextView) addView.findViewById(R.id.textout);
            textOut.setText(mangNguyenLieuIntent[i]);

            final ImageButton buttonRemove = (ImageButton) addView.findViewById(R.id.btnRemove);
            final View.OnClickListener thisListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    ((LinearLayout) addView.getParent()).removeView(addView);

                    listAllAddViewNguyenLieu();
                }
            };

            buttonRemove.setOnClickListener(thisListener);


            llSuaContainerNguyenLieu.addView(addView);
        }

        for (int j = 0; j < mangHuongDanIntent.length; j++) {
            LayoutInflater layoutInflater = (LayoutInflater) getActivity().getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View addView2 = layoutInflater.inflate(R.layout.item_buoc_huongdan_cachlam, null);
            actNoiDung = addView2.findViewById(R.id.actNoiDung);
            tvTenBuoc = addView2.findViewById(R.id.tvTenBuoc);
            soBuoc = soBuoc + 1;
            if (soBuoc <= 10) {
                tvTenBuoc.setText("Bước " + (soBuoc));

            } else {
                Toast.makeText(getContext(), "Bạn đã vượt quá số bước", Toast.LENGTH_SHORT).show();
            }

            actNoiDung.setText(mangHuongDanIntent[j]);

            imgdeleteBuoc = addView2.findViewById(R.id.imgdeleteBuoc);
            View.OnClickListener thisListener = new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    ((LinearLayout) addView2.getParent()).removeView(addView2);
                    soBuoc = 0;

                    int childCountHuongDan = llSuaContainerHuongDan.getChildCount();
                    for (int j = 0; j < childCountHuongDan; j++) {
                        View thisChildHuongDan = llSuaContainerHuongDan.getChildAt(j);
                        TextView tvTenBuoc = thisChildHuongDan.findViewById(R.id.tvTenBuoc);

                        soBuoc = soBuoc + 1;
                        tvTenBuoc.setText("Bước " + (soBuoc));

                    }
                    listAllAddViewHuongDan();
                }
            };

            imgdeleteBuoc.setOnClickListener(thisListener);

            llSuaContainerHuongDan.addView(addView2);
        }

        btnSuaNLHDNA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                listAllAddViewNguyenLieu();
                listAllAddViewHuongDan();

                int childCountHuongDan = llSuaContainerHuongDan.getChildCount();

                int childCountNguyenLieu = llSuaContainerNguyenLieu.getChildCount();

                if (childCountNguyenLieu != 0) {

                    for (int i = 0; i < childCountNguyenLieu; i++) {
                        View thisChildNguyenLieu = llSuaContainerNguyenLieu.getChildAt(i);
                        childTextView = (AutoCompleteTextView) thisChildNguyenLieu.findViewById(R.id.textout);

                        nguyenLieu = childTextView.getText().toString().trim();

                        chuoiNguyenLieu = chuoiNguyenLieu + nguyenLieu + "@";

                    }

                    String kiemTraKyTu = "@";
                    if (!nguyenLieu.isEmpty()) {
                        if (!nguyenLieu.contains(kiemTraKyTu)) {
                            if (childCountHuongDan != 0) {
                                for (int j = 0; j < childCountHuongDan; j++) {
                                    View thisChildHuongDan = llSuaContainerHuongDan.getChildAt(j);
                                    childTextViewHuongDan = (AutoCompleteTextView) thisChildHuongDan.findViewById(R.id.actNoiDung);
                                    noiDungBuocHD = childTextViewHuongDan.getText().toString().trim();
                                    chuoiNoiDungBuocHD = chuoiNoiDungBuocHD + noiDungBuocHD + "@";
                                }

                                if (!noiDungBuocHD.isEmpty()) {
                                    if (!noiDungBuocHD.contains(kiemTraKyTu)) {

                                        Query queryNguyenLieu = databaseReferenceNguyenLieu.orderByChild("maMonAn").equalTo(maMonAnSua);
                                        queryNguyenLieu.addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                                for (DataSnapshot dataNguyenLieu : dataSnapshot.getChildren()) {

                                                    dataNguyenLieu.getRef().child("tenNguyenLieu").setValue(chuoiNguyenLieu);

                                                    Toast.makeText(getActivity(), "Cập nhật nguyên liệu thành công", Toast.LENGTH_SHORT).show();
                                                }

                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                            }
                                        });


                                        Query queryHuongDan = databaseReferenceHDNA.orderByChild("maMonAn").equalTo(maMonAnSua);
                                        queryHuongDan.addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                                for (DataSnapshot data : dataSnapshot.getChildren()) {

                                                    data.getRef().child("noiDung").setValue(chuoiNoiDungBuocHD);

                                                }

                                                Toast.makeText(getActivity(), "Cập nhật hướng dẫn thành công", Toast.LENGTH_SHORT).show();

                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                            }
                                        });


                                      //  SuaMonAn.viewpagerSuaTMA.setCurrentItem(2, true);


                                    } else {
                                        Toast.makeText(getContext(), "Vui lòng không nhập ký tự @", Toast.LENGTH_SHORT).show();
                                    }

                                } else if (noiDungBuocHD.isEmpty()) {
                                    Toast.makeText(getContext(), "Vui lòng nhập nội dung các bước", Toast.LENGTH_SHORT).show();
                                }

                            } else {
                                Toast.makeText(getContext(), "Vui lòng thêm bước hướng dẫn", Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            Toast.makeText(getContext(), "Vui lòng không nhập ký tự @", Toast.LENGTH_SHORT).show();
                        }

                    } else if (nguyenLieu.isEmpty()) {
                        Toast.makeText(getContext(), "Vui lòng nhập nguyên liệu", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "Vui lòng thêm nguyên liệu", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }



    private void addBuocHuongDan() {
        tvSuaThemBuocHuongDan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LayoutInflater layoutInflater = (LayoutInflater) getActivity().getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View addView2 = layoutInflater.inflate(R.layout.item_buoc_huongdan_cachlam, null);

                actNoiDung = addView2.findViewById(R.id.actNoiDung);

                actNoiDung.setText(" ");
                tvTenBuoc = addView2.findViewById(R.id.tvTenBuoc);

                soBuoc = soBuoc + 1;
                Log.d("so", String.valueOf(soBuoc));
                if (soBuoc <= 10) {
                    tvTenBuoc.setText("Bước " + (soBuoc));

                } else {
                    Toast.makeText(getContext(), "Bạn đã vượt quá số bước", Toast.LENGTH_SHORT).show();
                }

                imgdeleteBuoc = addView2.findViewById(R.id.imgdeleteBuoc);
                View.OnClickListener thisListener = new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        ((LinearLayout) addView2.getParent()).removeView(addView2);
                        soBuoc = 0;

                        int childCountHuongDan = llSuaContainerHuongDan.getChildCount();
                        for (int j = 0; j < childCountHuongDan; j++) {
                            View thisChildHuongDan = llSuaContainerHuongDan.getChildAt(j);
                            TextView tvTenBuoc = thisChildHuongDan.findViewById(R.id.tvTenBuoc);

                            soBuoc = soBuoc + 1;
                            tvTenBuoc.setText("Bước " + (soBuoc));

                        }
                        listAllAddViewHuongDan();
                    }
                };

                imgdeleteBuoc.setOnClickListener(thisListener);
                llSuaContainerHuongDan.addView(addView2);

            }
        });
    }


    private void addEditTextThemNguyenLieu() {
        tvSuaAddNguyenLieu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                layoutInflater = (LayoutInflater) getActivity().getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                final View addView = layoutInflater.inflate(R.layout.item_themnguyenlieu, null);
                textOut = (AutoCompleteTextView) addView.findViewById(R.id.textout);
                textOut.setText(" ");

                final ImageButton buttonRemove = (ImageButton) addView.findViewById(R.id.btnRemove);
                final View.OnClickListener thisListener = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        ((LinearLayout) addView.getParent()).removeView(addView);

                        listAllAddViewNguyenLieu();
                    }
                };

                buttonRemove.setOnClickListener(thisListener);
                llSuaContainerNguyenLieu.addView(addView);

            }
        });
    }

    private void listAllAddViewNguyenLieu() {
        try {
            int childCountNguyenLieu = llSuaContainerNguyenLieu.getChildCount();
            for (int i = 0; i < childCountNguyenLieu; i++) {
                View thisChildNguyenLieu = llSuaContainerNguyenLieu.getChildAt(i);
                childTextView = (AutoCompleteTextView) thisChildNguyenLieu.findViewById(R.id.textout);
                nguyenLieu = childTextView.getText().toString().trim();

                String[] mangNguyenLieu = nguyenLieu.split("@");

                for (int k = 0; k < mangNguyenLieu.length; k++) {
                    chuoiNguyenLieu = nguyenLieu.substring(0, mangNguyenLieu.length - 1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void listAllAddViewHuongDan() {
        try {
            int childCountHuongDan = llSuaContainerHuongDan.getChildCount();
            if (childCountHuongDan != 0) {
                for (int j = 0; j < childCountHuongDan; j++) {
                    View thisChildHuongDan = llSuaContainerHuongDan.getChildAt(j);
                    childTextViewHuongDan = (AutoCompleteTextView) thisChildHuongDan.findViewById(R.id.actNoiDung);
                    noiDungBuocHD = childTextViewHuongDan.getText().toString().trim();

                    String[] mangNoiDungBuocHD = noiDungBuocHD.split("@");
                    for (int h = 0; h < mangNoiDungBuocHD.length; h++) {
                        chuoiNoiDungBuocHD = noiDungBuocHD.substring(0, mangNoiDungBuocHD.length - 1);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
