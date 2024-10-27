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
import android.widget.ProgressBar;

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

    private FragmentHomefragmenBinding binding; // Binding untuk fragment_homefragmen.xml
    private DatabaseHelperLogin db;
    public static final String SHARED_PREF_NAME = "myPref";
    private SharedPreferences sharedPreferences;

    private ViewPager2 viewPager2;
    private SlideAdapter slideAdapter;
    private MainViewModel mainViewModel;
    private ProgressBar progressBar, categoryProgressBar, recomendationProgressBar; // Tambahkan ProgressBar untuk rekomendasi
    private RecyclerView categoryRecyclerView, recomendationRecyclerView;
    private CategoryAdapter categoryAdapter;
    private RecomendationAdapter recomendationAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate layout untuk fragment dengan View Binding
        binding = FragmentHomefragmenBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        viewPager2 = binding.viewPager2;
        progressBar = binding.progressbarslider;
        categoryProgressBar = binding.progressBarcategory;
        recomendationProgressBar = binding.progressrecomendation; // Inisialisasi ProgressBar untuk rekomendasi
        categoryRecyclerView = binding.viewcategory;
        recomendationRecyclerView = binding.viewrecomendation;

        // Inisialisasi MainViewModel
        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        mainViewModel.loadBanners(); // Memuat banner

        // Tampilkan ProgressBar saat memuat banner
        progressBar.setVisibility(View.VISIBLE);

        // Mengatur adapter untuk banner
        mainViewModel.getBanners().observe(getViewLifecycleOwner(), sliderModels -> {
            if (sliderModels != null) {
                slideAdapter = new SlideAdapter(sliderModels, viewPager2);
                viewPager2.setAdapter(slideAdapter);
                binding.dotIndicator.setVisibility(View.VISIBLE);
            }

            // Sembunyikan ProgressBar banner setelah delay 1 detik
            new Handler().postDelayed(() -> progressBar.setVisibility(View.GONE), 1000);
        });

        // Menampilkan ProgressBar kategori saat data kategori sedang dimuat
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
        recomendationAdapter = new RecomendationAdapter(new ArrayList<>()); // Initial empty list
        recomendationRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recomendationRecyclerView.setAdapter(recomendationAdapter);

        // Memuat rekomendasi
        mainViewModel.loadRecomendation(); // Panggil untuk memuat data rekomendasi
        mainViewModel.getRecomendation().observe(getViewLifecycleOwner(), recomendations -> {
            if (recomendations != null && !recomendations.isEmpty()) {
                recomendationAdapter.getItems().clear();
                recomendationAdapter.getItems().addAll(recomendations);
                recomendationAdapter.notifyDataSetChanged();
                recomendationRecyclerView.setVisibility(View.VISIBLE);
            } else {
                recomendationRecyclerView.setVisibility(View.GONE);
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
