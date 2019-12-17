package com.duanmot.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {
    ProgressBar progressBar2;
    private FirebaseAuth firebaseAuth;
    Toolbar toolbar;
    TabHost tabHost;
    TextView tv_forgotpass;
    Button btnSignIn, btnSignUp, btn_forgotpass_confirm;
    RelativeLayout asd, asd2, asdForgot;
    EditText mEmailEt, mPasswordEt, password_again_TIL;
    EditText mEmailLoginEt, mPasswordLoginEt;
    EditText email_resetTEL;

    CheckBox chkLuuEmail_Password;
    String luuThongTinDangNhap = "thongtindangnhap";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();

        tabHost = findViewById(R.id.tabhost);
        tabHost.setup();
        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {

            @Override
            public void onTabChanged(String tabId) {

                for (int i = 0; i < tabHost.getTabWidget().getChildCount(); i++) {
                    tabHost.getTabWidget().getChildAt(i).setBackgroundColor(Color.parseColor("#A6A6A6")); // unselected
                    TextView tv = (TextView) tabHost.getTabWidget().getChildAt(i).findViewById(android.R.id.title); //Unselected Tabs
                    tv.setTextColor(Color.parseColor("#000000"));
                    tv.setTextSize(16);
                }
                // selected
                tabHost.getTabWidget().getChildAt(tabHost.getCurrentTab()).setBackgroundColor(Color.parseColor("#66FC00"));
                TextView tv = (TextView) tabHost.getCurrentTabView().findViewById(android.R.id.title); //for Selected Tab
                tv.setTextColor(Color.parseColor("#000000"));
                tv.setTextSize(18);
            }
        });

        anhXa();
        addControls();
        thread();
        register();
        login();

        toolbar.setVisibility(View.GONE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        forgotpass();


    }

    private void forgotpass() {
        tv_forgotpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toolbar.setVisibility(View.VISIBLE);
                asd.setVisibility(View.GONE);
                asdForgot.setVisibility(View.VISIBLE);

                btn_forgotpass_confirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String email = email_resetTEL.getText().toString().trim();
                        geginRecovery(email);
                    }
                });

            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        //lưu thông tin

        SharedPreferences preferences = getSharedPreferences(luuThongTinDangNhap, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        String email = mEmailLoginEt.getText().toString().trim();
        String passw = mPasswordLoginEt.getText().toString().trim();
        editor.putString("email", email);
        editor.putString("password", passw);
        editor.putBoolean("save", chkLuuEmail_Password.isChecked());
        editor.commit();

    }

    @Override
    protected void onResume() {
        super.onResume();
        //phục hồi thông tin
        SharedPreferences preferences = getSharedPreferences(luuThongTinDangNhap, MODE_PRIVATE);
        String email = preferences.getString("email", "");
        String pass = preferences.getString("password", "");
        boolean save = preferences.getBoolean("save", false);

        if (save) {
            mEmailLoginEt.setText(email);
            mPasswordLoginEt.setText(pass);

        }

        chkLuuEmail_Password.setChecked(save);

    }

    private void geginRecovery(String email) {

        firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (task.isSuccessful()) {
                    Toast.makeText(LoginActivity.this, "Email đã được gửi tới mail của Bạn", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(LoginActivity.this, "Failed...", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                // get and show proper error message

                Toast.makeText(LoginActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void login() {

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // input data

                String email = mEmailLoginEt.getText().toString().trim();
                String passw = mPasswordLoginEt.getText().toString().trim();

                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {

                    // invalid email pattern set error

                    mEmailEt.setError("Email không hợp lệ");
                    mEmailEt.setFocusable(true);
                } else {

                    // valid email partern

                    loginUser(email, passw);
                }
            }
        });

    }

    private void loginUser(String email, String passw) {


        firebaseAuth.signInWithEmailAndPassword(email, passw)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            // Sign in success, update UI with the signed-in user's information

                            FirebaseUser user = firebaseAuth.getCurrentUser();

                            // user is logged in, so start LoginActivity

                            Toast.makeText(LoginActivity.this, "Đăng nhập thành công.", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(LoginActivity.this, ManHinhChinh.class));
                            finish();

                        } else {
                            // If sign in fails, display a message to the user.

                            Toast.makeText(LoginActivity.this, "Đăng nhập thất bại.", Toast.LENGTH_SHORT).show();

                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                // error, get and show error message

                Toast.makeText(LoginActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void register() {
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = mEmailEt.getText().toString().trim();
                String password = mPasswordEt.getText().toString().trim();
                String passwordagain = password_again_TIL.getText().toString().trim();

                if (!email.isEmpty() || !password.isEmpty() || !passwordagain.isEmpty()) {
                    if (password.equals(passwordagain)) {
                        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {

                            mEmailEt.setError("Email không hợp lệ");
                            mEmailEt.setFocusable(true);
                        } else if (password.length() < 6) {
                            mPasswordEt.setError("Mật khẩu phải trên 6 ký tự");
                            mPasswordEt.setFocusable(true);
                        } else {
                            registerUser(email, password);
                        }
                    } else {
                        Toast.makeText(LoginActivity.this, "Mật khẩu không trùng khớp", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(LoginActivity.this, "Không được bỏ trống", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    private void registerUser(String email, String password) {
        firebaseAuth.createUserWithEmailAndPassword(email, password).
                addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = firebaseAuth.getCurrentUser();

                            String email = user.getEmail();
                            String uid = user.getUid();

                            String[] mangEmail = email.split("@");
                            String ten = mangEmail[0];

                            HashMap<Object, String> hashMap = new HashMap<>();

                            // put info in hashmap
                            hashMap.put("email", email);
                            hashMap.put("uid", uid);
                            hashMap.put("name", ten); // will add later (e.g.edit profile)
                            hashMap.put("phone", ""); // will add later (e.g.edit profile)
                            hashMap.put("image", "https://firebasestorage.googleapis.com/v0/b/foodduan1.appspot.com/o/Users_Profile_Cover_Imgs%2Fimg_chef.jpg?alt=media&token=b1e656fc-f963-493c-80c5-1f682e7ca6a8"); // will add later (e.g.edit profile)
                            hashMap.put("cover", "https://firebasestorage.googleapis.com/v0/b/foodduan1.appspot.com/o/Users_Profile_Cover_Imgs%2Fimage_BackGround_User.jpg?alt=media&token=a1533dd6-e401-45d8-9d5a-743b71b540cd"); // will add later (e.g.edit profile)

                            // firebase database instance
                            FirebaseDatabase database = FirebaseDatabase.getInstance();

                            // path to store user data named "Users"
                            DatabaseReference reference = database.getReference("Users");

                            // put data within hashmap in database
                            reference.child(uid).setValue(hashMap);

                            Toast.makeText(LoginActivity.this, "Bạn đã đăng ký thành công", Toast.LENGTH_SHORT).show();

                            startActivity(new Intent(LoginActivity.this, ManHinhChinh.class));
                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(LoginActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void thread() {

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    Thread.sleep(3000);
                } catch (Exception e) {

                } finally {
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            asd2.setVisibility(View.INVISIBLE);
                            asd.setVisibility(View.VISIBLE);
                            // Stuff that updates the UI
                        }
                    });

                }
            }
        });

        thread.start();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {

            asd.setVisibility(View.VISIBLE);
            asdForgot.setVisibility(View.GONE);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        asd.setVisibility(View.VISIBLE);
        asdForgot.setVisibility(View.GONE);
        // Invisible or Gone Your Views here....
    }

    private void anhXa() {

        progressBar2 = findViewById(R.id.progressBar2);
        btnSignIn = findViewById(R.id.btn_signin);
        btnSignUp = findViewById(R.id.btn_signup);
        asd = findViewById(R.id.asd);
        asd2 = findViewById(R.id.asd2);
        asdForgot = findViewById(R.id.relayForgot);
        tv_forgotpass = findViewById(R.id.forgot_password);
        toolbar = findViewById(R.id.toolbar);

        mEmailEt = findViewById(R.id.emailEt);
        mPasswordEt = findViewById(R.id.passwordEt);
        password_again_TIL = findViewById(R.id.password_again_TIL);

        mEmailLoginEt = findViewById(R.id.emailLoginEt);
        mPasswordLoginEt = findViewById(R.id.passwordLoginEt);

        btn_forgotpass_confirm = findViewById(R.id.btn_forgotpass_confirm);

        email_resetTEL = findViewById(R.id.email_resetTEL);

        chkLuuEmail_Password = findViewById(R.id.chkLuuEmail_Password);
    }

    private void addControls() {

        //Tạo đối tượng 1 tab, có id là t1
        TabHost.TabSpec tab1 = tabHost.newTabSpec("t1");

        //thiết lập nội dung layout
        tab1.setContent(R.id.tab1);

        //thiết lập tiêu đề cho tab:
        tab1.setIndicator("Đăng Nhập");

        // đưa tab1 vào tabhost
        tabHost.addTab(tab1);


        //Tạo đối tượng 1 tab, có id là t2
        TabHost.TabSpec tab2 = tabHost.newTabSpec("t2");

        //thiết lập nội dung layout
        tab2.setContent(R.id.tab2);

        //thiết lập tiêu đề cho tab:
        tab2.setIndicator("Đăng Ký");

        // đưa tab1 vào tabhost
        tabHost.addTab(tab2);

    }

}
