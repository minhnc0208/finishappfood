package com.duanmot.myapplication;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;
import static com.google.firebase.storage.FirebaseStorage.getInstance;


public class AccountFragmentActivity extends Fragment {

    Button btnAccountEdit, btnMonandatao, btnMonandathuchien, btnMonanyeuthich, btnDoimatkhau, btnDangxuat;

    ImageView imgBackGroundUser;
    CircleImageView imgAvatarUser;
    TextView tv_tennguoidung, tv_emailnguoidung;

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReferenceUser;

    //storage save images in firebase
    StorageReference storageReference;

    String storagePath = "Users_Profile_Cover_Imgs/";

    private static final int CAMERA_REQUEST_CODE = 100;
    private static final int STORAGE_REQUEST_CODE = 200;
    private static final int IMAGE_PICK_GALLERY_CODE = 300;
    private static final int IMAGE_PICK_CAMERA_CODE = 400;

    // arrays of permission to requested
    String cameraPermission[];
    String storagePermission[];

    Uri image_uri;

    String profileOrCoverPhoto;

    // progress dialog
    ProgressDialog pd;

    private static final int MY_CAMERA_REQUEST_CODE = 100;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.account_fragment, container, false);

        btnMonandatao = view.findViewById(R.id.btn_monandatao);
        btnMonandathuchien = view.findViewById(R.id.btn_monandathuchien);
        btnMonanyeuthich = view.findViewById(R.id.btn_monanyeuthich);
        btnAccountEdit = view.findViewById(R.id.btn_account_edit);
        btnDoimatkhau = view.findViewById(R.id.btn_doimatkhau);
        btnDangxuat = view.findViewById(R.id.btn_dangxuat);
        imgBackGroundUser = view.findViewById(R.id.imgBackGroundUser);
        imgAvatarUser = view.findViewById(R.id.imgAvatarUser);
        tv_tennguoidung = view.findViewById(R.id.tv_tennguoidung);
        tv_emailnguoidung = view.findViewById(R.id.tv_emailnguoidung);

        // init progress dialog

        pd = new ProgressDialog(getActivity());

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReferenceUser = firebaseDatabase.getReference("Users");
        storageReference = getInstance().getReference();

        cameraPermission = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        storagePermission = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};


        Query query = databaseReferenceUser.orderByChild("email").equalTo(firebaseUser.getEmail());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot dataUsers : dataSnapshot.getChildren()) {

                    String name = "" + dataUsers.child("name").getValue();
                    String email = "" + dataUsers.child("email").getValue();
                    String phone = "" + dataUsers.child("phone").getValue();
                    String image = "" + dataUsers.child("image").getValue();
                    String imageAvatar = "" + dataUsers.child("cover").getValue();

                    tv_tennguoidung.setText(name);
                    tv_emailnguoidung.setText(email);

                    try {
                        // if image is received then set
                        Picasso.get().load(image).into(imgAvatarUser);
                    } catch (Exception e) {
                        // if there is any exception while getting image then set default
                        Picasso.get().load(R.drawable.img_chef).into(imgAvatarUser);
                    }

                    try {
                        // if image is received then set
                        Picasso.get().load(imageAvatar).into(imgBackGroundUser);
                    } catch (Exception e) {
                        // if there is any exception while getting image then set default
                        Picasso.get().load(R.drawable.image_avata_user).into(imgBackGroundUser);
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        doiMatKhau();
        monAnDaTao();
        monAnDaThucHien();
        monAnYeuThich();
        dangxuat();
        themThongTinUser();
        return view;
    }

    private void showEditProfileDialog() {

        // show dialog containing options

        // 1: Edit Profile Picture
        // 2: Edit Cover Photo
        // 3: Edit Name
        // 4: Edit Phone

        // options to show in dialog

        String options[] = {"Sửa Ảnh Đại Diện", "Sửa Background Ảnh Đại Diện", "Sửa Tên", "Sửa Số Điện Thoại"};

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        // set tittle

        builder.setTitle("Choose Action");

        // set items to dialog

        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                // handle dialog item clicks

                if (which == 0) {

                    // edit profile clicked

                    pd.setMessage("Cập nhật ảnh đại diện");
                    profileOrCoverPhoto = "image";
                    showImagePicDialog();

                } else if (which == 1) {

                    // edit cover clicked

                    pd.setMessage("Cập Background Ảnh Đại Diện");
                    profileOrCoverPhoto = "cover";
                    showImagePicDialog();

                } else if (which == 2) {

                    // edit name clicked

                    pd.setMessage("Cập nhật tên");

                    // calling method and pass key "name" as parameter to update it's value in database

                    showNamePhoneUpdatedDialog("name");

                } else if (which == 3) {

                    // edit phone clicked

                    pd.setMessage("Cập nhật số điện thoại");

                    showNamePhoneUpdatedDialog("phone");
                }
            }
        });

        // create and show dialog
        builder.create().show();


    }

    private void showNamePhoneUpdatedDialog(final String key) {

        // custom dialog

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("Update" + key);

        // set layout of dialog

        LinearLayout linearLayout = new LinearLayout(getActivity());

        linearLayout.setOrientation(LinearLayout.VERTICAL);

        linearLayout.setPadding(10, 10, 10, 10);

        // add edit text

        final EditText editText = new EditText(getActivity());

        editText.setHint("Enter " + key);

        linearLayout.addView(editText);

        builder.setView(linearLayout);

        // add buttons in dialog to update

        builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                // input text from edit text

                String value = editText.getText().toString().trim();

                // validate if user has entered something or not

                if (!TextUtils.isEmpty(value)) {

                    pd.show();

                    HashMap<String, Object> result = new HashMap<>();

                    result.put(key, value);

                    databaseReferenceUser.child(firebaseUser.getUid()).updateChildren(result)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {

                                    // updated, dismiss progress

                                    pd.dismiss();
                                    Toast.makeText(getActivity(), "Updated", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            // failed, dismiss progress, get and show error message

                            pd.dismiss();
                            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    });

                } else {

                    Toast.makeText(getActivity(), "Please enter" + key, Toast.LENGTH_SHORT).show();
                }

            }
        });

        // add buttons in dialog to cancel

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.create().show();
    }

    private void showImagePicDialog() {
// show dialog containing options camera and gallery to pick the image

        String options[] = {"Camera", "Ảnh Trong Bộ Sưu Tập"};

        // alert dialog

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        // set tittle

        builder.setTitle("Chọn Ảnh từ: ");

        // set items to dialog

        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                // handle dialog item clicks

                if (which == 0) {

                    // Camera clicked

                    if (!checkCameraPermission()) {

                        requestCameraPermission();

                    } else {
                        pickFromCamera();
                    }

                } else if (which == 1) {

                    // Gallery clicked

                    if (!checkStoragePermission()) {

                        requestStoragePermission();
                    } else {

                        pickFromGallery();
                    }

                }
            }
        });

        // create and show dialog

        builder.create().show();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        // this method called when user press alow or deny from permission request dialog
        // here we will handle permission cases ( allowed & denied )

        switch (requestCode) {

            case CAMERA_REQUEST_CODE: {

                // picking form camera, first check if camera and storage permission allowed or not

                if (grantResults.length > 0) {
                    boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean writeStorageAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                    if (cameraAccepted && writeStorageAccepted) {

                        // permission enabled

                        pickFromCamera();
                    } else {

                        // permisions denied

                        Toast.makeText(getActivity(), "Please enable camera & storage permission", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            break;
            case STORAGE_REQUEST_CODE: {

                // picking form galler, first check if storage permission allowed or not

                if (grantResults.length > 0) {

                    boolean writeStorageAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                    if (writeStorageAccepted) {

                        // permission enabled

                        pickFromGallery();
                    } else {

                        // permisions denied

                        Toast.makeText(getActivity(), "Please enable storage permission", Toast.LENGTH_SHORT).show();
                    }
                }

            }
            break;
        }
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }



    private void pickFromGallery() {
        // pick from gallery

        Intent galleryIntent = new Intent(Intent.ACTION_PICK);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, IMAGE_PICK_GALLERY_CODE);
    }

    private void requestStoragePermission() {

        // request runtime storage permission

        requestPermissions(storagePermission, STORAGE_REQUEST_CODE);
    }

    private boolean checkStoragePermission() {
        // check if storage permission is enabled or not

        // return true if enabled

        // return false if not enabled

        boolean result = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == (PackageManager.PERMISSION_GRANTED);

        return result;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        // this method will be called after picking image from camera of gallery

        if (resultCode == RESULT_OK) {

            if (requestCode == IMAGE_PICK_GALLERY_CODE) {

                // image is picked from gallery, get uri of image

                image_uri = data.getData();

                uploadProfileCoverPhoto(image_uri);

            }
            if (requestCode == IMAGE_PICK_CAMERA_CODE) {
                // image is picked from camera, get uri of image

                uploadProfileCoverPhoto(image_uri);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void uploadProfileCoverPhoto(final Uri uri) {

        // show progress

        pd.show();

        // path and name of image to be stored in firebase storage
        String filePathAndName = storagePath + "" + profileOrCoverPhoto + "_" + firebaseUser.getUid();

        StorageReference storageReference2nd = storageReference.child(filePathAndName);

        storageReference2nd.putFile(uri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        // image is uploaded to storage, now get it's url and store in user's database

                        Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();

                        while (!uriTask.isSuccessful()) ;

                        Uri downloadUri = uriTask.getResult();

                        // check if image is uploaded or not and url is received

                        if (uriTask.isSuccessful()) {
                            // image upload
                            // add/ update url in user's database

                            HashMap<String, Object> results = new HashMap<>();

                            results.put(profileOrCoverPhoto, downloadUri.toString());

                            databaseReferenceUser.child(firebaseUser.getUid()).updateChildren(results)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {

                                            // url in database of user is added successfully

                                            // dismiss progress bar

                                            pd.dismiss();

                                            Toast.makeText(getActivity(), "Image Updated...", Toast.LENGTH_SHORT).show();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    pd.dismiss();
                                    Toast.makeText(getActivity(), "Error Updating Image...", Toast.LENGTH_SHORT).show();

                                }
                            });


                        } else {

                            // error

                            pd.dismiss();
                            Toast.makeText(getActivity(), "Some error occured", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                // there were some error(s), get and show error message, dismiss progress dialog
                pd.dismiss();
                Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void pickFromCamera() {
        // intent of picking image from device camera

        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "Temp Pic");
        values.put(MediaStore.Images.Media.DESCRIPTION, "Temp Description");

        // put image uri

        image_uri = getActivity().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        // intent to start camera

        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri);

        startActivityForResult(cameraIntent, IMAGE_PICK_CAMERA_CODE);
    }

    private void requestCameraPermission() {
        // request runtime storage permission

        requestPermissions(cameraPermission, CAMERA_REQUEST_CODE);
    }

    private boolean checkCameraPermission() {
        // check if storage permission is enabled or not

        // return true if enabled

        // return false if not enabled

        boolean result = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA)
                == (PackageManager.PERMISSION_GRANTED);


        boolean result1 = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == (PackageManager.PERMISSION_GRANTED);

        return result && result1;
    }


    private void themThongTinUser() {
        btnAccountEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showEditProfileDialog();


            }
        });
    }

    private void dangxuat() {
        btnDangxuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);

            }
        });
    }

    private void monAnDaThucHien() {
        btnMonandathuchien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), ListMonAn_ThucHien_Activity.class));
            }
        });
    }

    private void monAnDaTao() {
        btnMonandatao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), DanhSachMonAnNguoiDung.class));
            }
        });
    }

    private void monAnYeuThich() {
        btnMonanyeuthich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ListMonAn_YeuThich_Activity.class));
            }
        });
    }

    private void doiMatKhau() {
        btnDoimatkhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), AccoutPassChange.class));
            }
        });
    }



}
