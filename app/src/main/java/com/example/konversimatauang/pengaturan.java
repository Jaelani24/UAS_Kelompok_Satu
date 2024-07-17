package com.example.konversimatauang;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class pengaturan extends AppCompatActivity {

    private static final String TAG = "PengaturanActivity";
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private EditText emailField, passwordField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pengaturan);

        // Mendapatkan referensi ke Firebase Database
        mDatabase = FirebaseDatabase.getInstance().getReference();

        mAuth = FirebaseAuth.getInstance();
        emailField = findViewById(R.id.username);
        passwordField = findViewById(R.id.password);
    }

    public void gantipassword(View view) {
        String emailKey1 = emailField.getText().toString().trim();
        String emailKey = emailKey1.replace(".", "_dot_");
        String passwordKey = passwordField.getText().toString().trim();

        DatabaseReference userRef = mDatabase.child("users").child(emailKey);

        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Data user ditemukan di Firebase Database
                    updatePassword(emailKey1, passwordKey);
                } else {
                    // Data user tidak ditemukan di Firebase Database
                    Log.d(TAG, "User tidak ditemukan.");
                    Toast.makeText(pengaturan.this, "Email tidak terdaftar.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(TAG, "Error accessing database", databaseError.toException());
                Toast.makeText(pengaturan.this, "Error accessing database", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updatePassword(String email, String newPassword) {
        DatabaseReference userRef = mDatabase.child("users").child(email.replace(".", "_dot_"));

        // Membuat objek untuk mengupdate data password
        Map<String, Object> updateData = new HashMap<>();
        updateData.put("password", newPassword);

        // Melakukan update password di Firebase Database
        userRef.updateChildren(updateData)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            // Update password berhasil
                            Toast.makeText(pengaturan.this, "Password berhasil diupdate", Toast.LENGTH_SHORT).show();
                            Intent moveIntent = new Intent(pengaturan.this, login.class);
                            startActivity(moveIntent);
                            finish();
                        } else {
                            // Update password gagal
                            Log.e(TAG, "Gagal mengupdate password", task.getException());
                            Toast.makeText(pengaturan.this, "Gagal mengupdate password", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void kembalihome(View view) {
        Intent moveIntent = new Intent(pengaturan.this, menu.class);
        startActivity(moveIntent);
        finish();
    }
}
