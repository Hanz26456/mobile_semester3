package com.nyok.bottom_navigation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.nyok.bottom_navigation.R
import com.nyok.bottom_navigation.databinding.ViewHolderpicBinding

class PicAdapter(
    private val items: List<Int>,
    private val onImageSelected: (Int) -> Unit
) : RecyclerView.Adapter<PicAdapter.ViewHolder>() {

    private var selectedPosition = -1
    private var lastSelectedPosition = -1

    inner class ViewHolder(val binding: ViewHolderpicBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ViewHolderpicBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        val item = items[position]

        // Memuat gambar dari drawable ID
        Glide.with(holder.binding.pic.context)
            .load(item)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .skipMemoryCache(true)
            .placeholder(R.drawable.cat2_1)
            .error(R.drawable.cat2_1)
            .into(holder.binding.pic)

        holder.binding.root.setOnClickListener {
            lastSelectedPosition = selectedPosition
            selectedPosition = position
            notifyItemChanged(lastSelectedPosition)
            notifyItemChanged(selectedPosition)
            onImageSelected(item) // Memanggil callback untuk gambar yang dipilih
        }

        // Menentukan background berdasarkan status pemilihan
        holder.binding.picLayout.setBackgroundResource(
            if (selectedPosition == position) R.drawable.custom_btn_blue else R.drawable.grey_background
        )
    }

    override fun getItemCount(): Int = items.size
}
