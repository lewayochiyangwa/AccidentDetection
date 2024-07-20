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

        Button btnSplash = (Button) findViewById(R.id.btnSplash);
        btnSplash.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), NonRegisteredActivity.class);
                startActivity(intent);
            }
        });
//activity_splash
      /*  Button ambuBtn = (Button) findViewById(R.id.btnSplash);
        ambuBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AmbulanceLoginActivity.class);
                startActivity(intent);
            }
        });*/
/*
        Button btn = (Button) findViewById(R.id.loginButton);
        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://eb43-2c0f-f8f0-d348-0-3c63-96ae-d540-162.ngrok-free.app/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                UserService service = retrofit.create(UserService.class);
                UserRequest userRequest = new UserRequest();
                EditText emailEditText = findViewById(R.id.emailEditText);
                String email = emailEditText.getText().toString();
                EditText passwordEditText = findViewById(R.id.passwordEditText);
                String pass = passwordEditText.getText().toString();
                userRequest.setEmail(email);
                userRequest.setPassword(pass);
                try {
                    Call<Object>call= service.createUser(userRequest);
                    Response response = call.execute();
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
                Intent intent = new Intent(MainActivity.this, AccidentDetectionActivity.class);
                startActivity(intent);
                // Intent intent = new Intent(MainActivity.this, AccidentsListAttendanceActivity.class);
                // Intent intent = new Intent(MainActivity.this, LocationActivity.class);
            }
        });

        Button btnPolice = (Button) findViewById(R.id.policeButton);
        btnPolice.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PoliceLoginActivity.class);
                startActivity(intent);
            }
        });

        Button ambuBtn = (Button) findViewById(R.id.ambulanceButton);
        ambuBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AmbulanceLoginActivity.class);
                startActivity(intent);
            }
        });
*/


    }

    //==================Non=================================


    //=======================================================
}