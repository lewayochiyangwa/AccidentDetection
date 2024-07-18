package com.example.finalyearproject;

import androidx.appcompat.app.AppCompatActivity;

//import com.example.myapp.utils.DatabaseHelper;

public class ProfileActivity extends AppCompatActivity {
/*
    private static final int REQUEST_CONTACT_PERMISSION = 1001;
    private static final int REQUEST_SELECT_CONTACT = 1002;
    private static final int REQUEST_SELECT_PROFILE_IMAGE = 1003;

    private DatabaseHelper databaseHelper;
    private TextView tvFullName, tvEmail, tvAllergies, tvMedicalInsurance, tvEmergencyContact;
    private EditText etFullName, etEmail, etAllergies, etMedicalInsurance;
    private Button btnSaveChanges, btnSelectContact;
    private ImageView iconEditProfileImage, iconEditFullName, iconEditEmail, iconEditAllergies, iconEditMedicalInsurance, iconEditEmergencyContact, ivProfileImage;

    private String userId = "1"; // Assuming userId is 1 for this example, change it as needed.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        databaseHelper = new DatabaseHelper(this);

        initViews();
        loadUserData();
        setupListeners();
    }

    private void initViews() {
        tvFullName = findViewById(R.id.tv_full_name_label);
        tvEmail = findViewById(R.id.tv_email_label);
        tvAllergies = findViewById(R.id.tv_allergies_label);
        tvMedicalInsurance = findViewById(R.id.tv_medical_insurance_label);
        tvEmergencyContact = findViewById(R.id.tv_emergency_contact_label);

        etFullName = findViewById(R.id.et_full_name);
        etEmail = findViewById(R.id.et_email);
        etAllergies = findViewById(R.id.et_allergies);
        etMedicalInsurance = findViewById(R.id.et_medical_insurance);

        btnSaveChanges = findViewById(R.id.btn_save);
        btnSelectContact = findViewById(R.id.btn_select_contact);

        iconEditProfileImage = findViewById(R.id.icon_edit_profile_image);
        iconEditFullName = findViewById(R.id.icon_edit_full_name);
        iconEditEmail = findViewById(R.id.icon_edit_email);
        iconEditAllergies = findViewById(R.id.icon_edit_allergies);
        iconEditMedicalInsurance = findViewById(R.id.icon_edit_medical_insurance);


        ivProfileImage = findViewById(R.id.iv_profile_image);
    }

    private void loadUserData() {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = db.query(DatabaseHelper.TABLE_USERS, null, DatabaseHelper.KEY_ID + "=?", new String[]{userId}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            int fullNameIndex = cursor.getColumnIndex(DatabaseHelper.KEY_FULL_NAME);
            int emailIndex = cursor.getColumnIndex(DatabaseHelper.KEY_EMAIL);
            int allergiesIndex = cursor.getColumnIndex(DatabaseHelper.KEY_ALLERGIES);
            int insuranceInfoIndex = cursor.getColumnIndex(DatabaseHelper.KEY_INSURANCE_INFO);
            int emergencyContactIndex = cursor.getColumnIndex(DatabaseHelper.KEY_EMERGENCY_CONTACT);
            int profileImageIndex = cursor.getColumnIndex(DatabaseHelper.KEY_PROFILE_IMAGE);

            if (fullNameIndex >= 0) tvFullName.setText(cursor.getString(fullNameIndex));
            if (emailIndex >= 0) tvEmail.setText(cursor.getString(emailIndex));
            if (allergiesIndex >= 0) tvAllergies.setText(cursor.getString(allergiesIndex));
            if (insuranceInfoIndex >= 0) tvMedicalInsurance.setText(cursor.getString(insuranceInfoIndex));
            if (emergencyContactIndex >= 0) tvEmergencyContact.setText(cursor.getString(emergencyContactIndex));
            if (profileImageIndex >= 0) {
                String profileImageUri = cursor.getString(profileImageIndex);
                if (profileImageUri != null) {
                    ivProfileImage.setImageURI(Uri.parse(profileImageUri));
                }
            }

            cursor.close();
        }
    }

    private void setupListeners() {
        iconEditProfileImage.setOnClickListener(view -> {
            selectProfileImage();
        });

        iconEditFullName.setOnClickListener(view -> {
            toggleEditField(tvFullName, etFullName);
        });

        iconEditEmail.setOnClickListener(view -> {
            toggleEditField(tvEmail, etEmail);
        });

        iconEditAllergies.setOnClickListener(view -> {
            toggleEditField(tvAllergies, etAllergies);
        });

        iconEditMedicalInsurance.setOnClickListener(view -> {
            toggleEditField(tvMedicalInsurance, etMedicalInsurance);
        });

        iconEditEmergencyContact.setOnClickListener(view -> {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, REQUEST_CONTACT_PERMISSION);
            } else {
                selectContact();
            }
        });

        btnSelectContact.setOnClickListener(view -> selectContact());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            btnSaveChanges.setOnClickListener(view -> saveChanges());
        }
    }

    private void toggleEditField(TextView textView, EditText editText) {
        if (editText.getVisibility() == View.GONE) {
            editText.setText(textView.getText());
            editText.setVisibility(View.VISIBLE);
            textView.setVisibility(View.GONE);
        } else {
            editText.setVisibility(View.GONE);
            textView.setVisibility(View.VISIBLE);
        }
    }

    private void selectProfileImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_SELECT_PROFILE_IMAGE);
    }

    private void selectContact() {
        Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        startActivityForResult(intent, REQUEST_SELECT_CONTACT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_SELECT_CONTACT && resultCode == RESULT_OK && data != null) {
            Uri contactUri = data.getData();
            if (contactUri != null) {
                retrieveContactInfo(contactUri);
            }
        } else if (requestCode == REQUEST_SELECT_PROFILE_IMAGE && resultCode == RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();
            if (selectedImageUri != null) {
                ivProfileImage.setImageURI(selectedImageUri);
                saveProfileImage(selectedImageUri.toString());
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CONTACT_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                selectContact();
            } else {
                Toast.makeText(this, "Permission denied to access contacts", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @SuppressLint("Range")
    private void retrieveContactInfo(Uri contactUri) {
        Cursor cursor = getContentResolver().query(contactUri, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(ContactsContract.Contacts._ID);
            int nameIndex = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);

            if (idIndex >= 0 && nameIndex >= 0) {
                String id = cursor.getString(idIndex);
                String name = cursor.getString(nameIndex);

                if (cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                    Cursor phoneCursor = getContentResolver().query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            new String[]{id}, null);

                    if (phoneCursor != null && phoneCursor.moveToFirst()) {
                        int phoneNumberIndex = phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                        if (phoneNumberIndex >= 0) {
                            String phoneNumber = phoneCursor.getString(phoneNumberIndex);
                            tvEmergencyContact.setText(name + " : " + phoneNumber);
                        }
                        phoneCursor.close();
                    }
                }
            }
            cursor.close();
        }
    }

    private void saveProfileImage(String imageUri) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.KEY_PROFILE_IMAGE, imageUri);
        db.update(DatabaseHelper.TABLE_USERS, values, DatabaseHelper.KEY_ID + "=?", new String[]{userId});
        Toast.makeText(this, "Profile image updated successfully", Toast.LENGTH_SHORT).show();
    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    private void saveChanges() {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        if (etFullName.getVisibility() == View.VISIBLE) {
            values.put(DatabaseHelper.KEY_FULL_NAME, etFullName.getText().toString());
        }
        if (etEmail.getVisibility() == View.VISIBLE) {
            values.put(DatabaseHelper.KEY_EMAIL, etEmail.getText().toString());
        }
        if (etAllergies.getVisibility() == View.VISIBLE) {
            values.put(DatabaseHelper.KEY_ALLERGIES, etAllergies.getText().toString());
        }
        if (etMedicalInsurance.getVisibility() == View.VISIBLE) {
            values.put(DatabaseHelper.KEY_INSURANCE_INFO, etMedicalInsurance.getText().toString());
        }
        if (!values.isEmpty()) {
            db.update(DatabaseHelper.TABLE_USERS, values, DatabaseHelper.KEY_ID + "=?", new String[]{userId});
            Toast.makeText(this, "Profile updated successfully", Toast.LENGTH_SHORT).show();
            loadUserData();
        } else {
            Toast.makeText(this, "No changes to save", Toast.LENGTH_SHORT).show();
        }
    }

 */
}
