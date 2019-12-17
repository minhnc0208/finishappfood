package com.duanmot.myapplication;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.duanmot.myapplication.Adapter.Adapter_SpinnerThucDon;
import com.duanmot.myapplication.Database.PhanLoaiMonAnDao;
import com.duanmot.myapplication.model.MonAn;
import com.duanmot.myapplication.model.PhanLoaiMonAn;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class PhanLoaiMonAnFragment extends Fragment {

    Spinner spnThucDon;
    Spinner spnLMA;
    Spinner spnCTH;
    Spinner spnDipMua;
    Spinner spnMucDich;

    Button btnSavePhanLoai;

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
            R.drawable.thuc_hien_monan, R.drawable.thuc_hien_monan, R.drawable.thuc_hien_monan, R.drawable.thuc_hien_monan, R.drawable.thuc_hien_monan,
            R.drawable.thuc_hien_monan, R.drawable.thuc_hien_monan, R.drawable.thuc_hien_monan, R.drawable.thuc_hien_monan, R.drawable.thuc_hien_monan,
            R.drawable.thuc_hien_monan, R.drawable.thuc_hien_monan, R.drawable.thuc_hien_monan, R.drawable.thuc_hien_monan, R.drawable.thuc_hien_monan,
            R.drawable.thuc_hien_monan, R.drawable.thuc_hien_monan, R.drawable.thuc_hien_monan, R.drawable.thuc_hien_monan, R.drawable.thuc_hien_monan,
            R.drawable.thuc_hien_monan};


    String[] listDipMua = {"Mùa Xuân", "Mùa Thu", "Mùa Hè", "Mùa Đông"};
    int imgListDipMua[] = {R.drawable.mua_xuan, R.drawable.mua_ha, R.drawable.mua_thu, R.drawable.mua_dong};


    String[] listMucDich = {"Ăn sáng", "Ăn kiêng", "Cho phái mạnh", "Tiệc", "Chữa bệnh", "Phụ nữ sau khi sinh", "Trẻ dưới 1 tuổi", "Ăn tối", "Tốt cho tim mạch",
            "Tăng cân", "Ăn trưa", "Giảm cân", "Ăn vặt", "Ăn chay", "Ăn gia đình", "Phụ nữ mang thai", "Tốt cho sức khỏe", "Tốt cho trẻ em", "Cho phái nữ"};
    int imgListMucDich[] = {R.drawable.muc_dich, R.drawable.muc_dich, R.drawable.muc_dich, R.drawable.muc_dich, R.drawable.muc_dich, R.drawable.muc_dich,
            R.drawable.muc_dich, R.drawable.muc_dich, R.drawable.muc_dich, R.drawable.muc_dich, R.drawable.muc_dich,
            R.drawable.muc_dich, R.drawable.muc_dich, R.drawable.muc_dich, R.drawable.muc_dich, R.drawable.muc_dich,
            R.drawable.muc_dich, R.drawable.muc_dich, R.drawable.muc_dich};

    Adapter_SpinnerThucDon adapter_spinnerThucDon;

    String viTriTenThucDon;
    String viTriTenLoaiMonAn;
    String viTriTenCachThucHien;
    String viTriTenDipMua;
    String viTriTenMucDich;
    String maMonAn;

    String maPhanLoaiMonAn;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReferenceMonAn;
    DatabaseReference databaseReferencePhanLoaiMonAn;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_phan_loai_mon_an, container, false);

        spnThucDon = view.findViewById(R.id.spnThucDon);
        spnLMA = view.findViewById(R.id.spnLMA);
        spnCTH = view.findViewById(R.id.spnCTH);
        spnDipMua = view.findViewById(R.id.spnDipMua);
        spnMucDich = view.findViewById(R.id.spnMucDich);
        btnSavePhanLoai = view.findViewById(R.id.btnSavePhanLoai);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReferenceMonAn = firebaseDatabase.getReference("TaoMonAn");
        databaseReferencePhanLoaiMonAn = firebaseDatabase.getReference("PhanLoaiMonAn");

        spinnerThucDon();
        spinnerLoaiMonAn();
        spinnerCachThucHien();
        spinnerDipMua();
        spinnerMucDich();

        luuPhanLoai();

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


        return view;
    }

    private void luuPhanLoai() {

        btnSavePhanLoai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                maPhanLoaiMonAn = databaseReferencePhanLoaiMonAn.push().getKey();
                PhanLoaiMonAn phanLoaiMonAn = new PhanLoaiMonAn(maPhanLoaiMonAn, viTriTenThucDon, viTriTenLoaiMonAn,
                        viTriTenCachThucHien, viTriTenDipMua, viTriTenMucDich, maMonAn);

                PhanLoaiMonAnDao phanLoaiMonAnDao = new PhanLoaiMonAnDao(getContext());
                phanLoaiMonAnDao.insert(phanLoaiMonAn);

                Toast.makeText(getContext(), "Thêm Thành Công", Toast.LENGTH_SHORT).show();

                getActivity().finish();
                Intent intent = new Intent(getActivity(), DanhSachMonAnNguoiDung.class);
                startActivity(intent);

            }
        });
    }

    private void spinnerMucDich() {
        adapter_spinnerThucDon = new Adapter_SpinnerThucDon(getContext(), imgListMucDich, listMucDich);
        spnMucDich.setAdapter(adapter_spinnerThucDon);


        spnMucDich.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
        adapter_spinnerThucDon = new Adapter_SpinnerThucDon(getContext(), imgListDipMua, listDipMua);
        spnDipMua.setAdapter(adapter_spinnerThucDon);

        spnDipMua.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
        adapter_spinnerThucDon = new Adapter_SpinnerThucDon(getContext(), imgListCachThucHien, listCachThucHien);
        spnCTH.setAdapter(adapter_spinnerThucDon);

        spnCTH.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
        adapter_spinnerThucDon = new Adapter_SpinnerThucDon(getContext(), imgListLoaiMon, listLoaiMon);
        spnLMA.setAdapter(adapter_spinnerThucDon);

        spnLMA.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

        adapter_spinnerThucDon = new Adapter_SpinnerThucDon(getContext(), imgListThucDon, listThucDon);
        spnThucDon.setAdapter(adapter_spinnerThucDon);

        spnThucDon.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
