package com.example.konversimatauang;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class CariLokasi extends AppCompatActivity implements LocationListener {

    private TextView tvLocation;
    private LocationManager locationManager;

    private static final int REQUEST_LOCATION_PERMISSION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cari_lokasi);

        tvLocation = findViewById(R.id.tvLocation);
        Button btnRefresh = findViewById(R.id.btnRefresh);
        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshLocation();
            }
        });

        // Inisialisasi LocationManager
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Meminta izin akses lokasi jika belum diizinkan
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_PERMISSION);
        } else {
            // Memperbarui lokasi saat izin sudah diberikan
            requestLocationUpdates();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Memberhentikan pembaruan lokasi untuk menghemat daya baterai
        locationManager.removeUpdates(this);
    }

    private void requestLocationUpdates() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, this);
        }
    }

    private void refreshLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (lastKnownLocation != null) {
                double latitude = lastKnownLocation.getLatitude();
                double longitude = lastKnownLocation.getLongitude();
                tvLocation.setText("Current Location:\nLatitude: " + latitude + "\nLongitude: " + longitude);
            } else {
                tvLocation.setText("Last known location not available.");
            }
        } else {
            Toast.makeText(this, "Location permission not granted.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        tvLocation.setText("Current Location:\nLatitude: " + latitude + "\nLongitude: " + longitude);
    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {
        // Called when the user enables the GPS provider
        requestLocationUpdates();
    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {
        // Called when the user disables the GPS provider
        Toast.makeText(this, "GPS provider disabled", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // Not used, but required to implement LocationListener
    }


    public void kembalimenu(View view) {
        Intent moveIntent = new Intent(CariLokasi.this, menu.class);
        startActivity(moveIntent);
        finish();
    }
}