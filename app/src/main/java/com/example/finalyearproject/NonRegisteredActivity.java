package com.example.finalyearproject;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.finalyearproject.databinding.ActivityMainBinding;
import com.example.finalyearproject.models.AccidentReporter;
import com.example.finalyearproject.models.UserRequest;
import com.example.finalyearproject.models.user;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.POST;

public class NonRegisteredActivity extends AppCompatActivity {

    Button btnReport;
    BottomSheetDialog dialog;
    private ImageView mPhotoImageView;

    interface AccidentReporterService {
        @POST("/api/accidents")
        Call<Object> createUser(@Body AccidentReporter userRequest);
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
                callIntent.setData(Uri.parse("tel:+263783065525"));

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
                callIntent.setData(Uri.parse("tel:+263783065525"));

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




        dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    }

    private void createDialog() {
        View view = getLayoutInflater().inflate(R.layout.bottom_dialog, null);

        Button submit = view.findViewById(R.id.submit);
        EditText nameField = view.findViewById(R.id.name);
        EditText phoneField = view.findViewById(R.id.phone);
        EditText messageField = view.findViewById(R.id.message);

        Button btnTakePicture = view.findViewById(R.id.btnTakePicture);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int name = Integer.parseInt(nameField.getText().toString());
                String phone = phoneField.getText().toString();
                String message = messageField.getText().toString();

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://eb43-2c0f-f8f0-d348-0-3c63-96ae-d540-162.ngrok-free.app/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                NonRegisteredActivity.AccidentReporterService service = retrofit.create(NonRegisteredActivity.AccidentReporterService.class);
                AccidentReporter userRequest = new AccidentReporter();
                userRequest.setReporter_id(name);
                userRequest.setLocation(phone);
                userRequest.setSeverity(message);
                userRequest.setPhoto("photo");

                try {
                    Call<Object> call= service.createUser(userRequest);
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


                dialog.dismiss();
                Toast.makeText(NonRegisteredActivity.this, "Thank you for reporting", Toast.LENGTH_SHORT).show();
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
private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_CAMERA_PERMISSION = 123;

    private static final int REQUEST_CALL_PERMISSION = 133;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE) {

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

    }

    //======================================

}