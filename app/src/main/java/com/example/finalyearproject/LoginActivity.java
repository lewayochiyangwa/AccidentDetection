package com.example.finalyearproject;

import static android.app.PendingIntent.getActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.finalyearproject.models.Helpers;
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

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        setContentView(R.layout.activity_login);


        Button btn = (Button) findViewById(R.id.loginButton);
        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                Retrofit retrofit = null;
                try {
                    retrofit = new Retrofit.Builder()
                            .baseUrl(Helpers.connection())
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                UserService service = retrofit.create(UserService.class);
                UserRequest userRequest = new UserRequest();
                EditText emailEditText = findViewById(R.id.emailEditText);
                String email = emailEditText.getText().toString();
                EditText passwordEditText = findViewById(R.id.passwordEditText);
                String pass = passwordEditText.getText().toString();
            //    userRequest.setEmail(email);
             //   userRequest.setPassword(pass);
                userRequest.setEmail("Annotida");
                userRequest.setPassword("Annotida");


                try {
                    AlertDialog alertDialog3 = new AlertDialog.Builder(LoginActivity.this).create();
                    alertDialog3.setTitle("Alert");
                    alertDialog3.setMessage("Loading...");
                    alertDialog3.show();
                    Call<Object>call= service.createUser(userRequest);
                    Response response = call.execute();
                    System.out.println(response.body());

                    if(response.body()==null){
                        alertDialog3.dismiss();
                        AlertDialog alertDialog = new AlertDialog.Builder(LoginActivity.this).create();
                        alertDialog.setTitle("Alert");
                        alertDialog.setMessage("Username Or Password Wrong");
                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                        alertDialog.show();
                    }
                    user u = new Gson().fromJson(response.body().toString(), user.class);
                    System.out.println(u.getEmail());
                    int idval = u.getId();
                    if(idval!=0){
                        alertDialog3.dismiss();
                        Intent intent = new Intent(LoginActivity.this, MainActivity2.class);
                        intent.putExtra("username", u.getEmail());
                        intent.putExtra("id", u.getId());
                        intent.putExtra("role", u.getRole());

                     //   SharedPreferences sharedPref = LoginActivity.this.getPreferences(Context.MODE_PRIVATE);
                        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putString("username", u.getEmail());
                        editor.putInt("id", u.getId());
                        editor.putString("role", u.getRole());
                        editor.apply();


                        startActivity(intent);
                    }else{
                        alertDialog3.dismiss();
                        AlertDialog alertDialog = new AlertDialog.Builder(LoginActivity.this).create();
                        alertDialog.setTitle("Alert");
                        alertDialog.setMessage("Username Or Password Wrong");
                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                        alertDialog.show();
                    }




                } catch (IOException e) {
                    Exception cv = e;
                    System.out.println(cv);

                } catch (Exception ex) {
                    Exception cv = ex;
                    System.out.println(cv);
                }

                // Intent intent = new Intent(MainActivity.this, AccidentsListAttendanceActivity.class);
                // Intent intent = new Intent(MainActivity.this, LocationActivity.class);
            }
        });

        TextView signupTextView = findViewById(R.id.signupTextView);
        signupTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

    }
}