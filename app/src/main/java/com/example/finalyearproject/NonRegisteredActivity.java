package com.example.finalyearproject;

import static android.content.ContentValues.TAG;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.finalyearproject.databinding.ActivityMainBinding;
import com.example.finalyearproject.models.AccReported;
import com.example.finalyearproject.models.AccidentReporter;
import com.example.finalyearproject.models.Helpers;
import com.example.finalyearproject.models.UserRequest;
import com.example.finalyearproject.models.user;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.POST;

public class NonRegisteredActivity extends AppCompatActivity  implements AdapterView.OnItemSelectedListener{

    Button btnReport;
    BottomSheetDialog dialog;

    Button closeButton;
    AlertDialog.Builder builder;
    private ImageView mPhotoImageView;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private byte[] imageByteStream;
    String[] courses = { "Severe", "Moderate",
            "Minor" };
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
       // Toast.makeText(getApplicationContext(), courses[i], Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    interface AccidentReporterService {
        @POST("/api/reported")
        Call<Object> createUser(@Body AccReported userRequest);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_non_registered);




        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
        }
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.CALL_PHONE}, REQUEST_CALL_PERMISSION);
        }

        Button btnCallAmbulance = (Button) findViewById(R.id.btnCallAmbulance);
        btnCallAmbulance.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:+23054722810"));

             /*   if (ActivityCompat.checkSelfPermission(NonRegisteredActivity.this,
                        android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }*/
                startActivity(callIntent);
            }
        });

        Button btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(NonRegisteredActivity.this, LoginActivity.class);
                startActivity(intent);

            }
        });
        Button btnCallPolice = (Button) findViewById(R.id.btnCallPolice);
        btnCallPolice.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:+23054722810"));

                if (ActivityCompat.checkSelfPermission(NonRegisteredActivity.this,
                        android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                startActivity(callIntent);
            }
        });

        btnReport = findViewById(R.id.btnReport);

        dialog = new BottomSheetDialog(NonRegisteredActivity.this);
        createDialog();
        btnReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Tapinda Boyz");
                dialog.show();
            }
        });

        //========================Dialog Starts Here================================
        /*closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeButton = (Button) findViewById(R.id.button);
                builder = new AlertDialog.Builder(this);

                //Uncomment the below code to Set the message and title from the strings.xml file
                builder.setMessage(R.string.dialog_message) .setTitle(R.string.dialog_title);

                //Setting message manually and performing action on button click
                builder.setMessage("Do you want to close this application ?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                finish();
                                Toast.makeText(getApplicationContext(),"you choose yes action for alertbox",
                                        Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //  Action for 'NO' Button
                                dialog.cancel();
                                Toast.makeText(getApplicationContext(),"you choose no action for alertbox",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
                //Creating dialog box
                AlertDialog alert = builder.create();
                //Setting the title manually
                alert.setTitle("AlertDialogExample");
                alert.show();
            }
        });*/

        //========================Dialog Ends Here================================


        dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    }

    private void createDialog() {
        View view = getLayoutInflater().inflate(R.layout.bottom_dialog, null);

        //=====================Spinner Content==============================================
        Spinner severitySpinner = view.findViewById(R.id.severity_spinner);
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
        Button submit =  view.findViewById(R.id.submit);
        EditText nameField = view.findViewById(R.id.name);
        EditText phoneField = view.findViewById(R.id.phone);
        EditText messageField = view.findViewById(R.id.message);

        Button btnTakePicture = view.findViewById(R.id.btnTakePicture);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name =nameField.getText().toString();
                String phone = phoneField.getText().toString();
                String message = messageField.getText().toString();

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
                        NonRegisteredActivity.AccidentReporterService service = retrofit.create(NonRegisteredActivity.AccidentReporterService.class);
                        //   AccidentReporter userRequest = new AccidentReporter();
                        AccReported userRequest = new AccReported();
                        LocalDateTime nowToday = null;
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                            nowToday = LocalDateTime.now();
                        }
                        String b = severitySpinner.getSelectedItem().toString();

                        userRequest.setName(name);
                        userRequest.setPhone(phone);
                        userRequest.setMessage(message);
                        userRequest.setReport_time(nowToday.toString());
                        userRequest.setSeverity(severitySpinner.getSelectedItem().toString());
                        //   userRequest.setPhoto("photo");




                        try {

                            if(phone.isEmpty()){
                                AlertDialog alertDialog = new AlertDialog.Builder(NonRegisteredActivity.this).create();
                                alertDialog.setTitle("Alert");
                                alertDialog.setMessage("Can Not Post Without Phone Number");
                                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                            }
                                        });
                                alertDialog.show();
                            }else{
                                Call<Object> call= service.createUser(userRequest);
                                Response response = call.execute();
                                System.out.println(response.body());
                                user u = new Gson().fromJson(response.body().toString(), user.class);
                                System.out.println(u.getEmail());

                                AlertDialog alertDialog3 = new AlertDialog.Builder(NonRegisteredActivity.this).create();
                                alertDialog3.dismiss();
                                AlertDialog alertDialog = new AlertDialog.Builder(NonRegisteredActivity.this).create();
                                alertDialog.setTitle("Alert");
                                alertDialog.setMessage("Report Submitted Successfully");
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


                    }else{
                        AlertDialog alertDialog3 = new AlertDialog.Builder(NonRegisteredActivity.this).create();
                        alertDialog3.dismiss();
                        AlertDialog alertDialog = new AlertDialog.Builder(NonRegisteredActivity.this).create();
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


                dialog.dismiss();
              //  Toast.makeText(NonRegisteredActivity.this, "Thank you for reporting", Toast.LENGTH_SHORT).show();
            }
        });

        btnTakePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivityForResult(new Intent(MediaStore.ACTION_IMAGE_CAPTURE), REQUEST_IMAGE_CAPTURE);
             //   dialog.dismiss();
            //    Toast.makeText(NonRegisteredActivity.this, "Thank you for reporting", Toast.LENGTH_SHORT).show();
            }
        });
        dialog.setContentView(view);
    }

    //return a url for the image
    private Uri createUrl(){
        File imageFile= new File(getApplicationContext().getFilesDir(), "camera_photo.jpg");
        return FileProvider.getUriForFile(
                getApplicationContext(),
                "com.example.finalyearproject.fileprovider",
                imageFile);
    }
//======================================

    private static final int REQUEST_CAMERA_PERMISSION = 123;

    private static final int REQUEST_CALL_PERMISSION = 133;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
      /*  if (requestCode == REQUEST_IMAGE_CAPTURE) {

            switch (resultCode) {
                case RESULT_OK:
                    Log.v(TAG, "Picture taken! :)");
                    if (data != null) {
                        Bitmap bitmap = data.getParcelableExtra("data");
                        mPhotoImageView.setImageBitmap(bitmap);
                    }
                    break;
                case RESULT_CANCELED:
                    Log.v(TAG, "Picture canceled! :(");
                    break;
            }
        }

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            // Get the captured image data
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");

            // Convert the Bitmap to a byte array
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            imageByteStream = stream.toByteArray();

            // Do something with the byte stream, such as upload it to a server
            // ...
        }*/

    }

    //======================================

    public static boolean validatePhoneNumber(String phoneNumber) {
        Pattern pattern = Pattern.compile("^(\\+263|0)7[7-8|1|3][0-9]{7}$");
        Matcher matcher = pattern.matcher(phoneNumber);
        return matcher.matches();
    }

}