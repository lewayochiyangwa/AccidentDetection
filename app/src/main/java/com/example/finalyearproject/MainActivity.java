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
import android.widget.Toast;

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

    public interface UserService {
        @POST("/api/login")
        Call<Object> createUser(@Body UserRequest userRequest);


    }

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
        setContentView(R.layout.activity_login);

        FirebaseMessaging.getInstance().subscribeToTopic("AccidentDetection")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = "Subscribed33";
                        if (!task.isSuccessful()) {
                            msg = "Failed";
                        }
                     //   Log.d(TAG, msg);
                     //   Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });





        Button btn = (Button) findViewById(R.id.loginButton);
        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                //======================Firebase Notification====================================
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                    NotificationChannel channel = new NotificationChannel("accident_detection", "accident_detection", NotificationManager.IMPORTANCE_DEFAULT);
                    NotificationManager manager = getSystemService(NotificationManager.class);
                    manager.createNotificationChannel(channel);

                }

                NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.this, "accident_detection")
                        .setContentTitle("Hezvoko")
                        .setSmallIcon(R.drawable.baseline_notifications_active_24)
                        .setAutoCancel(true)
                        .setContentText("Hezvoko Ngondo Ngondo");

                notification = builder.build();
                notificationManagerCompat = NotificationManagerCompat.from(MainActivity.this);

                push(v);
                //=================================================================

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://78fc-41-60-89-155.ngrok-free.app/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                UserService service = retrofit.create(UserService.class);
                UserRequest userRequest = new UserRequest();
                userRequest.setEmail("Annotida");
                userRequest.setPassword("Annotida");
                try {
                    Call<Object>call= service.createUser(userRequest);
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

                Intent intent = new Intent(MainActivity.this, AccidentDetectionActivity.class);
                startActivity(intent);
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

    }
}