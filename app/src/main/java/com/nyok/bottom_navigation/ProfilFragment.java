package com.nyok.bottom_navigation;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ProfilFragment extends Fragment {

    ImageView imageView;
    FloatingActionButton button;

    // Key untuk menyimpan URI gambar di SharedPreferences
    private static final String PREFS_NAME = "profile_prefs";
    private static final String IMAGE_URI_KEY = "image_uri";

    // Periksa dan minta izin yang diperlukan
    private void checkAndRequestPermissions() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(requireActivity(),
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA},
                    100);
        }
    }

    // Simpan URI gambar ke SharedPreferences
    private void saveImageUri(Uri uri) {
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(IMAGE_URI_KEY, uri.toString());
        editor.apply();
    }

    // Ambil URI gambar dari SharedPreferences
    private Uri getImageUri() {
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String uriString = sharedPreferences.getString(IMAGE_URI_KEY, null);
        if (uriString != null) {
            return Uri.parse(uriString);
        }
        return null;
    }

    // Gunakan ActivityResultLauncher untuk menangani hasil dari ImagePicker
    private final ActivityResultLauncher<Intent> imagePickerLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == getActivity().RESULT_OK && result.getData() != null) {
                    Uri uri = result.getData().getData();
                    if (uri != null) {
                        imageView.setImageURI(uri);
                        saveImageUri(uri);  // Simpan URI setelah gambar diambil
                        Log.d("ProfilFragment", "Image URI: " + uri.toString());
                    } else {
                        Log.e("ProfilFragment", "URI is null");
                    }
                } else {
                    Log.e("ProfilFragment", "Failed to pick image");
                }
            }
    );

    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("ProfilFragment", "onCreateView called");
        View view = inflater.inflate(R.layout.fragment_profil, container, false);

        imageView = view.findViewById(R.id.imageprof);
        button = view.findViewById(R.id.floatingActionButton2);

        // Periksa dan minta izin
        checkAndRequestPermissions();

        // Set ActionBar background color
        if (requireActivity().getActionBar() != null) {
            requireActivity().getActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.custom_blue)));
        }

        // Ambil dan tampilkan gambar yang disimpan dari SharedPreferences
        Uri savedImageUri = getImageUri();
        if (savedImageUri != null) {
            imageView.setImageURI(savedImageUri);
        }

        button.setOnClickListener(v -> {
            ImagePicker.with(ProfilFragment.this)
                    .crop()
                    .compress(1024)
                    .maxResultSize(1080, 1080)
                    .createIntent(intent -> {
                        try {
                            imagePickerLauncher.launch(intent);
                        } catch (Exception e) {
                            Log.e("ProfilFragment", "Error launching ImagePicker: " + e.getMessage());
                        }
                        return null;
                    });
        });

        return view;
    }
}
