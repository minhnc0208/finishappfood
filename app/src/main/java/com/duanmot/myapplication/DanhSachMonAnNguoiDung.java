package com.duanmot.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.duanmot.myapplication.Adapter.Adapter_DSMonAnNguoiDung;
import com.duanmot.myapplication.Database.ThongTinMonAnDao;
import com.duanmot.myapplication.model.MonAn;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DanhSachMonAnNguoiDung extends AppCompatActivity {

    ListView lvMonAnNguoiDung;
    public static Adapter_DSMonAnNguoiDung adapter_dsMonAnNguoiDung;
    ArrayList<MonAn> dsMonAn;
    Button btnTaoMonAn;
    ImageView imgBackDSMAND;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReferenceMonAn;
    FirebaseAuth firebaseAuth;

    ArrayList<MonAn> listMonAnNguoiDung = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_sach_mon_an_nguoi_dung);

        lvMonAnNguoiDung = findViewById(R.id.lvMonAnNguoiDung);
        btnTaoMonAn = findViewById(R.id.btnTaoMonAn);

        imgBackDSMAND = findViewById(R.id.imgBackDSMAND);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReferenceMonAn = firebaseDatabase.getReference().child("TaoMonAn");
        firebaseAuth = FirebaseAuth.getInstance();

        dsListMonAnNguoiDung();

        imgBackDSMAND.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();


            }
        });

        btnTaoMonAn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DanhSachMonAnNguoiDung.this, TaoMonAn.class);
                startActivity(intent);
            }
        });

    }


    private void dsListMonAnNguoiDung() {

        databaseReferenceMonAn.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                listMonAnNguoiDung.clear();
                for (DataSnapshot dataMonAn : dataSnapshot.getChildren()) {
                    MonAn monAn1 = dataMonAn.getValue(MonAn.class);
                    String maNguoiDungMonAn = monAn1.getMaNguoiDung();

                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    String maNguoiDung = user.getUid();
                    if (maNguoiDung.equals(maNguoiDungMonAn)) {

                        listMonAnNguoiDung.add(monAn1);

                        adapter_dsMonAnNguoiDung = new Adapter_DSMonAnNguoiDung(DanhSachMonAnNguoiDung.this, listMonAnNguoiDung);
                        adapter_dsMonAnNguoiDung.notifyDataSetChanged();
                        lvMonAnNguoiDung.setAdapter(adapter_dsMonAnNguoiDung);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    public static void udateListViewMonAnNguoiDung() {
        adapter_dsMonAnNguoiDung.notifyDataSetChanged();
    }


    private void dsListView() {
        ThongTinMonAnDao monAnDao = new ThongTinMonAnDao(DanhSachMonAnNguoiDung.this);
        dsMonAn = monAnDao.getDSMonAn();
        adapter_dsMonAnNguoiDung = new Adapter_DSMonAnNguoiDung(DanhSachMonAnNguoiDung.this, dsMonAn);
        lvMonAnNguoiDung.setAdapter(adapter_dsMonAnNguoiDung);
    }


}
