package com.nyok.bottom_navigation.menu_dalam;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.nyok.bottom_navigation.database.DatabaseHelperLogin;
import com.nyok.bottom_navigation.databinding.FragmentSettingBinding;
import com.nyok.bottom_navigation.login.Login;

public class SettingFragment extends Fragment {

    private @NonNull FragmentSettingBinding binding;
    private DatabaseHelperLogin db;
    public static final String SHARED_PREF_NAME = "myPref";
    private SharedPreferences sharedPreferences;

    private ImageView imageView;

    // Key untuk menyimpan URI gambar di SharedPreferences
    private static final String PREFS_NAME = "profile_prefs";
    private static final String IMAGE_URI_KEY = "image_uri";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("SettingFragment", "onCreateView called");

        // Menggunakan ViewBinding untuk fragment_profil.xml
        binding = FragmentSettingBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        imageView = binding.imageProfil;

        // Inisialisasi DatabaseHelperLogin
        db = new DatabaseHelperLogin(getActivity());

        // Inisialisasi SharedPreferences
        sharedPreferences = requireContext().getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        // Ambil dan tampilkan gambar yang disimpan dari SharedPreferences
        Uri savedImageUri = getImageUri();
        if (savedImageUri != null) {
            imageView.setImageURI(savedImageUri);
        }

        // Set action untuk pindah ke EditProfile
        TextView editProfileTextView = binding.editProfile;  // Sesuaikan dengan ID di XML
        editProfileTextView.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), EditProfil.class);  // Ganti sesuai nama activity
            startActivity(intent);
        });

        // Tombol logout
        Button btnkeluar = (Button) binding.logOut;
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
