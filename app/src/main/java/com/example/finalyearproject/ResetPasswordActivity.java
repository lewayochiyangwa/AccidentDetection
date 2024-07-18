package com.example.finalyearproject;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;


public class ResetPasswordActivity extends AppCompatActivity {

    private EditText editTextNewPassword, editTextConfirmPassword;
    private Button buttonResetPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        editTextNewPassword = findViewById(R.id.editTextNewPassword);
        editTextConfirmPassword = findViewById(R.id.editTextConfirmPassword);
        buttonResetPassword = findViewById(R.id.buttonResetPassword);

        buttonResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //resetPassword();
            }
        });
    }

 /*   private void resetPassword() {
        String newPassword = editTextNewPassword.getText().toString().trim();
        String confirmPassword = editTextConfirmPassword.getText().toString().trim();

        // Validate passwords
        if (!newPassword.equals(confirmPassword)) {
            Toast.makeText(getApplicationContext(), "Passwords do not match", Toast.LENGTH_SHORT).show();
            return;
        }

        // Update password in database
        DatabaseHelper databaseHelper = new DatabaseHelper(getApplicationContext());
        String email = getIntent().getStringExtra("email"); // Assuming you pass the email via Intent
        boolean isPasswordUpdated = databaseHelper.updateUserPassword(email, newPassword);

        if (isPasswordUpdated) {
            // Show success message as a pop-up
            Toast.makeText(getApplicationContext(), "Password reset successful", Toast.LENGTH_SHORT).show();
            // Navigate back to login activity
            onBackPressed();
        } else {
            // Handle password update failure
            Toast.makeText(getApplicationContext(), "Failed to update password", Toast.LENGTH_SHORT).show();
        }
    }*/
}
