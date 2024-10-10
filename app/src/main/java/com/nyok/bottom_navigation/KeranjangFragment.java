package com.nyok.bottom_navigation;

import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.nyok.bottom_navigation.adapter.CartAdapter;
import com.nyok.bottom_navigation.database.ChangeNumberItemsListener;
import com.nyok.bottom_navigation.database.ManagmentCart;
import com.nyok.bottom_navigation.databinding.FragmentKeranjangBinding;

public class KeranjangFragment extends Fragment {
    private ManagmentCart managmentCart;
    private FragmentKeranjangBinding binding;
    private double tax;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout untuk fragment menggunakan inflater yang diberikan
        binding = FragmentKeranjangBinding.inflate(inflater, container, false);
        View view = binding.getRoot();  // Menggunakan view binding untuk layout

        managmentCart = new ManagmentCart(requireContext());
        initList();  // Pastikan initList dipanggil setelah layout di-inflate
        statusBarColor();  // Mengubah warna status bar
        calculatorCart();  // Menghitung total belanjaan
        setVariable();  // Mengatur event listener

        return view;  // Mengembalikan view yang sudah di-inflate
    }

    private void statusBarColor() {
        // Mengubah warna status bar, dengan pengecekan null untuk getActivity
        if (getActivity() != null) {
            Window window = getActivity().getWindow();
            window.setStatusBarColor(ContextCompat.getColor(requireContext(), R.color.custom_blue));
        }
    }

    private void initList() {
        Log.d("KeranjangFragment", "initList: called");

        // Cek apakah list dari keranjang tidak null atau tidak kosong
        if (managmentCart.getListCart() == null || managmentCart.getListCart().isEmpty()) {
            binding.emptyTxt.setVisibility(View.VISIBLE);
            binding.scroll.setVisibility(View.GONE);
        } else {
            binding.emptyTxt.setVisibility(View.GONE);
            binding.scroll.setVisibility(View.VISIBLE);
        }

        // Menggunakan RecyclerView untuk menampilkan item dalam keranjang
        binding.cartview.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));
        binding.cartview.setAdapter(new CartAdapter(managmentCart.getListCart(), () -> calculatorCart()));
    }

    private void calculatorCart() {
        double percentTax = 0.02;
        double delivery = 10;

        // Tambahkan log untuk memastikan managmentCart.getTotalFee() mengembalikan nilai yang benar
        double totalFee = managmentCart.getTotalFee();
        Log.d("KeranjangFragment", "Total Fee: " + totalFee);

        // Hitung pajak dan total
        tax = Math.round(totalFee * percentTax * 100) / 100.0;
        double total = Math.round((totalFee + tax + delivery) * 100) / 100.0;
        double itemTotal = Math.round(totalFee * 100) / 100.0;

        // Set nilai ke TextView menggunakan View Binding
        binding.totalfeeTxt.setText("$" + itemTotal);
        binding.taxTxt.setText("$" + tax);
        binding.deliveryTxt.setText("$" + delivery);
        binding.TotalTxt.setText("$" + total);
    }

    private void setVariable() {
        // Menambahkan onClickListener untuk tombol kembali
        binding.backbtn.setOnClickListener(v -> {
            if (getActivity() != null) {
                getActivity().onBackPressed();  // Pastikan getActivity() tidak null sebelum memanggil onBackPressed
            }
        });
    }
}
