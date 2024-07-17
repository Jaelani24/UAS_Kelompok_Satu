package com.example.konversimatauang;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class kompas  extends AppCompatActivity implements SensorEventListener {

    private ImageView imgCompass;
    private TextView tvHeading;

    private SensorManager sensorManager;
    private Sensor sensorAccelerometer;
    private Sensor sensorMagneticField;

    private float[] accelerometerData = new float[3];
    private float[] magneticFieldData = new float[3];

    private float[] rotationMatrix = new float[9];
    private float[] orientationAngles = new float[3];

    private float currentDegree = 0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kompas);

        imgCompass = findViewById(R.id.imgCompass);
        tvHeading = findViewById(R.id.tvHeading);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensorAccelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorMagneticField = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
    }

        @Override
        protected void onResume () {
            super.onResume();
            sensorManager.registerListener(this, sensorAccelerometer, SensorManager.SENSOR_DELAY_UI);
            sensorManager.registerListener(this, sensorMagneticField, SensorManager.SENSOR_DELAY_UI);
        }

        @Override
        protected void onPause () {
            super.onPause();
            sensorManager.unregisterListener(this);
        }

        @Override
        public void onSensorChanged (SensorEvent event){
            if (event.sensor == sensorAccelerometer) {
                System.arraycopy(event.values, 0, accelerometerData, 0, event.values.length);
            } else if (event.sensor == sensorMagneticField) {
                System.arraycopy(event.values, 0, magneticFieldData, 0, event.values.length);
            }

            updateOrientation();
        }

        @Override
        public void onAccuracyChanged (Sensor sensor,int accuracy){
            // Not used, but required to implement SensorEventListener
        }

        private void updateOrientation () {
            SensorManager.getRotationMatrix(rotationMatrix, null, accelerometerData, magneticFieldData);
            SensorManager.getOrientation(rotationMatrix, orientationAngles);

            float azimuthInRadians = orientationAngles[0];
            float azimuthInDegrees = (float) Math.toDegrees(azimuthInRadians);

            RotateAnimation rotateAnimation = new RotateAnimation(
                    currentDegree,
                    -azimuthInDegrees,
                    Animation.RELATIVE_TO_SELF, 0.5f,
                    Animation.RELATIVE_TO_SELF, 0.5f);

            rotateAnimation.setDuration(250);
            rotateAnimation.setFillAfter(true);

            imgCompass.startAnimation(rotateAnimation);

            currentDegree = -azimuthInDegrees;

            tvHeading.setText("Heading: " + String.format("%.2f", azimuthInDegrees) + "°");
        }


    public void kembalimenu(View view) {
        Intent moveIntent = new Intent(kompas.this, menu.class);
        startActivity(moveIntent);
        finish();
    }

}
