package com.example.konversimatauang;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
public class MainActivity extends AppCompatActivity {

    Button btndaftar, btnlogin;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btndaftar = (Button)findViewById(R.id.btndaftar);
        btnlogin = (Button)findViewById(R.id.btnlogin);


        btndaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent moveIntent = new Intent(MainActivity.this, daftar.class);
                startActivity(moveIntent);
                finish();
            }
        });
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent moveIntent = new Intent(MainActivity.this, login.class);
                startActivity(moveIntent);
                finish();
            }
        });
    }}