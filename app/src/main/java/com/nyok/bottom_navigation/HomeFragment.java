package com.nyok.bottom_navigation;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

import com.nyok.bottom_navigation.adapter.PopularAdapter;
import com.nyok.bottom_navigation.database.DatabaseHelperLogin;
import com.nyok.bottom_navigation.databinding.FragmentHomefragmenBinding;
import com.nyok.bottom_navigation.domain.PopularDomain;
import com.nyok.bottom_navigation.login.Login;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private FragmentHomefragmenBinding binding;  // Binding untuk fragment_homefragmen.xml
    private DatabaseHelperLogin db;
    public static final String SHARED_PREF_NAME = "myPref";
    private SharedPreferences sharedPreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate layout untuk fragment dengan View Binding
        binding = FragmentHomefragmenBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        // Inisialisasi RecyclerView
        statusBarColor();  // Panggil metode statusBarColor
        initRecyclerView();

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
            if (getActivity() != null) {
                getActivity().finish();  // Gunakan getActivity().finish() untuk menutup Activity
            }
        }

        return view;
    }

    private void statusBarColor() {
        // Pastikan bahwa getActivity() tidak null
        if (getActivity() != null) {
            Window window = getActivity().getWindow();
            window.setStatusBarColor(ContextCompat.getColor(getActivity(), R.color.custom_blue));
        }
    }

    private void initRecyclerView() {
        ArrayList<PopularDomain> items = new ArrayList<>();
        items.add(new PopularDomain("Brown Sugar", "brown_sugar", 15, 4, 500, "Deskripsi item 1"));
        items.add(new PopularDomain("White Leopard", "white_leopard", 10, 4.5, 450, "Deskripsi item 2"));
        items.add(new PopularDomain("Tre Smaker Glass", "tre_smaker_glass", 3, 4.9, 800, "Deskripsi item 3"));

        // Mengatur LayoutManager dan Adapter untuk RecyclerView
        binding.PopularView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.PopularView.setAdapter(new PopularAdapter(items));
    }
}
