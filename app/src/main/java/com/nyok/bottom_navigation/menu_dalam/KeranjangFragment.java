package com.nyok.bottom_navigation.menu_dalam;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.project1762.Helper.ChangeNumberItemsListener;
import com.example.project1762.Helper.ManagmentCart;
import com.nyok.bottom_navigation.R;
import com.nyok.bottom_navigation.adapter.CartAdapter;
import com.nyok.bottom_navigation.databinding.FragmentKeranjangBinding;

public class KeranjangFragment extends Fragment {

    private FragmentKeranjangBinding binding;
    private ManagmentCart managmentCart;
    private double tax;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentKeranjangBinding.inflate(inflater, container, false);
        managmentCart = new ManagmentCart(requireContext());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setVariable();
        calculateCart();
        initCartList();
    }

    private void initCartList() {
        binding.viewCart.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));
        binding.viewCart.setAdapter(new CartAdapter(managmentCart.getListCart(), requireContext(), new ChangeNumberItemsListener() {
            @Override
            public void onChanged() {
                calculateCart();
            }
        }));

        if (managmentCart.getListCart().isEmpty()) {
            binding.emptyTxt.setVisibility(View.VISIBLE);
            binding.scroll.setVisibility(View.GONE);
        } else {
            binding.emptyTxt.setVisibility(View.GONE);
            binding.scroll.setVisibility(View.VISIBLE);
        }
    }

    private void setVariable() {
        binding.BackBtn.setOnClickListener(v -> requireActivity().onBackPressed());

        binding.method1.setOnClickListener(v -> {
            selectMethod1();
        });

        binding.method2.setOnClickListener(v -> {
            selectMethod2();
        });

        // Inisialisasi metode yang dipilih secara default, jika perlu
        selectMethod1(); // Atau `selectMethod2()` jika Anda ingin memulai dengan metode lain yang dipilih
    }

    private void selectMethod1() {
        updateMethod1Selected();
        updateMethod2Unselected();
    }

    private void selectMethod2() {
        updateMethod1Unselected();
        updateMethod2Selected();
    }

    private void updateMethod1Selected() {
        binding.method1.setBackgroundResource(R.drawable.custom_btn_blue);
        binding.methodIc1.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.white))); // Ikon menjadi putih
        binding.method1Title1.setTextColor(ContextCompat.getColor(requireContext(), R.color.white)); // Ubah warna teks menjadi putih
        binding.methodsubtitle1.setTextColor(ContextCompat.getColor(requireContext(), R.color.white)); // Ubah warna teks menjadi putih
    }

    private void updateMethod1Unselected() {
        binding.method1.setBackgroundResource(R.drawable.grey_background);
        binding.methodIc1.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.black))); // Ikon menjadi hitam
        binding.method1Title1.setTextColor(ContextCompat.getColor(requireContext(), R.color.black)); // Ubah warna teks menjadi hitam
        binding.methodsubtitle1.setTextColor(ContextCompat.getColor(requireContext(), R.color.black)); // Ubah warna teks menjadi hitam
    }

    private void updateMethod2Selected() {
        binding.method2.setBackgroundResource(R.drawable.custom_btn_blue);
        binding.methodIc2.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.white))); // Ikon menjadi putih
        binding.method2Title2.setTextColor(ContextCompat.getColor(requireContext(), R.color.white)); // Ubah warna teks menjadi putih
        binding.methodsubtitle2.setTextColor(ContextCompat.getColor(requireContext(), R.color.white)); // Ubah warna teks menjadi putih
    }

    private void updateMethod2Unselected() {
        binding.method2.setBackgroundResource(R.drawable.grey_background);
        binding.methodIc2.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.black))); // Ikon menjadi hitam
        binding.method2Title2.setTextColor(ContextCompat.getColor(requireContext(), R.color.black)); // Ubah warna teks menjadi hitam
        binding.methodsubtitle2.setTextColor(ContextCompat.getColor(requireContext(), R.color.black)); // Ubah warna teks menjadi hitam
    }




    private void calculateCart() {
        double percentTax = 0.02;
        double delivery = 10.0;
        tax = Math.round((managmentCart.getTotalFee() * percentTax) * 100.0) / 100.0;
        double total = Math.round((managmentCart.getTotalFee() + tax + delivery) * 100.0) / 100.0;
        double itemTotal = Math.round(managmentCart.getTotalFee() * 100.0) / 100.0;

        binding.totalFeeTxt.setText("$" + itemTotal);
        binding.TaxTxt.setText("$" + tax);
        binding.deliveryTxt.setText("$" + delivery);
        binding.totalTxt.setText("$" + total);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
