package com.duanmot.myapplication.Database;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.duanmot.myapplication.DanhSachMonAnNguoiDung;
import com.duanmot.myapplication.HomeFragmentActivity;
import com.duanmot.myapplication.model.MonAn;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ThongTinMonAnDao {

    private DatabaseReference databaseReference;

    Context context;
    String maMonAn;

    public ThongTinMonAnDao(Context context) {
        this.context = context;
        this.databaseReference = FirebaseDatabase.getInstance().getReference("TaoMonAn");
    }

    public ArrayList<MonAn> getDSMonAn() {

        final ArrayList<MonAn> list = new ArrayList<>();

        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                list.clear();
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    MonAn monAn = data.getValue(MonAn.class);
                    list.add(monAn);
                }

                DanhSachMonAnNguoiDung.udateListViewMonAnNguoiDung();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        databaseReference.addValueEventListener(listener);
        return list;
    }

    public ArrayList<MonAn> getDSMonAnHome() {

        final ArrayList<MonAn> list = new ArrayList<>();

        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                list.clear();
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    MonAn monAn = data.getValue(MonAn.class);
                    list.add(monAn);
                }

                HomeFragmentActivity.udateRecyclerView();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        databaseReference.addValueEventListener(listener);
        return list;
    }


    public void insert(MonAn monAn) {

        maMonAn = databaseReference.push().getKey();

        databaseReference.child(maMonAn).setValue(monAn).addOnSuccessListener(new OnSuccessListener<Void>() {
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

    public void deleteThongTinMonAn(final MonAn monAn) {

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                for (DataSnapshot data : dataSnapshot.getChildren()) {

                    if (data.child("maMonAn").getValue(String.class).equalsIgnoreCase(monAn.getMaMonAn())) {
                        maMonAn = data.getKey();
                        Log.d("getkey", "OnCreate: key:" + maMonAn);

                        databaseReference.child(maMonAn).setValue(null).addOnSuccessListener(new OnSuccessListener<Void>() {
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







    public void updateThongTinMonAn(final MonAn monAn) {

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot data : dataSnapshot.getChildren()) {

                    if (data.child("maMonAn").getValue(String.class).equalsIgnoreCase(monAn.getMaMonAn())) {
                        maMonAn = data.getKey();


                        Log.d("Get Key", "onCreate: key" + maMonAn);


                        databaseReference.child(maMonAn).setValue(monAn).addOnSuccessListener(new OnSuccessListener<Void>() {
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
}
