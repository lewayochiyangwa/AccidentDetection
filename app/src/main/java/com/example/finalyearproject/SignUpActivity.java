package com.example.finalyearproject;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.finalyearproject.api.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {

    private EditText fullNameEditText, emailEditText, passwordEditText, confirmPasswordEditText, phoneEditText;
    private Button signUpButton;
    private TextView loginTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        fullNameEditText = findViewById(R.id.fullNameEditText);
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText);
        phoneEditText = findViewById(R.id.phoneEditText);
        signUpButton = findViewById(R.id.signupButton);
        loginTextView = findViewById(R.id.loginTextView);

        signUpButton.setOnClickListener(v -> {
            String fullName = fullNameEditText.getText().toString().trim();
            String email = emailEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();
            String confirmPassword = confirmPasswordEditText.getText().toString().trim();
            String phone = phoneEditText.getText().toString().trim();

            if (validateSignUp(fullName, email, password, confirmPassword, phone)) {
                signUpUser(fullName, email, password, phone);
            } else {
                Toast.makeText(SignUpActivity.this, "Please fill all the fields correctly", Toast.LENGTH_SHORT).show();
            }
        });

        loginTextView.setOnClickListener(v -> {
            Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
            startActivity(intent);
        });
    }

    private boolean validateSignUp(String fullName, String email, String password, String confirmPassword, String phone) {
        return !fullName.isEmpty() && !email.isEmpty() && !password.isEmpty() && password.equals(confirmPassword) && !phone.isEmpty();
    }

    private void signUpUser(String fullName, String email, String password, String phone) {
    /*    ApiService apiService = RetrofitClient.getInstance().getApi();
        User newUser = new User(fullName, email, password, phone);

        Call<User> call = apiService.signUp(newUser);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Handle successful sign up
                    Toast.makeText(SignUpActivity.this, "Sign Up Successful!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    // Handle sign up failure
                    Toast.makeText(SignUpActivity.this, "Sign Up Failed. Please try again.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                // Handle failure to connect or other errors
                Toast.makeText(SignUpActivity.this, "Sign Up Failed. Please check your internet connection.", Toast.LENGTH_SHORT).show();
            }
        });*/
    }
}
