package com.example.konversimatauang;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class login extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    private DatabaseReference mDatabase;

    EditText email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Mendapatkan referensi ke Firebase Database
        mDatabase = FirebaseDatabase.getInstance().getReference();

        email = findViewById(R.id.username);
        password = findViewById(R.id.password);
    }

    public void signInWithEmailAndPassword(View view) {
        String emailKey1 = email.getText().toString().trim();
        String emailKey = emailKey1.replace(".", "_dot_");
        String passwordKey = password.getText().toString().trim();

        DatabaseReference userRef = mDatabase.child("users").child(emailKey);

        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Data user ditemukan di Firebase Database
                    String storedPassword = dataSnapshot.child("password").getValue(String.class);
                    if (storedPassword != null && storedPassword.equals(passwordKey)) {
                        // Password sesuai, masuk ke menu
                        Log.d(TAG, "signInWithEmailAndPassword:success");
                        Toast.makeText(login.this, "Login berhasil.", Toast.LENGTH_SHORT).show();

                        Intent moveIntent = new Intent(login.this, menu.class);
                        startActivity(moveIntent);
                        finish();
                    } else {
                        // Password tidak sesuai
                        Log.w(TAG, "Password salah.");
                        Toast.makeText(login.this, "Password salah.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Data user tidak ditemukan di Firebase Database
                    Log.d(TAG, "User tidak ditemukan.");
                    Toast.makeText(login.this, "Email tidak terdaftar.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(TAG, "Error accessing database", databaseError.toException());
                Toast.makeText(login.this, "Error accessing database", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void kembalihome(View view) {
        Intent moveIntent = new Intent(login.this, MainActivity.class);
        startActivity(moveIntent);
        finish();
    }
}
