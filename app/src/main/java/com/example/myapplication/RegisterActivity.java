package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.example.myapplication.database.DbHelper;
import com.example.myapplication.model.SinhVien;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;



public class RegisterActivity extends AppCompatActivity {

    TextInputLayout tilTenDangNhap, tilMatKhau, tilNhapLaiMatKhau;
    TextInputEditText edtTenDangNhap, edtMatKhau, edtNhapLaiMatKhau;
    Button btnDangKy;

    DbHelper dbHelper;
    SinhVien sv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        tilTenDangNhap = findViewById(R.id.tilTenDangNhap);
        tilMatKhau = findViewById(R.id.tilMatKhau);
        tilNhapLaiMatKhau = findViewById(R.id.tilNhapLaiMatKhau);
        edtTenDangNhap = findViewById(R.id.edtTenDangNhap);
        edtMatKhau = findViewById(R.id.edtMatKhau);
        edtNhapLaiMatKhau = findViewById(R.id.edtNhapLaiMatKhau);
        btnDangKy = findViewById(R.id.btnDangKy);

        dbHelper = new DbHelper(this);

        btnDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!validateEditText(tilTenDangNhap,edtTenDangNhap) | !validateEditText(tilMatKhau,edtMatKhau) | !validateEditText(tilNhapLaiMatKhau,edtNhapLaiMatKhau)) {
                    return;
                }

                String _user = edtTenDangNhap.getText().toString();
                String _mk = edtMatKhau.getText().toString();
                String _mkNhapLai = edtNhapLaiMatKhau.getText().toString();

                if(_mk.equals(_mkNhapLai)){
                    sv = new SinhVien(_user,_mk);
                    if(dbHelper.insert(sv)){
                        Toast.makeText(RegisterActivity.this, "Đăng ký thành công ", Toast.LENGTH_SHORT).show();
//                        Toasty.success(RegisterActivity.this, "Đăng ký thành công!", Toasty.LENGTH_SHORT, true).show();
                    }else{
                        Toast.makeText(RegisterActivity.this, "Username đã tồn tại.", Toast.LENGTH_SHORT).show();
//                        Toasty.error(RegisterActivity.this, "Username đã tồn tại.", Toasty.LENGTH_SHORT, true).show();
                        edtTenDangNhap.setFocusable(true);
                    }
                }else{
                    Toast.makeText(RegisterActivity.this, "Mật khẩu phải giống nhau.", Toast.LENGTH_SHORT).show();
//                    Toasty.error(RegisterActivity.this, "Mật khẩu phải giống nhau.", Toasty.LENGTH_SHORT, true).show();
                }
            }
        });

    }

    private boolean validateEditText(TextInputLayout til, TextInputEditText edt) {
        String _str = edt.getText().toString().trim();
        if (_str.isEmpty()) {
            til.setError("Vui lòng không bỏ trống");
            return false;
        } else {
            til.setError("");
            til.setErrorEnabled(false);
            return true;
        }
    }
}