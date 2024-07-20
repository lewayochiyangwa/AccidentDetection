package com.example.finalyearproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.finalyearproject.models.UserRequest;
import com.example.finalyearproject.models.user;
import com.google.gson.Gson;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.POST;

public class LoginActivity extends AppCompatActivity {

    public interface UserService {
        @POST("/api/login")
        Call<Object> createUser(@Body UserRequest userRequest);


    }

    private EditText emailEditText, passwordEditText;
    private Button loginButton;
    private Button policeButton;
    private Button ambulanceButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


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
              //  userRequest.setEmail(email);
              //  userRequest.setPassword(pass);
                userRequest.setEmail("Annotida");
                userRequest.setPassword("Annotida");
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
                Intent intent = new Intent(LoginActivity.this, MainActivity2.class);
                startActivity(intent);
                // Intent intent = new Intent(MainActivity.this, AccidentsListAttendanceActivity.class);
                // Intent intent = new Intent(MainActivity.this, LocationActivity.class);
            }
        });

    }
}