package com.nyok.bottom_navigation;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.nyok.bottom_navigation.database.DatabaseHelperLogin;
import com.nyok.bottom_navigation.databinding.FragmentProfilBinding; // Pastikan nama binding sesuai dengan layout XML
import com.nyok.bottom_navigation.login.Login;

public class ProfilFragment extends Fragment {
    private FragmentProfilBinding binding; // Menggunakan binding untuk fragment_profil.xml
    private DatabaseHelperLogin db;
    public static final String SHARED_PREF_NAME = "myPref";
    private SharedPreferences sharedPreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout untuk fragment ini
        binding = FragmentProfilBinding.inflate(inflater, container, false);
        View view = binding.getRoot();  // Mengambil root view dari binding

        // Inisialisasi DatabaseHelperLogin
        db = new DatabaseHelperLogin(getActivity());

        // Inisialisasi SharedPreferences
        sharedPreferences = getActivity().getSharedPreferences(SHARED_PREF_NAME, getContext().MODE_PRIVATE);

        // Menghubungkan btn_logout dengan layout
        Button btnkeluar = binding.btnLogout;  // Pastikan ID sesuai dengan layout XML

        // Aksi untuk tombol keluar
        btnkeluar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean updateSession = db.upgradeSession("Kosong", 1);
                if (updateSession) {
                    Toast.makeText(getActivity().getApplicationContext(), "Berhasil Keluar", Toast.LENGTH_SHORT).show();

                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("masuk", false);
                    editor.apply();

                    Intent keluar = new Intent(getActivity(), Login.class);
                    startActivity(keluar);
                    getActivity().finish();  // Gunakan getActivity().finish() untuk menutup Activity
                }
            }
        });

        return view;  // Mengembalikan root view
    }
}
