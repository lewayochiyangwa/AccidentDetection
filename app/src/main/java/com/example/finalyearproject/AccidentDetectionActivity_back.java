package com.example.finalyearproject;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationManager;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.finalyearproject.models.LocationPost;
import com.example.finalyearproject.models.user;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.POST;

public class AccidentDetectionActivity_back extends AppCompatActivity implements SensorEventListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle actionBarDrawerToggle;
    private Button goToListButton,startDetectionButton, stopDetectionButton, stopDetectionButtonBottom, confirmButton, cancelButton;
    private TextView accelerometerData, decibelData, accidentDetectedMessage, confirmationTimer;
    private LinearLayout sensorDataLayout, accidentAlertLayout, detectionInterface;
    private SensorManager sensorManager;
    private Sensor accelerometer;
    private MediaRecorder mediaRecorder;
    private Handler handler;
    private boolean isDetecting = false;

    private static final int PERMISSION_REQUEST_CODE = 1001;

    NotificationManagerCompat notificationManagerCompat;
    Notification notification;

    private static String latitude;
    private static String longitude;

    public interface LocationPostService {
        @POST("/api/accidents2")
        Call<Object> postAccidentLocation(@Body LocationPost locationPost);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_accident_detection);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();


     /*   navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.nav_home:
                        Toast.makeText(AccidentDetectionActivity_back.this, "Home", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(AccidentDetectionActivity_back.this, MainActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.maps:
                        Toast.makeText(AccidentDetectionActivity_back.this, "Maps", Toast.LENGTH_SHORT).show();
                        Intent intent2 = new Intent(AccidentDetectionActivity_back.this, LoginActivity.class);
                        startActivity(intent2);
                        break;
                    case R.id.profile:
                        Toast.makeText(AccidentDetectionActivity_back.this, "Profile", Toast.LENGTH_SHORT).show();
                        Intent intent3 = new Intent(AccidentDetectionActivity_back.this, LoginActivity.class);
                        startActivity(intent3);
                        break;
                    case R.id.accidents:
                        Toast.makeText(AccidentDetectionActivity_back.this, "Accidents", Toast.LENGTH_SHORT).show();
                        Intent intent4 = new Intent(AccidentDetectionActivity_back.this, AccidentsListAttendanceActivity.class);
                        startActivity(intent4);
                        break;
                    case R.id.logout:
                        Toast.makeText(AccidentDetectionActivity_back.this, "Logout", Toast.LENGTH_SHORT).show();
                        Intent intent5 = new Intent(AccidentDetectionActivity_back.this, LoginActivity.class);
                        startActivity(intent5);
                        break;
                }
                return false;
            }
        });*/



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
        goToListButton = findViewById(R.id.accidents_list_button);
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
        goToListButton.setOnClickListener(v -> {
            Intent intent = new Intent(AccidentDetectionActivity_back.this, AccidentsListAttendanceActivity.class);
            startActivity(intent);
        });
        startDetectionButton.setOnClickListener(v -> startDetection());
        stopDetectionButton.setOnClickListener(v -> stopDetection());
        stopDetectionButtonBottom.setOnClickListener(v -> stopDetection());
        confirmButton.setOnClickListener(v -> confirmAccident(v));
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

    private void confirmAccident(View v) {
        accidentAlertLayout.setVisibility(View.GONE);

        //======================Firebase Notification====================================
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel("accident_detection", "accident_detection", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(AccidentDetectionActivity_back.this, "accident_detection")
                .setContentTitle("Accident Detection")
                .setSmallIcon(R.drawable.baseline_notifications_active_24)
                .setAutoCancel(true)
                .setContentText("Alert, Accident Might Have Happened !");

        notification = builder.build();
        notificationManagerCompat = NotificationManagerCompat.from(AccidentDetectionActivity_back.this);

        push(v);
        //=================================================================

        getLastLocation();
        // Notify emergency services

        //========================Post Location=====================================
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://f73f-41-79-132-218.ngrok-free.app/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        try {
            AccidentDetectionActivity_back.LocationPostService service = retrofit.create(AccidentDetectionActivity_back.LocationPostService.class);
            LocationPost locationPostBody = new LocationPost();
            locationPostBody.setLatitude(latitude);
            locationPostBody.setLongitude(longitude);
            locationPostBody.setUser_id("xc");


            Call<Object> call= service.postAccidentLocation(locationPostBody);
            //  retrofit2.Response response = call.execute();
            Response response = call.execute();
            //Object response = call.execute();
            System.out.println(response.body());
            user u = new Gson().fromJson(response.body().toString(), user.class);
            System.out.println(u.getEmail());

        } catch (IOException e) {
            Exception cv = e;
            System.out.println(cv);

        } catch (Exception ex) {
            Exception cv = ex;
            System.out.println(cv);
        }

        //==============================================================
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


    //===============================Get the location of the user=====================================================================
    int PERMISSION_ID = 44;
    FusedLocationProviderClient mFusedLocationClient;
    TextView latTextView, lonTextView;
    @SuppressLint("MissingPermission")
    private void getLastLocation(){
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                mFusedLocationClient.getLastLocation().addOnCompleteListener(
                        new OnCompleteListener<Location>() {
                            @Override
                            public void onComplete(@NonNull Task<Location> task) {
                                Location location = task.getResult();
                                if (location == null) {
                                    requestNewLocationData();
                                } else {
                                    System.out.println("=============Location===============");
                                    System.out.println(location.getLatitude());
                                    System.out.println(location.getLongitude());

                                    latitude = String.valueOf(location.getLatitude());
                                    longitude = String.valueOf(location.getLongitude());

                                    //   latTextView.setText(location.getLatitude()+"");
                                    //  lonTextView.setText(location.getLongitude()+"");
                                }
                            }
                        }
                );
            } else {
                Toast.makeText(this, "Turn on location", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        } else {
            requestPermissions();
        }
    }
    @SuppressLint("MissingPermission")
    private void requestNewLocationData(){

        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(0);
        mLocationRequest.setFastestInterval(0);
        mLocationRequest.setNumUpdates(1);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mFusedLocationClient.requestLocationUpdates(
                mLocationRequest, mLocationCallback,
                Looper.myLooper()
        );

    }

    private LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location mLastLocation = locationResult.getLastLocation();
            System.out.println("=============Location2===============");
            System.out.println(mLastLocation.getLongitude());
            System.out.println(mLastLocation.getLatitude());
            //  latTextView.setText(mLastLocation.getLatitude()+"");
            //  lonTextView.setText(mLastLocation.getLongitude()+"");
        }
    };

    private boolean checkPermissions() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        return false;
    }

    private void requestPermissions2() {
        ActivityCompat.requestPermissions(
                this,
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                PERMISSION_ID
        );
    }

    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
                LocationManager.NETWORK_PROVIDER
        );
    }

    /*
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_ID) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            }
        }
    }
*/
    @Override
    public void onResume(){
        super.onResume();
        if (checkPermissions()) {
            getLastLocation();
        }

    }


    //====================================================================================================


    public void push(View view) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        notificationManagerCompat.notify(1, notification);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }

    }
}


