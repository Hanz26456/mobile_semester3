package com.nyok.bottom_navigation.menu_dalam;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.project1762.Helper.ManagmentCart;
import com.nyok.bottom_navigation.R;
import com.nyok.bottom_navigation.databinding.ActivityDetailBinding;
import com.nyok.bottom_navigation.domain.PopularDomain;

public class DetailActivity extends AppCompatActivity {
    private ActivityDetailBinding binding;
    private PopularDomain object;
    private int numberOrder;
    private ManagmentCart managmentCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        managmentCart = new ManagmentCart(this);
        getBundles();
    }

    private void getBundles() {
        object = (PopularDomain) getIntent().getSerializableExtra("object");
        if (object != null) {
            int drawableResourceId = this.getResources().getIdentifier(object.getPicUrl(), "drawable", this.getPackageName());
            Glide.with(this)
                    .load(drawableResourceId)
                    .into(binding.img);

            binding.titleTxt.setText(object.getTitle());
            binding.PriceTxt.setText("$" + object.getPrice());
            binding.descriptionTxt.setText(object.getDescription());
            binding.textView16.setText(object.getReview() + "");
            binding.ratingTxt.setText(object.getScore() + "");

//            binding.addTocartBtn.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    object.setNumberInCart(numberOrder);
//                    managmentCart.insertFood(object);
//                }
//            });
        } else {
            Log.e("DetailActivity", "Object is null");
            Toast.makeText(this, "Data tidak ditemukan", Toast.LENGTH_SHORT).show();
        }
        binding.backbtn.setOnClickListener(v -> finish());
    }
}
