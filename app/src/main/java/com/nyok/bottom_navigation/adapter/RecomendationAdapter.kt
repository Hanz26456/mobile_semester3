package com.nyok.bottom_navigation.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nyok.bottom_navigation.R
import com.nyok.bottom_navigation.databinding.ViewHolderrecomendationBinding
import com.nyok.bottom_navigation.menu_dalam.detail_activity
import com.nyok.bottom_navigation.model.ItemsModel

class RecomendationAdapter(private var items: MutableList<ItemsModel>) : RecyclerView.Adapter<RecomendationAdapter.Viewholder>() {

    class Viewholder(val binding: ViewHolderrecomendationBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder {
        val binding = ViewHolderrecomendationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Viewholder(binding)
    }

    override fun onBindViewHolder(holder: Viewholder, position: Int) {
        val item = items[position]

        with(holder.binding) {
            tittleTxt.text = item.title
            PriceTxt.text = "$${item.price}"
            RatingTxt.text = item.rating.toString()

            // Pastikan drawableId valid sebelum memuat gambar
            if (item.drawableId != 0) {
                Glide.with(holder.itemView.context)
                    .load(item.drawableId)
                    .into(pic)
            } else {
                pic.setImageResource(R.drawable.cat2_1) // Gambar placeholder jika drawableId tidak valid
            }

            root.setOnClickListener {
                // Pastikan item tidak null saat mengklik
                val intent = Intent(holder.itemView.context, detail_activity::class.java).apply {
                    putExtra("object", item) // Mengirimkan seluruh objek ItemsModel
                }
                ContextCompat.startActivity(holder.itemView.context, intent, null)
            }
        }
    }

    override fun getItemCount(): Int = items.size

    // Tambahkan metode ini untuk mendapatkan daftar items
    fun getItems(): MutableList<ItemsModel> {
        return items
    }
}
