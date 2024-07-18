package com.example.finalyearproject;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class AccidentDetectionActivity extends AppCompatActivity implements SensorEventListener {

    private Button startDetectionButton, stopDetectionButton, stopDetectionButtonBottom, confirmButton, cancelButton;
    private TextView accelerometerData, decibelData, accidentDetectedMessage, confirmationTimer;
    private LinearLayout sensorDataLayout, accidentAlertLayout, detectionInterface;
    private SensorManager sensorManager;
    private Sensor accelerometer;
    private MediaRecorder mediaRecorder;
    private Handler handler;
    private boolean isDetecting = false;

    private static final int PERMISSION_REQUEST_CODE = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accident_detection);

        int REQUEST_CODE = 123;

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // Permission already granted
        } else {
            // Request permission
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.RECORD_AUDIO
            }, REQUEST_CODE);
        }

        // Initialize views
        startDetectionButton = findViewById(R.id.start_detection_button);
        stopDetectionButton = findViewById(R.id.stop_detection_button);
        stopDetectionButtonBottom = findViewById(R.id.stop_detection_button_bottom);
        confirmButton = findViewById(R.id.confirm_button);
        cancelButton = findViewById(R.id.cancel_button);
        accelerometerData = findViewById(R.id.accelerometer_data);
        decibelData = findViewById(R.id.decibel_data);
        accidentDetectedMessage = findViewById(R.id.accident_detected_message);
        confirmationTimer = findViewById(R.id.confirmation_timer);
        sensorDataLayout = findViewById(R.id.sensor_data_layout);
        accidentAlertLayout = findViewById(R.id.accident_alert_layout);
        detectionInterface = findViewById(R.id.detection_interface);

        // Initialize Sensor Manager and Accelerometer
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        // Initialize Media Recorder
        mediaRecorder = new MediaRecorder();
       // mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.DEFAULT);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        mediaRecorder.setOutputFile("/dev/null");

        // Handler for timers
        handler = new Handler();

        // Set up button listeners
        startDetectionButton.setOnClickListener(v -> startDetection());
        stopDetectionButton.setOnClickListener(v -> stopDetection());
        stopDetectionButtonBottom.setOnClickListener(v -> stopDetection());
        confirmButton.setOnClickListener(v -> confirmAccident());
        cancelButton.setOnClickListener(v -> cancelAccident());

        // Request necessary permissions
        requestPermissions();
    }

    private void requestPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.BODY_SENSORS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO, Manifest.permission.BODY_SENSORS}, PERMISSION_REQUEST_CODE);
        }
    }

    private void startDetection() {
        if (isDetecting) return;

        // Start collecting sensor data
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);

        try {
            mediaRecorder.prepare();
            mediaRecorder.start();
        } catch (Exception e) {
            e.printStackTrace();
        }

        isDetecting = true;
        sensorDataLayout.setVisibility(View.VISIBLE);
        stopDetectionButton.setVisibility(View.VISIBLE);
    }

    private void stopDetection() {
        if (!isDetecting) return;

        // Stop collecting sensor data
        sensorManager.unregisterListener(this);
        mediaRecorder.stop();

        isDetecting = false;
        sensorDataLayout.setVisibility(View.GONE);
        stopDetectionButton.setVisibility(View.GONE);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            accelerometerData.setText(getString(R.string.accelerometer_data, x, y, z));

            // Check for accident conditions (example logic)
            if (Math.abs(x) > 15 || Math.abs(y) > 15 || Math.abs(z) > 15) {
                showAccidentAlert();
            }
        }
    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Do nothing
    }

    private void showAccidentAlert() {
        accidentAlertLayout.setVisibility(View.VISIBLE);
        // Start a countdown timer (example)
        handler.postDelayed(() -> {
            accidentAlertLayout.setVisibility(View.GONE);
            // Send alert to emergency services
            Toast.makeText(this, "Emergency services notified", Toast.LENGTH_SHORT).show();
        }, 120000); // 2 minutes
    }

    private void confirmAccident() {
        accidentAlertLayout.setVisibility(View.GONE);
        // Notify emergency services
        Toast.makeText(this, "Emergency services notified", Toast.LENGTH_SHORT).show();
    }

    private void cancelAccident() {
        accidentAlertLayout.setVisibility(View.GONE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults); // Add this line
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permissions granted
            } else {
                Toast.makeText(this, "Permissions are required to use this feature", Toast.LENGTH_SHORT).show();
            }
        }
    }
}


