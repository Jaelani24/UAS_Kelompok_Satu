package com.example.konversimatauang;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DatabaseHelper {

    private static FirebaseAuth mAuth;
    private DatabaseReference databaseRef;

    public DatabaseHelper() {
        // Inisialisasi referensi database
        mAuth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseRef = database.getReference("users");
    }

    public void checkUser(String email, String password, final OnLoginListener listener) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            listener.onLogin(true); // Login berhasil
                        } else {
                            listener.onLogin(false); // Login gagal
                            Log.e("LoginError", "Login gagal: " + task.getException().getMessage());
                        }
                    }
                });
    }

    // Interface untuk listener callback
    public interface OnLoginListener {
        void onLogin(boolean success);
    }

    public void addUser(String email, String password, final OnCompleteListener<Void> listener) {
        String emailKey = email.replace(".", "_dot_");

        User user = new User(email, password); // Pastikan kelas User sudah didefinisikan dengan baik

        databaseRef.child(emailKey).setValue(user)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            listener.onComplete(task);
                        } else {
                            // Handle error jika penambahan data gagal
                            Log.e("AddUserError", "Gagal menambahkan user: " + task.getException().getMessage());
                            listener.onComplete(task); // Menjalankan callback dengan task yang diterima
                        }
                    }
                });
    }

}
