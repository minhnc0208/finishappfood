package com.duanmot.myapplication.Database;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.duanmot.myapplication.HomeFragmentActivity;
import com.duanmot.myapplication.model.NguyenLieu;
import com.duanmot.myapplication.model.PhanLoaiMonAn;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PhanLoaiMonAnDao {

    private DatabaseReference databaseReference;

    Context context;
    String maPLMA;


    public PhanLoaiMonAnDao(Context context) {
        this.context = context;
        this.databaseReference = FirebaseDatabase.getInstance().getReference("PhanLoaiMonAn");
    }


    public void insert(PhanLoaiMonAn phanLoaiMonAn) {

        maPLMA = databaseReference.push().getKey();

        databaseReference.child(maPLMA).setValue(phanLoaiMonAn).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d("insert", " insert thành công");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("insert", " insert thất bại");
            }
        });
    }



}
