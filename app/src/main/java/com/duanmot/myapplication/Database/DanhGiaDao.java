package com.duanmot.myapplication.Database;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.duanmot.myapplication.model.RatingStar;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;



public class DanhGiaDao {

    private DatabaseReference databaseReference;

    Context context;
    String maDanhGia;

    public DanhGiaDao(Context context){
        this.context = context;
        this.databaseReference = FirebaseDatabase.getInstance().getReference("DanhGia");
    }


    public void insert(RatingStar ratingStar){

        maDanhGia = databaseReference.push().getKey();

        databaseReference.child(maDanhGia).setValue(ratingStar).addOnSuccessListener(new OnSuccessListener<Void>() {
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
