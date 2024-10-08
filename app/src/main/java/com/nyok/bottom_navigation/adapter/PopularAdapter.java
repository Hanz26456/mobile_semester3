package com.nyok.bottom_navigation.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners;
import com.nyok.bottom_navigation.databinding.ViewholderPupListBinding;
import com.nyok.bottom_navigation.domain.PopularDomain;
import com.nyok.bottom_navigation.menu_dalam.DetailActivity;

import java.util.ArrayList;

public class PopularAdapter extends RecyclerView.Adapter<PopularAdapter.Viewholder> {
    ArrayList<PopularDomain> items;
    Context context;

    public PopularAdapter(ArrayList<PopularDomain> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public PopularAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewholderPupListBinding binding = ViewholderPupListBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        context = parent.getContext();
        return new Viewholder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PopularAdapter.Viewholder holder, int position) {
        holder.binding.tittleTxt.setText(items.get(position).getTitle());
        holder.binding.feeTxt.setText("$" + items.get(position).getPrice());
        holder.binding.scoreTxt.setText("" + items.get(position).getScore());

        int drawableResourced = holder.itemView.getResources().getIdentifier(items.get(position).getPicUrl(), "drawable", holder.itemView.getContext().getPackageName());


        Glide.with(context)
                .load(drawableResourced)
                .transform(new GranularRoundedCorners(30, 30, 0, 0))
                .into(holder.binding.pic);

        holder.itemView.setOnClickListener(v ->  {
                // Aksi ketika item di klik
            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra("object", items.get(position)); // Ini mengirimkan object PopularDomain
            context.startActivity(intent);


        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        ViewholderPupListBinding binding;

        public Viewholder(ViewholderPupListBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
