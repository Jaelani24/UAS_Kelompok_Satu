package com.example.konversimatauang;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.io.IOException;

public class kamera extends AppCompatActivity implements SurfaceHolder.Callback {

    private Camera mCamera;
    private SurfaceView mSurfaceView;
    private SurfaceHolder mSurfaceHolder;
    private MediaRecorder mMediaRecorder;
    private boolean isRecording = false;

    private static final int REQUEST_CAMERA_PERMISSION = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kamera);

        mSurfaceView = findViewById(R.id.surfaceView);
        mSurfaceHolder = mSurfaceView.getHolder();
        mSurfaceHolder.addCallback(this);

        Button btnCapture = findViewById(R.id.btnCapture);
        btnCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                capturePhoto();
            }
        });

    }

    private void capturePhoto() {
        if (mCamera != null) {
            mCamera.takePicture(null, null, new Camera.PictureCallback() {
                @Override
                public void onPictureTaken(byte[] data, Camera camera) {
                    // Handle captured photo data here
                    Toast.makeText(kamera.this, "Photo captured", Toast.LENGTH_SHORT).show();
                    mCamera.startPreview(); // Restart preview
                }
            });
        }
    }

    private void toggleRecording() {
        if (isRecording) {
            // Stop recording
            mMediaRecorder.stop();
            releaseMediaRecorder();
            mCamera.lock();
            isRecording = false;
            Toast.makeText(this, "Video saved", Toast.LENGTH_SHORT).show();
        } else {
            // Start recording
            if (prepareVideoRecorder()) {
                mMediaRecorder.start();
                isRecording = true;
                Toast.makeText(this, "Recording started", Toast.LENGTH_SHORT).show();
            } else {
                releaseMediaRecorder();
                Toast.makeText(this, "Failed to start recording", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean prepareVideoRecorder() {
        mMediaRecorder = new MediaRecorder();

        mCamera.unlock();
        mMediaRecorder.setCamera(mCamera);

        mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.CAMCORDER);
        mMediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
        mMediaRecorder.setProfile(CamcorderProfile.get(CamcorderProfile.QUALITY_HIGH));
        mMediaRecorder.setOutputFile(getOutputMediaFile().toString());
        mMediaRecorder.setPreviewDisplay(mSurfaceHolder.getSurface());

        try {
            mMediaRecorder.prepare();
        } catch (IOException e) {
            Log.e("TAG", "prepare() failed");
            releaseMediaRecorder();
            return false;
        }
        return true;
    }

    private void releaseMediaRecorder() {
        if (mMediaRecorder != null) {
            mMediaRecorder.reset();
            mMediaRecorder.release();
            mMediaRecorder = null;
            mCamera.lock();
        }
    }

    private void releaseCamera() {
        if (mCamera != null) {
            mCamera.release();
            mCamera = null;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        releaseMediaRecorder();
        releaseCamera();
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        // Open the camera and start preview when surface is created
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
            return;
        }

        try {
            mCamera = Camera.open();
            mCamera.setPreviewDisplay(holder);
            mCamera.startPreview();
        } catch (IOException e) {
            Log.e("TAG", "Error setting camera preview: " + e.getMessage());
        }
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {
        // Refresh camera preview when surface changes
        if (mSurfaceHolder.getSurface() == null) {
            return;
        }

        try {
            mCamera.stopPreview();
        } catch (Exception e) {
            Log.e("TAG", "Error stopping camera preview: " + e.getMessage());
        }

        try {
            mCamera.setPreviewDisplay(mSurfaceHolder);
            mCamera.startPreview();
        } catch (Exception e) {
            Log.e("TAG", "Error starting camera preview: " + e.getMessage());
        }
    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
        // Release camera when surface is destroyed
        releaseCamera();
    }

    private static java.io.File getOutputMediaFile() {
        // To be done
        return null;
    }

    public void kembalimenu(View view) {
        Intent moveIntent = new Intent(kamera.this, menu.class);
        startActivity(moveIntent);
        finish();
    }

}