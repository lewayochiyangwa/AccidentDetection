package com.example.finalyearproject;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.finalyearproject.models.Helpers;
import com.example.finalyearproject.models.PassReset;
import com.example.finalyearproject.models.ProfileUpdate;
import com.example.finalyearproject.models.UserRequest;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.POST;


public class ResetPasswordActivity extends AppCompatActivity {

    private EditText editTextNewPassword, editTextConfirmPassword;
    private Button buttonResetPassword;
    private Button back;

    public interface UserService {
        @POST("/api/reset")
        Call<Object> createUser(@Body PassReset userRequest);


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_reset_password);


        buttonResetPassword = findViewById(R.id.buttonResetPassword);

        back= findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResetPasswordActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        buttonResetPassword.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                EditText editTextEmail = findViewById(R.id.email);
                String email = editTextEmail.getText().toString();


                editTextNewPassword = findViewById(R.id.pass);
                String pass = editTextNewPassword.getText().toString();

                editTextConfirmPassword = findViewById(R.id.confirmPassword);
                String confirm = editTextConfirmPassword.getText().toString();

                buttonResetPassword = findViewById(R.id.buttonResetPassword);

                if (!confirm.equals(pass)) {

                    AlertDialog alertDialog3 = new AlertDialog.Builder(ResetPasswordActivity.this).create();
                    alertDialog3.dismiss();
                    AlertDialog alertDialog = new AlertDialog.Builder(ResetPasswordActivity.this).create();
                    alertDialog.setTitle("Alert");
                    alertDialog.setMessage("Password Does Not Match");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                }else{
                    Retrofit retrofit = null;
                    try {
                        retrofit = new Retrofit.Builder()
                                .baseUrl(Helpers.connection())
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    ResetPasswordActivity.UserService service = retrofit.create(ResetPasswordActivity.UserService.class);
                    PassReset userRequest = new PassReset();
                    userRequest.setEmail(email);
                    userRequest.setPassword(pass);
                    try {
                        AlertDialog alertDialog3 = new AlertDialog.Builder(ResetPasswordActivity.this).create();
                        Call<Object> call= service.createUser(userRequest);
                        System.out.println(userRequest);
                        Response response = call.execute();
                        System.out.println(response.body());

                        if(response.body()==null){
                            alertDialog3.dismiss();
                            AlertDialog alertDialog = new AlertDialog.Builder(ResetPasswordActivity.this).create();
                            alertDialog.setTitle("Alert");
                            alertDialog.setMessage("Something Went Wrong");
                            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    });
                            alertDialog.show();
                        }else{

                            alertDialog3.dismiss();
                            AlertDialog alertDialog = new AlertDialog.Builder(ResetPasswordActivity.this).create();
                            alertDialog.setTitle("Alert");
                            alertDialog.setMessage("Password Updated Successfully");
                            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    });
                            alertDialog.show();
                        }
                        //  user u = new Gson().fromJson(response.body().toString(), user.class);
                        //   System.out.println(u.getEmail());
                        //    int idval = u.getId();

                    } catch (IOException e) {
                        Exception cv = e;
                        System.out.println(cv);

                    } catch (Exception ex) {
                        Exception cv = ex;
                        System.out.println(cv);
                    }
                }





            }
        });

    }


}
