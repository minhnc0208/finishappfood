package com.duanmot.myapplication;

import android.os.Bundle;

import android.view.MenuItem;

import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;

import androidx.recyclerview.widget.RecyclerView;

import com.duanmot.myapplication.Adapter.Adapter_MonAn_Show_Home;

import com.duanmot.myapplication.model.MonAn;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


import de.hdodenhof.circleimageview.CircleImageView;

import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;

import jp.wasabeef.recyclerview.animators.ScaleInAnimator;


public class ListMonAn_YeuThich_Activity extends AppCompatActivity {

    ArrayList<MonAn> listMonAn;
    Adapter_MonAn_Show_Home adapter_monAn_show_home;

    private RecyclerView mRecyclerViewList;

    CircleImageView img_user_list;
    TextView tv_user_name_list, tv_user_email_list;

    Toolbar toolbar;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReferenceMonAn;
    DatabaseReference databaseReferenceUser;
    DatabaseReference databaseReferenceLikes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_mon_an);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mRecyclerViewList = findViewById(R.id.recycleviewList);

        tv_user_name_list = findViewById(R.id.tv_user_name_list);
        tv_user_email_list = findViewById(R.id.tv_user_email_list);
        img_user_list = findViewById(R.id.img_user_list);

        listMonAn = new ArrayList<>();

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReferenceMonAn = firebaseDatabase.getReference().child("TaoMonAn");
        databaseReferenceUser = firebaseDatabase.getReference().child("Users");
        databaseReferenceLikes = firebaseDatabase.getReference().child("Likes");

        Query query = databaseReferenceUser.orderByChild("email").equalTo(firebaseUser.getEmail());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot dataUsers : dataSnapshot.getChildren()) {

                    String name = "" + dataUsers.child("name").getValue();
                    String email = "" + dataUsers.child("email").getValue();
                    String image = "" + dataUsers.child("image").getValue();

                    tv_user_name_list.setText(name);
                    tv_user_email_list.setText(email);

                    try {
                        // if image is received then set
                        Picasso.get().load(image).into(img_user_list);
                    } catch (Exception e) {
                        // if there is any exception while getting image then set default
                        Picasso.get().load(R.drawable.img_chef).into(img_user_list);
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        mRecyclerViewList.setHasFixedSize(true);
        mRecyclerViewList.setLayoutManager(new GridLayoutManager(ListMonAn_YeuThich_Activity.this, 2));
        mRecyclerViewList.setItemAnimator(new ScaleInAnimator());

        allData();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
        } else {
//            startActivity(new Intent(ListMonAn_ThucHien_Activity.this,MonAnDetail.class));
        }
        return super.onOptionsItemSelected(item);
    }

    public void allData() {

        databaseReferenceMonAn.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                for (final DataSnapshot dataMonAn : dataSnapshot.getChildren()) {

                    final MonAn monAn1 = dataMonAn.getValue(MonAn.class);
                    final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

                    databaseReferenceLikes = FirebaseDatabase.getInstance().getReference().child("Likes").child(monAn1.getMaMonAn());

                    databaseReferenceLikes.addValueEventListener(new ValueEventListener() {

                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            if ((dataSnapshot.child(firebaseUser.getUid()).exists())) {

                                listMonAn.add(monAn1);
                            }
                            adapter_monAn_show_home = new Adapter_MonAn_Show_Home(ListMonAn_YeuThich_Activity.this, listMonAn);
                            adapter_monAn_show_home.notifyDataSetChanged();

                            mRecyclerViewList.setAdapter(new ScaleInAnimationAdapter(adapter_monAn_show_home));

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
