package com.duanmot.myapplication;

import androidx.annotation.NonNull;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import androidx.recyclerview.widget.GridLayoutManager;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;

import android.os.Bundle;

import android.text.TextUtils;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import android.widget.EditText;


import com.duanmot.myapplication.Adapter.Adapter_Icon_Search;
import com.duanmot.myapplication.Adapter.Adapter_MonAn_Show_Home;
import com.duanmot.myapplication.model.MonAn;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import jp.wasabeef.recyclerview.animators.ScaleInAnimator;



public class Search_MonAn_Activity extends AppCompatActivity {


    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReferenceMonAn;
    ArrayList<MonAn> dsMonAn;

    //    GridView gvSearchAllFood;
    RecyclerView rvSearchAllFood, rvIconTitleAllFood;
    Adapter_MonAn_Show_Home adapter_monAn_show_home;
    SearchView searchView;

    String[] listTenSearch = {"Ăn sáng", "Ăn trưa", "Ăn tối" ,"Ăn vặt", "Khai vị",
            "Ăn chay", "Thức uống", "Salad", "Nước chấm", "Spaghetti",
            "Món chính","Nhanh và dễ", "Tốt cho sức khỏe", "Bánh - Bánh ngọt", "Lẩu" ,"Món nhậu"};
    int imgListTenSearch[] = {R.drawable.breakfast, R.drawable.lunch, R.drawable.dinner, R.drawable.snack, R.drawable.khaivi,
            R.drawable.anchay, R.drawable.drink, R.drawable.salad, R.drawable.sauces, R.drawable.spaghetti,
            R.drawable.mainfood, R.drawable.fast_easyfood, R.drawable.healthyfood, R.drawable.cupcake, R.drawable.lau,R.drawable.nhau};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search__mon_an_);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReferenceMonAn = firebaseDatabase.getReference().child("TaoMonAn");

        // RecyclerView Icon Title
        rvIconTitleAllFood = findViewById(R.id.rvIconTitleAllFood);


        // gvSearchAllFood= findViewById(R.id.gvSearchAllFood);
        rvSearchAllFood = findViewById(R.id.rvSearchAllFood);
        rvSearchAllFood.setHasFixedSize(true);
        rvSearchAllFood.setLayoutManager(new GridLayoutManager(Search_MonAn_Activity.this, 2));

        // tạo hiệu ứng
        rvSearchAllFood.setItemAnimator(new ScaleInAnimator());

        dsMonAn = new ArrayList<>();

        iconSearchFood();

        allFoods();
    }

    private void iconSearchFood() {
        rvIconTitleAllFood.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(Search_MonAn_Activity.this, LinearLayoutManager.HORIZONTAL, false);
        rvIconTitleAllFood.setLayoutManager(layoutManager);
        rvIconTitleAllFood.setItemAnimator(new ScaleInAnimator());

        Adapter_Icon_Search adapter_icon_search = new Adapter_Icon_Search(Search_MonAn_Activity.this, imgListTenSearch, listTenSearch);
        rvIconTitleAllFood.setAdapter(new ScaleInAnimationAdapter(adapter_icon_search));

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

                            searchFoods(s);
                        } else {

                            allFoods();
                        }

                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String s) {

                        if (!TextUtils.isEmpty(s.trim())) {

                            searchFoods(s);
                        } else {

                            allFoods();
                        }

                        return false;
                    }
                });
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    private void searchFoods(final String s) {

        databaseReferenceMonAn.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                dsMonAn.clear();

                for (DataSnapshot dataMonAn : dataSnapshot.getChildren()) {
                    MonAn ma = dataMonAn.getValue(MonAn.class);


                    if (ma.getTenMonAn().toLowerCase().contains(s.toLowerCase())) {
                        dsMonAn.add(ma);
                    }

                    adapter_monAn_show_home = new Adapter_MonAn_Show_Home(Search_MonAn_Activity.this, dsMonAn);

                    adapter_monAn_show_home.notifyDataSetChanged();

                    rvSearchAllFood.setAdapter(new ScaleInAnimationAdapter(adapter_monAn_show_home));
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    private void allFoods() {
        databaseReferenceMonAn.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dsMonAn.clear();

                for (DataSnapshot dataMonAn : dataSnapshot.getChildren()) {
                    MonAn ma = dataMonAn.getValue(MonAn.class);

                    dsMonAn.add(ma);

                    adapter_monAn_show_home = new Adapter_MonAn_Show_Home(Search_MonAn_Activity.this, dsMonAn);
                    rvSearchAllFood.setAdapter(new ScaleInAnimationAdapter(adapter_monAn_show_home));
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

}
