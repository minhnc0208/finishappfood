package com.duanmot.myapplication;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.duanmot.myapplication.Adapter.Adapter_SpinnerSuaThucDon;

import com.duanmot.myapplication.model.MonAn;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


public class SuaPhanLoaiMonAnFragment extends Fragment {

    Spinner spnSuaThucDon;
    Spinner spnSuaLMA;
    Spinner spnSuaCTH;
    Spinner spnSuaDipMua;
    Spinner spnSuaMucDich;

    Button btnSuaPhanLoai;

    String[] listThucDon = {"Món khai vị", "Món chay", "Món ăn sáng", "Thức uống", "Món ăn cho trẻ",
            "Món tráng miệng", "Món chính", "Nhanh và dễ", "Bánh - Bánh ngọt", "Món nhậu"};
    int imgListThucDon[] = {R.drawable.thuc_don3, R.drawable.thuc_don3, R.drawable.thuc_don3, R.drawable.thuc_don3, R.drawable.thuc_don3,
            R.drawable.thuc_don3, R.drawable.thuc_don3, R.drawable.thuc_don3, R.drawable.thuc_don3, R.drawable.thuc_don3};


    String[] listLoaiMon = {"Salad", "Canh", "Nộm - Gỏi", "Nem - Chả", "Xôi", "Bánh ngọt",
            "Cocktail - Mocktail", "Chè", "Đồ sống", "Cupcake - Muffin", "Miến - Hủ tiếu",
            "Đồ uống", "Nghêu - Sò - Ốc", "Món chiên", "Chưng - hấp", "Kho - Rim", "Món luộc", "Sữa chua",
            "Nước chấm - Sốt", "Lẩu", "Soup - Cháo", "Chay", "Bánh mặn", "Sinh tố - Nước ép", "Kem",
            "Mứt - Kẹo", "Snacks", "Pasta - Spaghetti", "Bún - Mì - Phở", "Nướng - Quay", "Rang - Xào",
            "Món cuốn", "Muối chua - Ngâm chua", "Ủ - Lên men", " Thạch - Rau câu"};

    int imgListLoaiMon[] = {R.drawable.loai_mon, R.drawable.loai_mon, R.drawable.loai_mon, R.drawable.loai_mon, R.drawable.loai_mon,
            R.drawable.loai_mon, R.drawable.loai_mon, R.drawable.loai_mon, R.drawable.loai_mon, R.drawable.loai_mon,
            R.drawable.loai_mon, R.drawable.loai_mon, R.drawable.loai_mon, R.drawable.loai_mon, R.drawable.loai_mon,
            R.drawable.loai_mon, R.drawable.loai_mon, R.drawable.loai_mon, R.drawable.loai_mon, R.drawable.loai_mon,
            R.drawable.loai_mon, R.drawable.loai_mon, R.drawable.loai_mon, R.drawable.loai_mon, R.drawable.loai_mon,
            R.drawable.loai_mon, R.drawable.loai_mon, R.drawable.loai_mon, R.drawable.loai_mon, R.drawable.loai_mon,
            R.drawable.loai_mon, R.drawable.loai_mon, R.drawable.loai_mon, R.drawable.loai_mon, R.drawable.loai_mon};

    String[] listCachThucHien = {"Nướng", "Lẩu", "Hầm", "Tiềm", "Trộn", "Ép", "Ngâm", "Sốt", "Muối", "Ủ", "Quay", "Chưng", "Ram",
            "Chiên", "Luộc", "Hấp", "Xào", "Xay", "Kho", "Om", "Nấu", "Vắt", "Cuốn", "Rang", "Ướp"};

    int imgListCachThucHien[] = {R.drawable.thuc_hien_monan, R.drawable.thuc_hien_monan, R.drawable.thuc_hien_monan, R.drawable.thuc_hien_monan,
            R.drawable.thuc_hien_monan,
            R.drawable.thuc_hien_monan, R.drawable.thuc_hien_monan, R.drawable.thuc_hien_monan, R.drawable.thuc_hien_monan, R.drawable.thuc_hien_monan,
            R.drawable.thuc_hien_monan, R.drawable.thuc_hien_monan, R.drawable.thuc_hien_monan, R.drawable.thuc_hien_monan, R.drawable.thuc_hien_monan,
            R.drawable.thuc_hien_monan, R.drawable.thuc_hien_monan, R.drawable.thuc_hien_monan, R.drawable.thuc_hien_monan, R.drawable.thuc_hien_monan,
            R.drawable.thuc_hien_monan, R.drawable.thuc_hien_monan, R.drawable.thuc_hien_monan, R.drawable.thuc_hien_monan, R.drawable.thuc_hien_monan};


    String[] listDipMua = {"Mùa Xuân", "Mùa Thu", "Mùa Hè", "Mùa Đông"};
    int imgListDipMua[] = {R.drawable.mua_xuan, R.drawable.mua_ha, R.drawable.mua_thu, R.drawable.mua_dong};


    String[] listMucDich = {"Ăn sáng", "Ăn kiêng", "Cho phái mạnh", "Tiệc", "Chữa bệnh", "Phụ nữ sau khi sinh", "Trẻ dưới 1 tuổi", "Ăn tối",
            "Tốt cho tim mạch", "Tăng cân",
            "Ăn trưa", "Giảm cân", "Ăn vặt", "Ăn chay", "Ăn gia đình", "Phụ nữ mang thai", "Tốt cho sức khỏe", "Tốt cho trẻ em", "Cho phái nữ"};
    int imgListMucDich[] = {R.drawable.muc_dich, R.drawable.muc_dich, R.drawable.muc_dich, R.drawable.muc_dich, R.drawable.muc_dich, R.drawable.muc_dich,
            R.drawable.muc_dich, R.drawable.muc_dich, R.drawable.muc_dich, R.drawable.muc_dich, R.drawable.muc_dich,
            R.drawable.muc_dich, R.drawable.muc_dich, R.drawable.muc_dich, R.drawable.muc_dich, R.drawable.muc_dich,
            R.drawable.muc_dich, R.drawable.muc_dich, R.drawable.muc_dich};

    Adapter_SpinnerSuaThucDon spinnerSuaThucDon;

    String viTriTenThucDon;
    String viTriTenLoaiMonAn;
    String viTriTenCachThucHien;
    String viTriTenDipMua;
    String viTriTenMucDich;
    String maMonAn;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReferenceMonAn;
    DatabaseReference databaseReferencePhanLoaiMonAn;

    String thucDon;

    String maPhanLoai;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_sua_phan_loai_mon_an, container, false);

        spnSuaThucDon = view.findViewById(R.id.spnSuaThucDon);
        spnSuaLMA = view.findViewById(R.id.spnSuaLMA);
        spnSuaCTH = view.findViewById(R.id.spnSuaCTH);
        spnSuaDipMua = view.findViewById(R.id.spnSuaDipMua);
        spnSuaMucDich = view.findViewById(R.id.spnSuaMucDich);
        btnSuaPhanLoai = view.findViewById(R.id.btnSuaPhanLoai);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReferenceMonAn = firebaseDatabase.getReference("TaoMonAn");
        databaseReferencePhanLoaiMonAn = firebaseDatabase.getReference("PhanLoaiMonAn");

        spinnerThucDon();
        spinnerLoaiMonAn();
        spinnerCachThucHien();
        spinnerDipMua();
        spinnerMucDich();

        databaseReferenceMonAn.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    MonAn monAn = dataSnapshot1.getValue(MonAn.class);
                    maMonAn = monAn.getMaMonAn();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        suaThongTinPhanLoai();

        capNhatPhanLoai();



        return view;
    }

    private void capNhatPhanLoai() {
        btnSuaPhanLoai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Query queryPhanLoai = databaseReferencePhanLoaiMonAn.orderByChild("mPLMA").equalTo(maPhanLoai);
                queryPhanLoai.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        for (DataSnapshot dataPhanLoai : dataSnapshot.getChildren()) {
                            dataPhanLoai.getRef().child("thucDon").setValue(viTriTenThucDon);
                            dataPhanLoai.getRef().child("loaiMon").setValue(viTriTenLoaiMonAn);
                            dataPhanLoai.getRef().child("cachThucHien").setValue(viTriTenCachThucHien);
                            dataPhanLoai.getRef().child("mua").setValue(viTriTenDipMua);
                            dataPhanLoai.getRef().child("mucDich").setValue(viTriTenMucDich);

                            Toast.makeText(getActivity(), "Cập nhật phân loại thành công", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });

    }

    private void suaThongTinPhanLoai() {

        Intent intent = getActivity().getIntent();

        maPhanLoai = intent.getStringExtra("maPhanLoaiMonAn");

        thucDon = intent.getStringExtra("thucDon");
        String loaiMonAn = intent.getStringExtra("loaiMonAn");
        String cachThucHien = intent.getStringExtra("cachThucHien");
        String dipMua = intent.getStringExtra("dipMua");
        String mucDich = intent.getStringExtra("mucDich");

        for (int j = 0; j < spnSuaThucDon.getCount(); j++) {
            if (listThucDon[j].equals(thucDon)) {
                spnSuaThucDon.setSelection(j);
            }
        }

        for (int k = 0; k < spnSuaLMA.getCount(); k++) {
            if (listLoaiMon[k].equals(loaiMonAn)) {
                spnSuaLMA.setSelection(k);
            }
        }

        for (int l = 0; l < spnSuaCTH.getCount(); l++) {
            if (listCachThucHien[l].equals(cachThucHien)) {
                spnSuaCTH.setSelection(l);
            }
        }

        for (int i = 0; i < spnSuaDipMua.getCount(); i++) {
            if (listDipMua[i].equals(dipMua)) {
                spnSuaDipMua.setSelection(i);
            }
        }

        for (int i = 0; i < spnSuaMucDich.getCount(); i++) {
            if (listMucDich[i].equals(mucDich)) {
                spnSuaMucDich.setSelection(i);
            }
        }




    }


    private void spinnerMucDich() {
        spinnerSuaThucDon = new Adapter_SpinnerSuaThucDon(getContext(), imgListMucDich, listMucDich);
        spnSuaMucDich.setAdapter(spinnerSuaThucDon);

        spnSuaMucDich.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                viTriTenMucDich = listMucDich[i];

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void spinnerDipMua() {
        spinnerSuaThucDon = new Adapter_SpinnerSuaThucDon(getContext(), imgListDipMua, listDipMua);
        spnSuaDipMua.setAdapter(spinnerSuaThucDon);

        spnSuaDipMua.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                viTriTenDipMua = listDipMua[i];

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void spinnerCachThucHien() {
        spinnerSuaThucDon = new Adapter_SpinnerSuaThucDon(getContext(), imgListCachThucHien, listCachThucHien);
        spnSuaCTH.setAdapter(spinnerSuaThucDon);

        spnSuaCTH.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                viTriTenCachThucHien = listCachThucHien[i];

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    private void spinnerLoaiMonAn() {
        spinnerSuaThucDon = new Adapter_SpinnerSuaThucDon(getContext(), imgListLoaiMon, listLoaiMon);
        spnSuaLMA.setAdapter(spinnerSuaThucDon);

        spnSuaLMA.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                viTriTenLoaiMonAn = listLoaiMon[i];

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    private void spinnerThucDon() {

        spinnerSuaThucDon = new Adapter_SpinnerSuaThucDon(getContext(), imgListThucDon, listThucDon);
        spnSuaThucDon.setAdapter(spinnerSuaThucDon);

        spnSuaThucDon.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                viTriTenThucDon = listThucDon[i];

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }
}
