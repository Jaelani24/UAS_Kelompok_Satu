package com.example.konversimatauang;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class menu extends AppCompatActivity {


    @SuppressLint("CutPasteId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    public void menukonversi(View view) {
        Intent moveIntent = new Intent(menu.this, KonversiMataUang.class);
        startActivity(moveIntent);
        finish();
    }

    public void menukamera(View view) {
        Intent moveIntent = new Intent(menu.this, kamera.class);
        startActivity(moveIntent);
        finish();
    }

    public void menukompas(View view) {
        Intent moveIntent = new Intent(menu.this, kompas.class);
        startActivity(moveIntent);
        finish();
    }
    public void menulist(View view) {
        Intent moveIntent = new Intent(menu.this, to_do_list.class);
        startActivity(moveIntent);
        finish();
    }

    public void menulogout(View view) {
        Intent moveIntent = new Intent(menu.this, login.class);
        startActivity(moveIntent);
        finish();
    }
    public void menulokasi(View view) {
        Intent moveIntent = new Intent(menu.this, CariLokasi.class);
        startActivity(moveIntent);
        finish();
    }
    public void menupengaturan(View view) {
        Intent moveIntent = new Intent(menu.this, pengaturan.class);
        startActivity(moveIntent);
        finish();
    }
}