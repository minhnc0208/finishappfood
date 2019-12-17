package com.duanmot.myapplication.Database;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.duanmot.myapplication.model.NguyenLieu;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class NguyenLieuDao {

    private DatabaseReference databaseReference;

    Context context;
    String maNguyenLieu;

    public NguyenLieuDao(Context context){
        this.context = context;
        this.databaseReference = FirebaseDatabase.getInstance().getReference("NguyenLieu");
    }

    public void insert(NguyenLieu nguyenLieu){

        maNguyenLieu = databaseReference.push().getKey();

        databaseReference.child(maNguyenLieu).setValue(nguyenLieu).addOnSuccessListener(new OnSuccessListener<Void>() {
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




    public ArrayList<NguyenLieu> getDSNL(){

        final ArrayList<NguyenLieu> list = new ArrayList<>();

        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                list.clear();
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    NguyenLieu nguyenLieu = data.getValue(NguyenLieu.class);
                    list.add(nguyenLieu);
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

    public void updateNguyenLieu(final NguyenLieu nguyenLieu){

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot data: dataSnapshot.getChildren()){
                    if(data.child("maNguyenLieu").getValue(String.class).equalsIgnoreCase(nguyenLieu.getMaNguyenLieu())){
                        maNguyenLieu = data.getKey();

                        Log.d("Get Key", "onCreate: key" + maNguyenLieu);


                        databaseReference.child(maNguyenLieu).setValue(nguyenLieu).addOnSuccessListener(new OnSuccessListener<Void>() {
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


    public void deleteNguyenLieu(final NguyenLieu nguyenLieu){

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                for (DataSnapshot data: dataSnapshot.getChildren()) {

                    if (data.child("maNguyenLieu").getValue(String.class).equalsIgnoreCase(nguyenLieu.getMaNguyenLieu())) {
                        maNguyenLieu = data.getKey();
                        Log.d("getkey", "OnCreate: key:" + maNguyenLieu);

                        databaseReference.child(maNguyenLieu).setValue(null).addOnSuccessListener(new OnSuccessListener<Void>() {
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
