package com.duanmot.myapplication;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.text.format.DateFormat;
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

import com.duanmot.myapplication.Adapter.Adapter_SpinnerKhauPhanMonAn;
import com.duanmot.myapplication.Database.ThongTinMonAnDao;
import com.duanmot.myapplication.model.MonAn;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;


public class ThongTinMonAnFragment extends Fragment {

    ImageView imgAnhMonAn, imgnen;

    EditText edtTenMonAn, edtLinkYouTube, edtMoTaMonAn;

    TextView tvChonTGCB, tvChonTGTH, tvChonDoKho, tvnen1, tvnen2;

    Spinner spnChonKhauPhan;

    public Button btnSaveTTMonAn;

    ArrayList<String> dsKhauPhan;
    int khauPhan = 0;
    String viTriKhauPhan;
    String viTriDoKho;
    String doKho;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReferenceMonAn;

    long maKeyMonAn;
    String maMonAn;

    ThongTinMonAnDao monAnDao;

    int REQUEST_CODE_CAMERA = 123;
    int REQUEST_CODE_FOLDER = 456;

    Bitmap bitmap;

    String img;

    FirebaseAuth firebaseAuth;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_thong_tin_mon_an, container, false);

        imgAnhMonAn = view.findViewById(R.id.imgAnhMonAn);

        edtTenMonAn = view.findViewById(R.id.edtTenMonAn);
        edtLinkYouTube = view.findViewById(R.id.edtLinkYouTube);
        edtMoTaMonAn = view.findViewById(R.id.edtMoTaMonAn);
        tvChonTGCB = view.findViewById(R.id.tvChonTGCB);
        tvChonTGTH = view.findViewById(R.id.tvChonTGTH);
        tvChonDoKho = view.findViewById(R.id.tvChonDoKho);
        spnChonKhauPhan = view.findViewById(R.id.spnChonKhauPhan);
        btnSaveTTMonAn = view.findViewById(R.id.btnSaveTTMonAn);

        firebaseAuth = FirebaseAuth.getInstance();


        imgnen = view.findViewById(R.id.imgnen);
        tvnen1 = view.findViewById(R.id.tvnen1);
        tvnen2 = view.findViewById(R.id.tvnen2);

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
        luuThongTinMonAn();


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

    private void chonAnh() {

        imgAnhMonAn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, REQUEST_CODE_FOLDER);
                imgnen.setVisibility(View.GONE);
                tvnen1.setVisibility(View.GONE);
                tvnen2.setVisibility(View.GONE);
            }
        });

    }

    private void thoiGianChuanBi() {

        tvChonTGCB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        tvChonTGCB.setText(selectedHour + "h" + selectedMinute + "ph");
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Thời Gian Chuẩn Bị");
                mTimePicker.show();

            }
        });

    }


    private void thoiGianThucHien() {
        tvChonTGTH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        tvChonTGTH.setText(selectedHour + "h" + selectedMinute + "ph");
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Thời gian Thực Hiện");
                mTimePicker.setTitle("Thời gian Thực Hiện");
                mTimePicker.show();
            }
        });
    }


    private void chonDoKho() {
        tvChonDoKho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog_DoKho dialog_doKho = new Dialog_DoKho(getContext());
                dialog_doKho.show();

                dialog_doKho.lvDoKho.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                        viTriDoKho = adapterView.getItemAtPosition(i).toString();

                        tvChonDoKho.setText(viTriDoKho);
                        dialog_doKho.dismiss();
                    }
                });
            }
        });

    }


    private void spinnerKhauPhan() {
        Adapter_SpinnerKhauPhanMonAn adapter_spinnerKhauPhanMonAn = new Adapter_SpinnerKhauPhanMonAn(getContext(), dsKhauPhan);
        spnChonKhauPhan.setAdapter(adapter_spinnerKhauPhanMonAn);

        spnChonKhauPhan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                viTriKhauPhan = adapterView.getItemAtPosition(i).toString().trim();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    private void luuThongTinMonAn() {
        btnSaveTTMonAn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tenMonAn = edtTenMonAn.getText().toString().trim();
                String linkYouTube = edtLinkYouTube.getText().toString().trim();
                String moTaMA = edtMoTaMonAn.getText().toString().trim();

                String thoiGianChuanBi = tvChonTGCB.getText().toString().trim();
                String thoiGianThucHien = tvChonTGTH.getText().toString().trim();
                doKho = tvChonDoKho.getText().toString().trim();

                if (!tenMonAn.isEmpty() && !moTaMA.isEmpty() && !thoiGianChuanBi.isEmpty() && !thoiGianThucHien.isEmpty() &&
                        !doKho.isEmpty()) {

                    if (bitmap == null) {
                        Toast.makeText(getContext(), "Vui Lòng chọn ảnh", Toast.LENGTH_SHORT).show();
                    } else {
                        // đưa bitmap về base64string
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
                        byte[] byteArray = byteArrayOutputStream.toByteArray();
                        img = Base64.encodeToString(byteArray, Base64.DEFAULT);

                        maMonAn = databaseReferenceMonAn.push().getKey();
                        FirebaseUser user = firebaseAuth.getCurrentUser();
                        String idUser = user.getUid();

                        Calendar cal = Calendar.getInstance(Locale.getDefault());

                        String dateTime = DateFormat.format("dd/MM/yyyy hh:mm aa", cal).toString();

                        MonAn monAn = new MonAn(maMonAn, tenMonAn, img, linkYouTube, moTaMA, thoiGianChuanBi,
                                thoiGianThucHien, doKho, viTriKhauPhan,idUser, dateTime);
                        monAnDao = new ThongTinMonAnDao(getContext());
                        monAnDao.insert(monAn);

                        Toast.makeText(getContext(), "Thêm thông tin món ăn thành công", Toast.LENGTH_SHORT).show();

                        TaoMonAn.viewPager.setCurrentItem(1, true);
                    }
                } else if (tenMonAn.isEmpty()) {
                    Toast.makeText(getContext(), "Tên món ăn không đươc bỏ trống ", Toast.LENGTH_SHORT).show();
                } else if (moTaMA.isEmpty()) {
                    Toast.makeText(getContext(), "Mô tả món ăn không đươc bỏ trống ", Toast.LENGTH_SHORT).show();
                } else if (thoiGianChuanBi.isEmpty()) {
                    Toast.makeText(getContext(), "Thời gian chuẩn bị không đươc bỏ trống ", Toast.LENGTH_SHORT).show();
                } else if (thoiGianThucHien.isEmpty()) {
                    Toast.makeText(getContext(), "Thời gian thực hiện không đươc bỏ trống ", Toast.LENGTH_SHORT).show();
                } else if (doKho.isEmpty()) {
                    Toast.makeText(getContext(), "Độ khó không đươc bỏ trống ", Toast.LENGTH_SHORT).show();
                }
            }


        });

    }


    //xử lý hình ảnh
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_CAMERA && resultCode == RESULT_OK) {
            //xử lý lấy ảnh trực tiếp lúc chụp hình:
            bitmap = (Bitmap) data.getExtras().get("data");
            imgAnhMonAn.setImageBitmap(bitmap);
        } else if (requestCode == REQUEST_CODE_FOLDER && resultCode == RESULT_OK) {
            Uri imageUri = data.getData();
            try {
                //xử lý lấy ảnh chọn từ điện thoại:
                bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), imageUri);

            } catch (IOException e) {
                e.printStackTrace();
            }
            imgAnhMonAn.setImageBitmap(bitmap);

        }
    }

}
