package com.duanmot.myapplication;

import android.content.Intent;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.duanmot.myapplication.Adapter.Adapter_MonAn_Likes_Show_Home;
import com.duanmot.myapplication.Adapter.Adapter_MonAn_Show_Home;
import com.duanmot.myapplication.Adapter.Adapter_Videos_MonAn_Show_Home;
import com.duanmot.myapplication.Database.ThongTinMonAnDao;
import com.duanmot.myapplication.model.MonAn;
import com.duanmot.myapplication.model.PhanLoaiMonAn;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import jp.wasabeef.recyclerview.animators.ScaleInAnimator;


public class HomeFragmentActivity extends Fragment {

    RecyclerView rvMonAnYeuThich, recycleviewTotChoSucKhoe, rvTatCaMonAn, recycleviewLamDep, recycleviewChoTre, recycleviewVideosMoAn;
    public static Adapter_MonAn_Show_Home adapter_monAn_show_home;

    Adapter_Videos_MonAn_Show_Home adapter_videos_monAn_show_home;
    Adapter_MonAn_Likes_Show_Home adapter_monAn_likes_show_home;

    TextView tvHintSearch;
    ArrayList<MonAn> dsAllMonAn;
    ArrayList<MonAn> listMonAnYeuThich;
    ArrayList<MonAn> listMonAnTotSucKhoe;
    ArrayList<MonAn> listMonAnChoLamDep;
    ArrayList<MonAn> listMonAnChoTre;
    ArrayList<MonAn> listVideosMonAn;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReferenceMonAn;
    DatabaseReference databaseReferenceLoaiMonAn;
    DatabaseReference databaseReferenceLikes;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, container, false);

        recycleviewTotChoSucKhoe = view.findViewById(R.id.recycleviewTotChoSucKhoe);
        rvMonAnYeuThich = view.findViewById(R.id.rvMonAnYeuThich);
        rvTatCaMonAn = view.findViewById(R.id.rvTatCaMonAn);
        recycleviewLamDep = view.findViewById(R.id.recycleviewLamDep);
        recycleviewChoTre = view.findViewById(R.id.recycleviewChoTre);
        recycleviewVideosMoAn = view.findViewById(R.id.recycleviewVideosMoAn);

        tvHintSearch = view.findViewById(R.id.tvHintSearch);

        dsAllMonAn = new ArrayList<>();
        listMonAnYeuThich = new ArrayList<>();
        listMonAnTotSucKhoe = new ArrayList<>();
        listMonAnChoLamDep = new ArrayList<>();
        listMonAnChoTre = new ArrayList<>();
        listVideosMonAn = new ArrayList<>();

        tvHintSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Search_MonAn_Activity.class);
                startActivity(intent);
            }
        });

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReferenceMonAn = firebaseDatabase.getReference("TaoMonAn");
        databaseReferenceLoaiMonAn = firebaseDatabase.getReference("PhanLoaiMonAn");
        databaseReferenceLikes = firebaseDatabase.getReference("Likes");

        recyclerviewAllMonAnYeuThich();
        recycleviewVideosMonAn();
        recycleviewMonAnLamDep();
        recycleviewMonAnChoTre();
        recyclerviewMonAnYeuThich();
        recyclerviewMonAnTotChoSucKhoe();
        recyclerviewTatCaMonAn();
        return view;
    }

    private void recycleviewVideosMonAn() {
        recycleviewVideosMoAn.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recycleviewVideosMoAn.setLayoutManager(layoutManager);
        recycleviewVideosMoAn.setItemAnimator(new ScaleInAnimator());
        databaseReferenceMonAn.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (final DataSnapshot dataSnapshotMonAn : dataSnapshot.getChildren()) {

                    MonAn monAn = dataSnapshotMonAn.getValue(MonAn.class);

                    if (!monAn.getVideoMonAn().isEmpty()) {
                        listVideosMonAn.add(monAn);

                        adapter_videos_monAn_show_home = new Adapter_Videos_MonAn_Show_Home(getContext(), listVideosMonAn);
                        adapter_videos_monAn_show_home.notifyDataSetChanged();
                        recycleviewVideosMoAn.setAdapter(new ScaleInAnimationAdapter(adapter_videos_monAn_show_home));
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void recycleviewMonAnChoTre() {
        recycleviewChoTre.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recycleviewChoTre.setLayoutManager(layoutManager);
        recycleviewChoTre.setItemAnimator(new ScaleInAnimator());
        databaseReferenceMonAn.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (final DataSnapshot dataSnapshotMonAn : dataSnapshot.getChildren()) {
                    databaseReferenceLoaiMonAn.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot dataSnapshotLoaiMonAn : dataSnapshot.getChildren()) {
                                MonAn monAn = dataSnapshotMonAn.getValue(MonAn.class);

                                PhanLoaiMonAn phanLoaiMonAn = dataSnapshotLoaiMonAn.getValue(PhanLoaiMonAn.class);

                                if (monAn.getMaMonAn().equalsIgnoreCase(phanLoaiMonAn.getMaMonAn())) {
                                    if (phanLoaiMonAn.getThucDon().equalsIgnoreCase("Món ăn cho trẻ")) {
                                        listMonAnChoTre.add(monAn);
                                        adapter_monAn_show_home = new Adapter_MonAn_Show_Home(getContext(), listMonAnChoTre);
                                        adapter_monAn_show_home.notifyDataSetChanged();
                                        recycleviewChoTre.setAdapter(new ScaleInAnimationAdapter(adapter_monAn_show_home));
                                    }
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

    private void recycleviewMonAnLamDep() {
        recycleviewLamDep.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recycleviewLamDep.setLayoutManager(layoutManager);
        recycleviewLamDep.setItemAnimator(new ScaleInAnimator());
        databaseReferenceMonAn.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (final DataSnapshot dataSnapshotMonAn : dataSnapshot.getChildren()) {
                    databaseReferenceLoaiMonAn.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot dataSnapshotLoaiMonAn : dataSnapshot.getChildren()) {
                                MonAn monAn = dataSnapshotMonAn.getValue(MonAn.class);

                                PhanLoaiMonAn phanLoaiMonAn = dataSnapshotLoaiMonAn.getValue(PhanLoaiMonAn.class);

                                if (monAn.getMaMonAn().equalsIgnoreCase(phanLoaiMonAn.getMaMonAn())) {
                                    if (phanLoaiMonAn.getMucDich().equalsIgnoreCase("Cho phái nữ")) {
                                        listMonAnChoLamDep.add(monAn);
                                        adapter_monAn_show_home = new Adapter_MonAn_Show_Home(getContext(), listMonAnChoLamDep);
                                        adapter_monAn_show_home.notifyDataSetChanged();
                                        recycleviewLamDep.setAdapter(new ScaleInAnimationAdapter(adapter_monAn_show_home));
                                    }
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

    private void recyclerviewMonAnTotChoSucKhoe() {
        recycleviewTotChoSucKhoe.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recycleviewTotChoSucKhoe.setLayoutManager(layoutManager);
        recycleviewTotChoSucKhoe.setItemAnimator(new ScaleInAnimator());
        databaseReferenceMonAn.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (final DataSnapshot dataSnapshotMonAn : dataSnapshot.getChildren()) {
                    databaseReferenceLoaiMonAn.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot dataSnapshotLoaiMonAn : dataSnapshot.getChildren()) {
                                MonAn monAn = dataSnapshotMonAn.getValue(MonAn.class);

                                PhanLoaiMonAn phanLoaiMonAn = dataSnapshotLoaiMonAn.getValue(PhanLoaiMonAn.class);

                                if (monAn.getMaMonAn().equalsIgnoreCase(phanLoaiMonAn.getMaMonAn())) {
                                    if (phanLoaiMonAn.getMucDich().equalsIgnoreCase("Tốt cho sức khỏe")) {
                                        listMonAnTotSucKhoe.add(monAn);
                                        adapter_monAn_show_home = new Adapter_MonAn_Show_Home(getContext(), listMonAnTotSucKhoe);
                                        adapter_monAn_show_home.notifyDataSetChanged();
                                        recycleviewTotChoSucKhoe.setAdapter(new ScaleInAnimationAdapter(adapter_monAn_show_home));
                                    }
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

    private void recyclerviewMonAnYeuThich() {
        rvMonAnYeuThich.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        rvMonAnYeuThich.setLayoutManager(layoutManager);
        rvMonAnYeuThich.setItemAnimator(new ScaleInAnimator());
        databaseReferenceMonAn.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (final DataSnapshot dataMonAn : dataSnapshot.getChildren()) {
                    databaseReferenceLikes.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot dataLikes : dataSnapshot.getChildren()) {
                                MonAn ma = dataMonAn.getValue(MonAn.class);
                                String key = dataLikes.getKey();
                                if (ma.getMaMonAn().equals(key)) {

                                    listMonAnYeuThich.add(ma);
                                }

                                adapter_monAn_likes_show_home = new Adapter_MonAn_Likes_Show_Home(getContext(), listMonAnYeuThich);
                                adapter_monAn_likes_show_home.notifyDataSetChanged();
                                rvMonAnYeuThich.setAdapter(new ScaleInAnimationAdapter(adapter_monAn_likes_show_home));
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

    private void recyclerviewAllMonAnYeuThich() {
        rvMonAnYeuThich.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        rvMonAnYeuThich.setLayoutManager(layoutManager);
        rvMonAnYeuThich.setItemAnimator(new ScaleInAnimator());
        databaseReferenceMonAn.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (final DataSnapshot dataMonAn : dataSnapshot.getChildren()) {
                    databaseReferenceLikes.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            rvMonAnYeuThich.removeAllViews();
                            for (DataSnapshot dataLikes : dataSnapshot.getChildren()) {
                                MonAn ma = dataMonAn.getValue(MonAn.class);
                                String key = dataLikes.getKey();

                                if (ma.getMaMonAn().equals(key)) {
                                    listMonAnYeuThich.clear();
                                }
                                adapter_monAn_likes_show_home = new Adapter_MonAn_Likes_Show_Home(getContext(), listMonAnYeuThich);
                                adapter_monAn_likes_show_home.notifyDataSetChanged();
                                rvMonAnYeuThich.setAdapter(new ScaleInAnimationAdapter(adapter_monAn_likes_show_home));
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

    private void recyclerviewTatCaMonAn() {
        rvTatCaMonAn.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        rvTatCaMonAn.setLayoutManager(layoutManager);
        rvTatCaMonAn.setItemAnimator(new ScaleInAnimator());
        dsAllMonAn = new ArrayList<>();
        ThongTinMonAnDao thongTinMonAnDao = new ThongTinMonAnDao(getContext());
        dsAllMonAn = thongTinMonAnDao.getDSMonAnHome();
        adapter_monAn_show_home = new Adapter_MonAn_Show_Home(getContext(), dsAllMonAn);
        adapter_monAn_show_home.notifyDataSetChanged();

        rvTatCaMonAn.setAdapter(new ScaleInAnimationAdapter(adapter_monAn_show_home));

    }

    public static void udateRecyclerView() {
        adapter_monAn_show_home.notifyDataSetChanged();

    }


}
