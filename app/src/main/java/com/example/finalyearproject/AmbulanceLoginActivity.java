package com.example.finalyearproject;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AmbulanceLoginActivity extends AppCompatActivity {
    private EditText surnameEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private Retrofit retrofit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ambulance_login);

        surnameEditText = findViewById(R.id.surname);
        passwordEditText = findViewById(R.id.password);
        loginButton = findViewById(R.id.login_button);

        retrofit = new Retrofit.Builder()
                .baseUrl("https://your-server-url.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();



        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String surname = surnameEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                if (!surname.isEmpty() && !password.isEmpty()) {
                 //   login(surname, password);
                } else {
                    Toast.makeText(AmbulanceLoginActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

  /*  private void login(String surname, String password) {
        Map<String, String> fields = new HashMap<>();
        fields.put("table", "ambulance_teams");
        fields.put("surname", surname);
        fields.put("password", password);

        Call<ResponseBody> call = apiService.login(fields);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        String responseBody = response.body().string();
                        JSONObject jsonObject = new JSONObject(responseBody);
                        String status = jsonObject.getString("status");

                        if (status.equals("success")) {
                            Toast.makeText(AmbulanceLoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                            // Navigate to the next screen
                        } else {
                            Toast.makeText(AmbulanceLoginActivity.this, "Invalid credentials", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(AmbulanceLoginActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(AmbulanceLoginActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
            }
        });
    }*/
}
