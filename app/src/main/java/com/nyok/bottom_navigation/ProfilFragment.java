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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.nyok.bottom_navigation.database.DatabaseHelperLogin;
import com.nyok.bottom_navigation.databinding.FragmentProfilBinding;
import com.nyok.bottom_navigation.login.Login;

public class ProfilFragment extends Fragment {

    private FragmentProfilBinding binding;
    private DatabaseHelperLogin db;
    public static final String SHARED_PREF_NAME = "myPref";
    private SharedPreferences sharedPreferences;

    private ImageView imageView;
    private FloatingActionButton button;

    // Key untuk menyimpan URI gambar di SharedPreferences
    private static final String PREFS_NAME = "profile_prefs";
    private static final String IMAGE_URI_KEY = "image_uri";

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
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("ProfilFragment", "onCreateView called");

        // Menggunakan ViewBinding untuk fragment_profil.xml
        binding = FragmentProfilBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        imageView = binding.imageprof;  // Gunakan binding untuk ImageView
        button = binding.floatingActionButton2;  // Gunakan binding untuk FloatingActionButton

        // Inisialisasi DatabaseHelperLogin
        db = new DatabaseHelperLogin(getActivity());

        // Inisialisasi SharedPreferences
        sharedPreferences = requireContext().getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        // Periksa dan minta izin
        checkAndRequestPermissions();

        // Set ActionBar background color jika diperlukan
        if (requireActivity().getActionBar() != null) {
            requireActivity().getActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.custom_blue)));
        }

        // Ambil dan tampilkan gambar yang disimpan dari SharedPreferences
        Uri savedImageUri = getImageUri();
        if (savedImageUri != null) {
            imageView.setImageURI(savedImageUri);
        }

        // Set action untuk tombol floating (ImagePicker)
        button.setOnClickListener(v -> {
            ImagePicker.with(ProfilFragment.this)
                    .crop()
                    .compress(1024)
                    .maxResultSize(1080, 1080)
                    .createIntent(intent -> {
                        imagePickerLauncher.launch(intent);
                        return null;
                    });
        });

        // Tombol logout
        Button btnkeluar = binding.btnLogout;  // Gunakan binding untuk btnLogout
        btnkeluar.setOnClickListener(v -> {
            Boolean updateSession = db.upgradeSession("Kosong", 1);
            if (updateSession) {
                Toast.makeText(getActivity().getApplicationContext(), "Berhasil Keluar", Toast.LENGTH_SHORT).show();

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("masuk", false);
                editor.apply();

                Intent keluar = new Intent(getActivity(), Login.class);
                startActivity(keluar);
                getActivity().finish();
            }
        });

        return view;
    }

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
        SharedPreferences.Editor editor = requireContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit();
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
}
