package com.example.finalyearproject;

import static android.content.ContentValues.TAG;

import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finalyearproject.models.LocationPost;
import com.example.finalyearproject.models.UserRequest;
import com.example.finalyearproject.models.user;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;


import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    NotificationManagerCompat notificationManagerCompat;
    Notification notification;



    public interface UserRegisterService {

        @POST("/api/register")
        Object registerUser(@Body UserRequest userRequest);

    }


    public void push(View view) {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
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
    protected void onCreate(Bundle savedInstanceState) {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
     //  setContentView(R.layout.activity_accident_detection);

        Button btnSplash = (Button) findViewById(R.id.btnSplash);
        btnSplash.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
               Intent intent = new Intent(getApplicationContext(), NonRegisteredActivity.class);
              // Intent intent = new Intent(getApplicationContext(), ResetPasswordActivity.class);
                startActivity(intent);
            }
        });





    }

    //==================Non=================================


    //=======================================================
}