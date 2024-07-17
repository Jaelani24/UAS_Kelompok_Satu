package com.example.konversimatauang;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class daftar extends AppCompatActivity {
    EditText email, password;
    Button btnDaftar, btnKembali;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar);

        dbHelper = new DatabaseHelper();

        email = findViewById(R.id.username);
        password = findViewById(R.id.password);
        btnDaftar = findViewById(R.id.btnDaftar);
        btnKembali = findViewById(R.id.btnKembali);

        btnDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailKey = email.getText().toString().trim();
                String passwordKey = password.getText().toString().trim();

                if (emailKey.isEmpty() || passwordKey.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Email dan Password harus diisi", Toast.LENGTH_SHORT).show();
                } else {
                    dbHelper.addUser(emailKey, passwordKey, new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), "Pendaftaran berhasil!", Toast.LENGTH_SHORT).show();
                                Intent moveIntent = new Intent(daftar.this, login.class);
                                startActivity(moveIntent);
                                finish();
                            } else {
                                // Tambahkan penanganan kesalahan untuk menampilkan pesan kesalahan
                                String errorMessage = task.getException() != null ? task.getException().getMessage() : "Pendaftaran gagal!";
                                AlertDialog.Builder builder = new AlertDialog.Builder(daftar.this);
                                builder.setMessage("Pendaftaran gagal! " + errorMessage)
                                        .setNegativeButton("OK", null)
                                        .create()
                                        .show();
                            }
                        }
                    });
                }
            }
        });

        btnKembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent moveIntent = new Intent(daftar.this, MainActivity.class);
                startActivity(moveIntent);
                finish();
            }
        });
    }
}
