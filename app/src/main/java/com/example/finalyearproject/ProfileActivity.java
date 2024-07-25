package com.example.finalyearproject;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.finalyearproject.models.Helpers;
import com.example.finalyearproject.models.ProfileUpdate;
import com.example.finalyearproject.models.UserRequest;
import com.example.finalyearproject.models.user;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.POST;

//import com.example.myapp.utils.DatabaseHelper;

public class ProfileActivity extends AppCompatActivity {


    public interface UserService {
        @POST("/api/profile_update")
        Call<Object> createUser(@Body ProfileUpdate userRequest);


    }
    private static final int REQUEST_CONTACT_PERMISSION = 1001;
    private static final int REQUEST_SELECT_CONTACT = 1002;
    private static final int REQUEST_SELECT_PROFILE_IMAGE = 1003;


    private TextView tvFullName, tvEmail, tvAllergies, tvMedicalInsurance, tvEmergencyContact;
    private EditText etFullName, etEmail, etAllergies, etMedicalInsurance;
    private Button btnSaveChanges, btnSelectContact;
    private ImageView iconEditProfileImage, iconEditFullName, iconEditEmail, iconEditAllergies, iconEditMedicalInsurance, iconEditEmergencyContact, ivProfileImage;

    private String userId = "1"; // Assuming userId is 1 for this example, change it as needed.

    public boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);





        Button btn = (Button) findViewById(R.id.submit);
        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                EditText nameEditText = findViewById(R.id.name);
                String name = nameEditText.getText().toString();

                EditText emailEditText = findViewById(R.id.email);
                String email = emailEditText.getText().toString();

                EditText allegyEditText = findViewById(R.id.allegy);
                String allegy = emailEditText.getText().toString();

                EditText phoneEditText = findViewById(R.id.contacts);
                String phone = phoneEditText.getText().toString();

                EditText medicEditText = findViewById(R.id.medic);
                String medic = medicEditText.getText().toString();


                if(isValidEmailAddress(email)){

            if(validatePhoneNumber(phone)){
                Retrofit retrofit = null;
                try {
                    retrofit = new Retrofit.Builder()
                            .baseUrl(Helpers.connection())
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                ProfileActivity.UserService service = retrofit.create(ProfileActivity.UserService.class);
                ProfileUpdate userRequest = new ProfileUpdate();

                SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(ProfileActivity.this);
                // String usernamew = sharedPref.getString("username", "");
                int idw = sharedPref.getInt("id", -1);

                //  userRequest.setEmail(email);
                //  userRequest.setPassword(pass);
                userRequest.setName(name);
                userRequest.setAllergies(allegy);
                userRequest.setMedical_insurance(medic);
                userRequest.setEmergency_contact(phone);
                userRequest.setId(idw);



                try {
                    AlertDialog alertDialog3 = new AlertDialog.Builder(ProfileActivity.this).create();
                     /*   alertDialog3.setTitle("Alert");
                        alertDialog3.setMessage("Loading...");
                        alertDialog3.show();*/
                    Call<Object> call= service.createUser(userRequest);
                    System.out.println(userRequest);
                    Response response = call.execute();
                    System.out.println(response.body());

                    if(response.body()==null){
                        alertDialog3.dismiss();
                        AlertDialog alertDialog = new AlertDialog.Builder(ProfileActivity.this).create();
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
                        AlertDialog alertDialog = new AlertDialog.Builder(ProfileActivity.this).create();
                        alertDialog.setTitle("Alert");
                        alertDialog.setMessage("Profile Updated Successfully");
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
            }else{
                AlertDialog alertDialog3 = new AlertDialog.Builder(ProfileActivity.this).create();
                alertDialog3.dismiss();
                AlertDialog alertDialog = new AlertDialog.Builder(ProfileActivity.this).create();
                alertDialog.setTitle("Alert");
                alertDialog.setMessage("Phone Number Not Valid");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();

            }

                    // Intent intent = new Intent(MainActivity.this, AccidentsListAttendanceActivity.class);
                    // Intent intent = new Intent(MainActivity.this, LocationActivity.class);
                }else{
                    AlertDialog alertDialog3 = new AlertDialog.Builder(ProfileActivity.this).create();
                    alertDialog3.dismiss();
                    AlertDialog alertDialog = new AlertDialog.Builder(ProfileActivity.this).create();
                    alertDialog.setTitle("Alert");
                    alertDialog.setMessage("Email Not Valid");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();

                }


            }
        });

    }



    public static boolean validatePhoneNumber(String phoneNumber) {
        Pattern pattern = Pattern.compile("^(\\+263|0)7[7-8|1|3][0-9]{7}$");
        Matcher matcher = pattern.matcher(phoneNumber);
        return matcher.matches();
    }





}
