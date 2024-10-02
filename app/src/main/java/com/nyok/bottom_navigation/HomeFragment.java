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
import com.nyok.bottom_navigation.login.Login;

public class HomeFragment extends Fragment {
    private Button btnkeluar;
    private DatabaseHelperLogin db;
    public static final String SHARED_PREF_NAME = "myPref";
    private SharedPreferences sharedPreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_homefragmen, container, false);

        // Menghubungkan btnkeluar dengan layout
        btnkeluar = view.findViewById(R.id.btn_logout);

        // Inisialisasi DatabaseHelperLogin
        db = new DatabaseHelperLogin(getActivity());

        // Inisialisasi SharedPreferences
        sharedPreferences = getActivity().getSharedPreferences(SHARED_PREF_NAME, getContext().MODE_PRIVATE);

        // Memeriksa sesi pengguna
        Boolean checksession = db.checkSession("ada");
        if (!checksession) {
            // Jika sesi tidak ada, pindah ke LoginActivity
            Intent login = new Intent(getActivity(), Login.class);
            startActivity(login);
            getActivity().finish();  // Gunakan getActivity().finish() untuk menutup Activity
        }

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

        return view;
    }
}
