package com.example.finalyearproject;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.finalyearproject.models.Helpers;
import com.example.finalyearproject.models.UserRegister;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.POST;

public class SignUpActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    public interface UserRegisterService {
        @POST("/api/mobileregister")
        Call<Object> createUser(@Body UserRegister userRegister);


    }
    private EditText fullNameEditText, emailEditText, passwordEditText, confirmPasswordEditText, phoneEditText;
    private Button signUpButton;
    private TextView loginTextView;

    String[] courses = { "User","Driver", "Police",
            "Ambulance" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        //=====================Spinner Content==============================================
        Spinner severitySpinner = findViewById(R.id.severity_spinner);
        // Set up the Spinner with the options
        severitySpinner.setOnItemSelectedListener(this);
        ArrayAdapter ad
                = new ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                courses);
        ad.setDropDownViewResource(
                android.R.layout
                        .simple_spinner_dropdown_item);
        severitySpinner.setAdapter(ad);

        //======================Spinner Ends Here===============================================
        fullNameEditText = findViewById(R.id.fullNameEditText);
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText);
        phoneEditText = findViewById(R.id.phoneEditText);
        signUpButton = findViewById(R.id.signupButton);
        loginTextView = findViewById(R.id.loginTextView);

        signUpButton.setOnClickListener(v -> {
            System.out.println("Button clicked");
            String fullName = fullNameEditText.getText().toString().trim();
            String email = emailEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();
            String confirmPassword = confirmPasswordEditText.getText().toString().trim();
            String phone = phoneEditText.getText().toString().trim();

            if (validateSignUp(fullName, email, password, confirmPassword, phone)) {
                signUpUser(fullName, email, password, phone);

                if(validatePhoneNumber(phone)){

                    if(isValidEmailAddress(email)){
                        Retrofit retrofit = null;
                        try {
                            retrofit = new Retrofit.Builder()
                                    .baseUrl(Helpers.connection())
                                    .addConverterFactory(GsonConverterFactory.create())
                                    .build();
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                        SignUpActivity.UserRegisterService service = retrofit.create(SignUpActivity.UserRegisterService.class);
                        UserRegister userRequest = new UserRegister();
                        userRequest.setName(fullName);
                        userRequest.setEmail(email);
                        userRequest.setPassword(password);
                        userRequest.setPhone(phone);
                        userRequest.setRole(severitySpinner.getSelectedItem().toString());

                        Call<Object>call= service.createUser(userRequest);
                        Response response = null;
                        try {
                            response = call.execute();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }


                        System.out.println(response.body());
                        if(response.isSuccessful()){
                            AlertDialog alertDialog3 = new AlertDialog.Builder(SignUpActivity.this).create();
                            alertDialog3.dismiss();
                            AlertDialog alertDialog = new AlertDialog.Builder(SignUpActivity.this).create();
                            alertDialog.setTitle("Alert");
                            alertDialog.setMessage("Registration Successful");
                            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    });
                            alertDialog.show();
                            try {
                                Thread.sleep(4000);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                            Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }else{
                        AlertDialog alertDialog3 = new AlertDialog.Builder(SignUpActivity.this).create();
                        alertDialog3.dismiss();
                        AlertDialog alertDialog = new AlertDialog.Builder(SignUpActivity.this).create();
                        alertDialog.setTitle("Alert");
                        alertDialog.setMessage("Email Not Valid \n eg myname@gmail.com");
                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                        alertDialog.show();
                    }

                }else{
                    AlertDialog alertDialog3 = new AlertDialog.Builder(SignUpActivity.this).create();
                    alertDialog3.dismiss();
                    AlertDialog alertDialog = new AlertDialog.Builder(SignUpActivity.this).create();
                    alertDialog.setTitle("Alert");
                    alertDialog.setMessage("Phone Number Not Valid \n eg +263783065525");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                }



            } else {
                AlertDialog alertDialog3 = new AlertDialog.Builder(SignUpActivity.this).create();
                alertDialog3.dismiss();
                AlertDialog alertDialog = new AlertDialog.Builder(SignUpActivity.this).create();
                alertDialog.setTitle("Alert");
                alertDialog.setMessage("Fill The Details Correctly");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
              //  Toast.makeText(SignUpActivity.this, "Please fill all the fields correctly", Toast.LENGTH_SHORT).show();
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

    public static boolean validatePhoneNumber(String phoneNumber) {
        Pattern pattern = Pattern.compile("^(\\+263|0)7[7-8|1|3][0-9]{7}$");
        Matcher matcher = pattern.matcher(phoneNumber);
        return matcher.matches();
    }

    public boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
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

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
