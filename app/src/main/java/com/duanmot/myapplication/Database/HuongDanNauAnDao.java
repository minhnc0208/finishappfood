package com.duanmot.myapplication.Database;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.duanmot.myapplication.model.HuongDanNauAn;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HuongDanNauAnDao {

    private DatabaseReference databaseReference;

    Context context;
    String maHDNA;

    public HuongDanNauAnDao(Context context){
        this.context = context;
        this.databaseReference = FirebaseDatabase.getInstance().getReference("HuongDanNauAn");
    }

    public void insert(HuongDanNauAn huongDanNauAn){

        maHDNA = databaseReference.push().getKey();

        databaseReference.child(maHDNA).setValue(huongDanNauAn).addOnSuccessListener(new OnSuccessListener<Void>() {
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






    public void updateHuongDan(final HuongDanNauAn huongDanNauAn){

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot data: dataSnapshot.getChildren()){
                    if(data.child("maHDNA").getValue(String.class).equalsIgnoreCase(huongDanNauAn.getMaHDNA())){
                        maHDNA = data.getKey();

                        Log.d("Get Key", "onCreate: key" + maHDNA);


                        databaseReference.child(maHDNA).setValue(huongDanNauAn).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d("update", " update Thành Công");
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d("update", "update Thất Bại");
                            }
                        });
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    public ArrayList<HuongDanNauAn> getDSHDNA(){

        final ArrayList<HuongDanNauAn> list = new ArrayList<>();

        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                list.clear();
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    HuongDanNauAn huongDanNauAn = data.getValue(HuongDanNauAn.class);
                    list.add(huongDanNauAn);
                }

//                QuanLySachFragment.updateListViewQLSFromadapter();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        databaseReference.addValueEventListener(listener);
        return list;
    }

    public void deleteHuongDan(final HuongDanNauAn huongDanNauAn){

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                for (DataSnapshot data: dataSnapshot.getChildren()) {

                    if (data.child("maHDNA").getValue(String.class).equalsIgnoreCase(huongDanNauAn.getMaHDNA())) {
                        maHDNA = data.getKey();
                        Log.d("getkey", "OnCreate: key:" + maHDNA);

                        databaseReference.child(maHDNA).setValue(null).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {

                                Log.d("delete", " Thành Công");
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d("delete", " Thất Bại");
                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
