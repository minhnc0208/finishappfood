package com.duanmot.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AccoutPassChange extends AppCompatActivity {
    Toolbar toolbar;
    EditText edtpassword_change, edtpassword_change_again;
    Button btn_passchange_confirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accout_pass_change);
        edtpassword_change = findViewById(R.id.edtpassword_change);
        edtpassword_change_again = findViewById(R.id.edtpassword_change_again);
        btn_passchange_confirm = findViewById(R.id.btn_passchange_confirm);

        changePassword();

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    private void changePassword() {
        btn_passchange_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String pass = edtpassword_change.getText().toString().trim();
                String pass_again = edtpassword_change_again.getText().toString().trim();

                if (!pass.isEmpty() || !pass_again.isEmpty()) {
                    if (pass.length() < 6) {
                        Toast.makeText(AccoutPassChange.this, "Mật khẩu phải trên 6 ký tự", Toast.LENGTH_SHORT).show();
                    } else {
                        if (pass.equals(pass_again)) {
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            user.updatePassword(pass)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Log.d("New password", "User password updated.");
                                                Toast.makeText(AccoutPassChange.this, "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                                                finish();
                                            }
                                        }
                                    });
                        } else {
                            Toast.makeText(AccoutPassChange.this, "Mật khẩu không trùng khớp", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    Toast.makeText(AccoutPassChange.this, "Không được bỏ trống", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
