package com.nyok.bottom_navigation.menu_dalam;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.nyok.bottom_navigation.database.DatabaseHelperLogin;
import com.nyok.bottom_navigation.databinding.ActivityEditProfilBinding;

public class EditProfil extends AppCompatActivity {

    private ActivityEditProfilBinding binding;
    private DatabaseHelperLogin db;
    public static final String SHARED_PREF_NAME = "myPref";
    private SharedPreferences sharedPreferences;
    private static final String PREFS_NAME = "profile_prefs";
    private static final String IMAGE_URI_KEY = "image_uri";

    private final ActivityResultLauncher<Intent> imagePickerLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Uri uri = result.getData().getData();
                    if (uri != null) {
                        binding.imageProfil.setImageURI(uri);
                        saveImageUri(uri);
                        Log.d("EditProfil", "Image URI: " + uri.toString());
                    } else {
                        Log.e("EditProfil", "URI is null");
                    }
                } else {
                    Log.e("EditProfil", "Failed to pick image");
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditProfilBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = new DatabaseHelperLogin(this);
        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        checkAndRequestPermissions();

        Uri savedImageUri = getImageUri();
        if (savedImageUri != null) {
            binding.imageProfil.setImageURI(savedImageUri);
        }

        binding.floatingActionButton2.setOnClickListener(v -> {
            ImagePicker.with(EditProfil.this)
                    .crop()
                    .compress(1024)
                    .maxResultSize(1080, 1080)
                    .createIntent(intent -> {
                        imagePickerLauncher.launch(intent);
                        return null;
                    });
        });
        
        // Set data profil
        binding.btnSave.setOnClickListener(v -> {
            String username = binding.etUsername.getText().toString();
            String email = binding.etEmail.getText().toString();
            String phone = binding.etPhone.getText().toString();
            String gender = binding.etGender.getText().toString();
            String dob = binding.etDOB.getText().toString();

            Toast.makeText(this, "Profile Saved:\n"
                    + "Username: " + username + "\n"
                    + "Email: " + email + "\n"
                    + "Phone: " + phone + "\n"
                    + "Gender: " + gender + "\n"
                    + "DOB: " + dob, Toast.LENGTH_LONG).show();
        });
    }

    private void checkAndRequestPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA},
                    100);
        }
    }

    private void saveImageUri(Uri uri) {
        SharedPreferences.Editor editor = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit();
        editor.putString(IMAGE_URI_KEY, uri.toString());
        editor.apply();
    }

    private Uri getImageUri() {
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String uriString = sharedPreferences.getString(IMAGE_URI_KEY, null);
        if (uriString != null) {
            return Uri.parse(uriString);
        }
        return null;
    }
}
