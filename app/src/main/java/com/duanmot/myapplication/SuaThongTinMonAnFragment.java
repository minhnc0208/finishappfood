package com.duanmot.myapplication;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.duanmot.myapplication.Adapter.Adapter_SpinnerKhauPhanMonAn;
import com.duanmot.myapplication.Database.ThongTinMonAnDao;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import static android.app.Activity.RESULT_OK;


public class SuaThongTinMonAnFragment extends Fragment {

    ImageView imgSuaAnhMonAn, imgSuaTTLinkYouTube, imgSuaNen;

    EditText edtSuaTenMonAn, edtSuaLinkYouTube, edtSuaMoTaMonAn;

    TextView tvSuaChonTGCB, tvSuaChonTGTH, tvSuaChonDoKho, tvSuaNen1, tvSuaNen2;

    Spinner spnSuaChonKhauPhan;

    public Button btnSuaTTMonAn;

    ArrayList<String> dsKhauPhan;
    int khauPhan = 0;
    String viTriKhauPhan;
    String viTriDoKho;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReferenceMonAn;

    long maKeyMonAn;

    int REQUEST_CODE_CAMERA = 123;
    int REQUEST_CODE_FOLDER = 456;

    Bitmap bitmap;

    String img;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_sua_thong_tin_mon_an, container, false);

        imgSuaAnhMonAn = view.findViewById(R.id.imgSuaAnhMonAn);
        imgSuaTTLinkYouTube = view.findViewById(R.id.imgSuaTTLinkYouTube);
        edtSuaTenMonAn = view.findViewById(R.id.edtSuaTenMonAn);
        edtSuaLinkYouTube = view.findViewById(R.id.edtSuaLinkYouTube);
        edtSuaMoTaMonAn = view.findViewById(R.id.edtSuaMoTaMonAn);
        tvSuaChonTGCB = view.findViewById(R.id.tvSuaChonTGCB);
        tvSuaChonTGTH = view.findViewById(R.id.tvSuaChonTGTH);
        tvSuaChonDoKho = view.findViewById(R.id.tvSuaChonDoKho);
        spnSuaChonKhauPhan = view.findViewById(R.id.spnSuaChonKhauPhan);
        btnSuaTTMonAn = view.findViewById(R.id.btnSuaTTMonAn);

        imgSuaNen = view.findViewById(R.id.imgSuaNen);
        tvSuaNen1 = view.findViewById(R.id.tvSuaNen1);
        tvSuaNen2 = view.findViewById(R.id.tvSuaNen2);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReferenceMonAn = firebaseDatabase.getReference();

        dsKhauPhan = new ArrayList<String>();
        for (int i = 0; i < 100; i++) {
            khauPhan = khauPhan + 1;
            dsKhauPhan.add(String.valueOf(khauPhan));

        }

        chonAnh();
        thoiGianChuanBi();
        thoiGianThucHien();
        chonDoKho();
        spinnerKhauPhan();
        suaThongTinMonAn();

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReferenceMonAn = firebaseDatabase.getReference().child("TaoMonAn");

        // mã món ăn
        databaseReferenceMonAn.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    maKeyMonAn = dataSnapshot.getChildrenCount();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        return view;
    }

    private void suaThongTinMonAn() {

        Intent intent = getActivity().getIntent();

        final String maMonAn = intent.getStringExtra("maMonAnSua");
        String tenMonAn = intent.getStringExtra("tenMonAn");
        final String linkVideosMonAn = intent.getStringExtra("linkVideos");
        String moTaMonAn = intent.getStringExtra("moTaMonAn");
        String tgChuanBi = intent.getStringExtra("tgChuanBi");
        String tgThucHien = intent.getStringExtra("tgThucHien");
        final String doKho = intent.getStringExtra("doKho");
        final String khauPhan = intent.getStringExtra("khauPhan");

        edtSuaTenMonAn.setText(tenMonAn);
        edtSuaLinkYouTube.setText(linkVideosMonAn);
        edtSuaMoTaMonAn.setText(moTaMonAn);
        tvSuaChonTGCB.setText(tgChuanBi);


        tvSuaChonTGTH.setText(tgThucHien);
        tvSuaChonDoKho.setText(doKho);

        final byte[] anhMonAn = intent.getByteArrayExtra("image");

        for (int i = 0; i < spnSuaChonKhauPhan.getCount(); i++) {
            if (spnSuaChonKhauPhan.getItemAtPosition(i).equals(khauPhan)) {
                spnSuaChonKhauPhan.setSelection(i);
            }
        }

        bitmap = BitmapFactory.decodeByteArray(anhMonAn, 0, anhMonAn.length);
        imgSuaAnhMonAn.setImageBitmap(bitmap);

        imgSuaNen.setVisibility(View.GONE);
        tvSuaNen1.setVisibility(View.GONE);
        tvSuaNen2.setVisibility(View.GONE);

        btnSuaTTMonAn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    final String maM_A = maMonAn;
                    final String tenM_A = edtSuaTenMonAn.getText().toString().trim();
                    final String linkM_A = edtSuaLinkYouTube.getText().toString().trim();
                    final String moTaM_A = edtSuaMoTaMonAn.getText().toString().trim();
                    final String chonTGCBMA = tvSuaChonTGCB.getText().toString().trim();
                    final String chonTGTHMA = tvSuaChonTGTH.getText().toString().trim();
                    final String chonDK = tvSuaChonDoKho.getText().toString().trim();
                    final String chonKP = viTriKhauPhan;

                    if (bitmap == null) {
                        Toast.makeText(getContext(), "Vui Lòng chọn ảnh", Toast.LENGTH_SHORT).show();
                    } else {

                        // đưa bitmap về base64string
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                        byte[] byteArray = byteArrayOutputStream.toByteArray();
                        img = Base64.encodeToString(byteArray, Base64.DEFAULT);


                        Query query = databaseReferenceMonAn.orderByChild("maMonAn").equalTo(maM_A);
                        query.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                    ds.getRef().child("tenMonAn").setValue(tenM_A);
                                    ds.getRef().child("anhMonAn").setValue(img);
                                    ds.getRef().child("videoMonAn").setValue(linkM_A);
                                    ds.getRef().child("moTa").setValue(moTaM_A);
                                    ds.getRef().child("thoiGianChuanBi").setValue(chonTGCBMA);
                                    ds.getRef().child("thoiGianThucHien").setValue(chonTGTHMA);
                                    ds.getRef().child("doKho").setValue(chonDK);
                                    ds.getRef().child("khauPhan").setValue(chonKP);

                                }

                                SuaMonAn.viewpagerSuaTMA.setCurrentItem(1, true);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                    }
            }
        });

    }

    private void chonAnh() {

        imgSuaAnhMonAn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, REQUEST_CODE_FOLDER);
                imgSuaNen.setVisibility(View.GONE);
                tvSuaNen1.setVisibility(View.GONE);
                tvSuaNen2.setVisibility(View.GONE);
            }
        });

    }

    private void thoiGianChuanBi() {

        tvSuaChonTGCB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        tvSuaChonTGCB.setText(selectedHour + "h" + selectedMinute + "ph");
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Thời Gian Chuẩn Bị");
                mTimePicker.show();
            }
        });
    }


    private void thoiGianThucHien() {
        tvSuaChonTGTH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        tvSuaChonTGTH.setText(selectedHour + "h" + selectedMinute + "ph");
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Thời gian Thực Hiện");
                mTimePicker.setTitle("Thời gian Thực Hiện");
                mTimePicker.show();
            }
        });
    }

    private void chonDoKho() {
        tvSuaChonDoKho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog_DoKho dialog_doKho = new Dialog_DoKho(getContext());
                dialog_doKho.show();

                dialog_doKho.lvDoKho.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                        viTriDoKho = adapterView.getItemAtPosition(i).toString();
                        tvSuaChonDoKho.setText(viTriDoKho);
                        dialog_doKho.dismiss();
                    }
                });
            }
        });
    }

    private void spinnerKhauPhan() {
        Adapter_SpinnerKhauPhanMonAn adapter_spinnerKhauPhanMonAn = new Adapter_SpinnerKhauPhanMonAn(getContext(), dsKhauPhan);
        spnSuaChonKhauPhan.setAdapter(adapter_spinnerKhauPhanMonAn);

        spnSuaChonKhauPhan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                viTriKhauPhan = adapterView.getItemAtPosition(i).toString().trim();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }


    //xử lý hình ảnh
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_CAMERA && resultCode == RESULT_OK) {
            //xử lý lấy ảnh trực tiếp lúc chụp hình:
            bitmap = (Bitmap) data.getExtras().get("data");
            imgSuaAnhMonAn.setImageBitmap(bitmap);
        } else if (requestCode == REQUEST_CODE_FOLDER && resultCode == RESULT_OK) {
            Uri imageUri = data.getData();
            try {
                //xử lý lấy ảnh chọn từ điện thoại:
                bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), imageUri);

            } catch (IOException e) {
                e.printStackTrace();
            }
            imgSuaAnhMonAn.setImageBitmap(bitmap);


        }
    }

}
