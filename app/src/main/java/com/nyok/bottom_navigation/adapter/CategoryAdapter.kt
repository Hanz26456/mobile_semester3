package com.nyok.bottom_navigation.adapter

import android.content.Intent
import android.content.res.ColorStateList
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nyok.bottom_navigation.R
import com.nyok.bottom_navigation.databinding.ViewholderCategoryBinding
import com.nyok.bottom_navigation.menu_dalam.ListItemsActivity2
import com.nyok.bottom_navigation.model.CategoryModel
import kotlinx.coroutines.delay
import java.util.logging.Handler

class CategoryAdapter(val items: MutableList<CategoryModel>) : RecyclerView.Adapter<CategoryAdapter.Viewholder>() {
    private var selectedPosition = -1 // Ubah ke var
    private var lastSelectedPosition = -1 // Ubah ke var dan perbaiki penamaan

    inner class Viewholder(val binding: ViewholderCategoryBinding) : RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder {
        val binding = ViewholderCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Viewholder(binding)
    }

    override fun onBindViewHolder(holder: Viewholder, position: Int) {
        val item = items[position]
        holder.binding.tittleTxt.text = item.title

        // Set gambar menggunakan resource ID
        holder.binding.pic.setImageResource(item.imageResId) // Perbarui ini untuk menggunakan imageResId

        // Logika pemilihan dan penataan tampilan tetap sama
        if (selectedPosition == position) {
            holder.binding.pic.setBackgroundResource(0)
            holder.binding.mainlayout.setBackgroundResource(R.drawable.custom_btn_blue)
            ImageViewCompat.setImageTintList(
                holder.binding.pic,
                ColorStateList.valueOf(
                    ContextCompat.getColor(
                        holder.itemView.context,
                        R.color.white
                    )
                )
            )
            holder.binding.tittleTxt.visibility = View.VISIBLE
            holder.binding.tittleTxt.setTextColor(
                ContextCompat.getColor(holder.itemView.context, R.color.white)
            )
        } else {
            holder.binding.pic.setBackgroundResource(R.drawable.grey_background)
            holder.binding.mainlayout.setBackgroundResource(0)
            ImageViewCompat.setImageTintList(
                holder.binding.pic,
                ColorStateList.valueOf(
                    ContextCompat.getColor(
                        holder.itemView.context,
                        R.color.black
                    )
                )
            )
            holder.binding.tittleTxt.visibility = View.GONE
            holder.binding.tittleTxt.setTextColor(
                ContextCompat.getColor(
                    holder.itemView.context, R.color.black
                )
            )
        }
        holder.binding.root.setOnClickListener {
            val position = position
            if (position != RecyclerView.NO_POSITION) {
                lastSelectedPosition = selectedPosition
                selectedPosition = position
                notifyItemChanged(lastSelectedPosition)
                notifyItemChanged(selectedPosition)
            }
        android.os.Handler(Looper.getMainLooper()).postDelayed({
            val intent=Intent(holder.itemView.context,ListItemsActivity2::class.java).apply {
                putExtra("id",item.imageResId.toString())
                putExtra("title",item.title)
            }
            ContextCompat.startActivity(holder.itemView.context,intent,null)
        }, 1000)
        }
    }

    override fun getItemCount(): Int = items.size
}
