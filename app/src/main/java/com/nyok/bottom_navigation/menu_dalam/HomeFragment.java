package com.nyok.bottom_navigation.menu_dalam;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.text.Editable;
import android.text.TextWatcher;

import com.nyok.bottom_navigation.R;
import com.nyok.bottom_navigation.adapter.CategoryAdapter;
import com.nyok.bottom_navigation.adapter.RecomendationAdapter;
import com.nyok.bottom_navigation.adapter.SlideAdapter;
import com.nyok.bottom_navigation.database.DatabaseHelperLogin;
import com.nyok.bottom_navigation.databinding.FragmentHomefragmenBinding;
import com.nyok.bottom_navigation.login.Login;
import com.nyok.bottom_navigation.model.MainViewModel;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private FragmentHomefragmenBinding binding;
    private DatabaseHelperLogin db;
    public static final String SHARED_PREF_NAME = "myPref";
    private SharedPreferences sharedPreferences;

    private ViewPager2 viewPager2;
    private SlideAdapter slideAdapter;
    private MainViewModel mainViewModel;
    private ProgressBar progressBar, categoryProgressBar, recomendationProgressBar;
    private RecyclerView categoryRecyclerView, recomendationRecyclerView;
    private CategoryAdapter categoryAdapter;
    private RecomendationAdapter recomendationAdapter;
    private EditText searchEditText; // Tambahkan referensi EditText

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomefragmenBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        viewPager2 = binding.viewPager2;
        progressBar = binding.progressbarslider;
        categoryProgressBar = binding.progressBarcategory;
        recomendationProgressBar = binding.progressrecomendation;
        categoryRecyclerView = binding.viewcategory;
        recomendationRecyclerView = binding.viewrecomendation;
        searchEditText = binding.searchEditText; // Inisialisasi EditText

        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        mainViewModel.loadBanners();

        // Tampilkan ProgressBar untuk slider/banner
        progressBar.setVisibility(View.VISIBLE);

        // Mengatur adapter untuk banner dan sinkronisasi DotsIndicator
        mainViewModel.getBanners().observe(getViewLifecycleOwner(), sliderModels -> {
            if (sliderModels != null && !sliderModels.isEmpty()) {
                slideAdapter = new SlideAdapter(sliderModels, viewPager2);
                viewPager2.setAdapter(slideAdapter);

                // Sinkronkan DotsIndicator dengan ViewPager2
                binding.dotIndicator.setVisibility(View.VISIBLE);
                binding.dotIndicator.setViewPager2(viewPager2);
            }

            // Sembunyikan ProgressBar banner setelah delay 1 detik
            new Handler().postDelayed(() -> progressBar.setVisibility(View.GONE), 1000);
        });

        // Menampilkan ProgressBar kategori saat data sedang dimuat
        categoryProgressBar.setVisibility(View.VISIBLE);

        // Mengatur adapter untuk kategori
        mainViewModel.loadCategory();
        mainViewModel.getCategory().observe(getViewLifecycleOwner(), categoryModels -> {
            if (categoryModels != null && !categoryModels.isEmpty()) {
                categoryAdapter = new CategoryAdapter(categoryModels);
                categoryRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
                categoryRecyclerView.setAdapter(categoryAdapter);

                // Sembunyikan ProgressBar setelah kategori berhasil dimuat
                categoryProgressBar.setVisibility(View.GONE);
            } else {
                Log.d("HomeFragment", "No categories available.");
                categoryProgressBar.setVisibility(View.GONE);
            }
        });

        // Inisialisasi dan memuat rekomendasi
        recomendationAdapter = new RecomendationAdapter(new ArrayList<>());
        recomendationRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recomendationRecyclerView.setAdapter(recomendationAdapter);

        // Menampilkan ProgressBar rekomendasi saat data sedang dimuat
        recomendationProgressBar.setVisibility(View.VISIBLE);

        // Memuat rekomendasi
        mainViewModel.loadRecomendation();
        mainViewModel.getRecomendation().observe(getViewLifecycleOwner(), recomendations -> {
            if (recomendations != null && !recomendations.isEmpty()) {
                recomendationAdapter.getItems().clear();
                recomendationAdapter.getItems().addAll(recomendations);
                recomendationAdapter.notifyDataSetChanged();

                // Sembunyikan ProgressBar setelah rekomendasi berhasil dimuat
                new Handler().postDelayed(() -> {
                    recomendationProgressBar.setVisibility(View.GONE);
                    recomendationRecyclerView.setVisibility(View.VISIBLE);
                }, 1000);
            } else {
                recomendationRecyclerView.setVisibility(View.GONE);
                recomendationProgressBar.setVisibility(View.GONE);
            }
        });
        ImageView btnNotifikasi = binding.btnnotifikasi; // Ganti dengan ID yang sesuai
        btnNotifikasi.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), Notification_Activity.class);
            startActivity(intent);
        });
        // Menangani klik pada ikon pencarian
        ImageView btnSearch = binding.btnsearch;
        btnSearch.setOnClickListener(v -> {
            if (searchEditText.getVisibility() == View.GONE) {
                searchEditText.setVisibility(View.VISIBLE);
                searchEditText.requestFocus(); // Minta fokus untuk input
            } else {
                searchEditText.setVisibility(View.GONE);
            }
        });

        // Tambahkan TextWatcher untuk menangani input pencarian
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Tidak ada yang perlu dilakukan di sini
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Lakukan pencarian berdasarkan teks yang dimasukkan
                String query = s.toString().toLowerCase();
                categoryAdapter.filter(query); // Implementasikan filter di adapter Anda
                recomendationAdapter.filter(query); // Implementasikan filter di adapter Anda
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Tidak ada yang perlu dilakukan di sini
            }
        });

        return view;
    }

    private void statusBarColor() {
        if (getActivity() != null) {
            Window window = getActivity().getWindow();
            window.setStatusBarColor(ContextCompat.getColor(getActivity(), R.color.custom_blue));
        }
    }
}
