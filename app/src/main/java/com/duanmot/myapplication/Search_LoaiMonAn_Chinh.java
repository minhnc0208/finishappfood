package com.duanmot.myapplication;


import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;

import androidx.recyclerview.widget.RecyclerView;

import com.duanmot.myapplication.Adapter.Adapter_MonAn_Show_Home;
import com.duanmot.myapplication.model.MonAn;
import com.duanmot.myapplication.model.PhanLoaiMonAn;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;

public class Search_LoaiMonAn_Chinh extends AppCompatActivity {

    RecyclerView rvSearchLoaiMonAn;

    Adapter_MonAn_Show_Home adapter_monAn_show_home;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReferenceMonAn;
    DatabaseReference databaseReferenceLoaiMonAn;

    ArrayList<MonAn> dsMonAnChinh;

SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search__loai_mon_an);


        rvSearchLoaiMonAn = findViewById(R.id.rvSearchLoaiMonAn);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReferenceMonAn = firebaseDatabase.getReference().child("TaoMonAn");
        databaseReferenceLoaiMonAn = firebaseDatabase.getReference("PhanLoaiMonAn");

        dsMonAnChinh = new ArrayList<>();

        rvSearchLoaiMonAn.setLayoutManager(new GridLayoutManager(Search_LoaiMonAn_Chinh.this, 2));

        rvmonchinh();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search, menu);

        MenuItem item = menu.findItem(R.id.menu_search);

        searchView = (SearchView) item.getActionView();
        searchView.setQueryHint("Vui lòng nhập tháng cần tìm…");
        EditText txtSearch = ((EditText) searchView.findViewById(androidx.appcompat.R.id.search_src_text));
        txtSearch.setHint(getResources().getString(R.string.hint_search));
        txtSearch.setHintTextColor(Color.rgb(250, 250, 250));
        txtSearch.setTextColor(Color.BLACK);
        searchView.setBackgroundColor(000);

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_search:
                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String s) {

                        if (!TextUtils.isEmpty(s.trim())) {

                            searchMonAnChinh(s);
                        } else {

                            rvmonchinh();
                        }

                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String s) {

                        if (!TextUtils.isEmpty(s.trim())) {

                            searchMonAnChinh(s);
                        } else {

                            rvmonchinh();
                        }

                        return false;
                    }
                });
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    private void searchMonAnChinh(final String s) {


        databaseReferenceMonAn.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                dsMonAnChinh.clear();
                for (final DataSnapshot dataSnapshotMonAn : dataSnapshot.getChildren()) {
                    databaseReferenceLoaiMonAn.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            for (DataSnapshot dataSnapshotLoaiMonAn : dataSnapshot.getChildren()) {
                                MonAn monAn = dataSnapshotMonAn.getValue(MonAn.class);

                                PhanLoaiMonAn phanLoaiMonAn = dataSnapshotLoaiMonAn.getValue(PhanLoaiMonAn.class);

                                if (monAn.getMaMonAn().equalsIgnoreCase(phanLoaiMonAn.getMaMonAn())) {
                                    if (phanLoaiMonAn.getThucDon().equalsIgnoreCase("Món chính")) {

                                        if (monAn.getTenMonAn().toLowerCase().contains(s.toLowerCase())) {
                                            dsMonAnChinh.add(monAn);
                                        }

                                    }

                                    adapter_monAn_show_home = new Adapter_MonAn_Show_Home(Search_LoaiMonAn_Chinh.this, dsMonAnChinh);
                                    adapter_monAn_show_home.notifyDataSetChanged();
                                    rvSearchLoaiMonAn.setAdapter(new ScaleInAnimationAdapter(adapter_monAn_show_home));

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

    private void rvmonchinh() {

        databaseReferenceMonAn.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                dsMonAnChinh.clear();
                for (final DataSnapshot dataSnapshotMonAn : dataSnapshot.getChildren()) {
                    databaseReferenceLoaiMonAn.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot dataSnapshotLoaiMonAn : dataSnapshot.getChildren()) {
                                MonAn monAn = dataSnapshotMonAn.getValue(MonAn.class);

                                PhanLoaiMonAn phanLoaiMonAn = dataSnapshotLoaiMonAn.getValue(PhanLoaiMonAn.class);

                                if (monAn.getMaMonAn().equalsIgnoreCase(phanLoaiMonAn.getMaMonAn())) {
                                    if (phanLoaiMonAn.getThucDon().equalsIgnoreCase("Món chính")) {

                                      dsMonAnChinh.add(monAn);

                                    }

                                    adapter_monAn_show_home = new Adapter_MonAn_Show_Home(Search_LoaiMonAn_Chinh.this, dsMonAnChinh);
                                    adapter_monAn_show_home.notifyDataSetChanged();
                                    rvSearchLoaiMonAn.setAdapter(adapter_monAn_show_home);
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
