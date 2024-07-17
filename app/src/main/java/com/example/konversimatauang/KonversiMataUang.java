package com.example.konversimatauang;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class KonversiMataUang extends AppCompatActivity {
    TextView t;
    EditText e;
    Button b, b2;
    RadioButton rb1, rb2, rb3;

    @SuppressLint("CutPasteId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_konversi_mata_uang);

        // Initialize ActionBar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowHomeEnabled(true);  // Ensure actionBar is not null before accessing methods
            // Other actionBar customizations if needed
        }

        t = findViewById(R.id.textView);
        e = findViewById(R.id.editTextNumber);
        b = findViewById(R.id.button);
        b2 = findViewById(R.id.button2);
        rb1 = findViewById(R.id.radioButton);
        rb2 = findViewById(R.id.radioButton2);
        rb3 = findViewById(R.id.radioButton3);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Perform currency conversion
                if (e.getText().toString().isEmpty()) {
                    Toast.makeText(KonversiMataUang.this, "Masukkan jumlah uang", Toast.LENGTH_SHORT).show();
                    return;
                }

                double k = Double.parseDouble(e.getText().toString());
                double rate = 0.0;
                String currency = "";

                if (rb1.isChecked()) {
                    rate = 15.227;
                    currency = " Rupiah";
                } else if (rb2.isChecked()) {
                    rate = 16.264;
                    currency = " Rupiah";
                } else if (rb3.isChecked()) {
                    rate = 18.31448;
                    currency = " Rupiah";
                }

                double result = k * rate;
                e.setText(String.format("%.2f", result));
                Toast.makeText(KonversiMataUang.this, String.format("%.2f", result) + currency, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("Pengaturan");
        menu.add("Nilai Kami");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getTitle().equals("Pengaturan")) {
            Toast.makeText(this, "Saya butuh bantuan", Toast.LENGTH_SHORT).show();
        } else if (item.getTitle().equals("Nilai Kami")) {
            Toast.makeText(this, "Saya bingung", Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    public void kembalimainmenu(View view) {
        Intent moveIntent = new Intent(KonversiMataUang.this, menu.class);
        startActivity(moveIntent);
        finish();
    }

}