package com.nyok.bottom_navigation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.nyok.bottom_navigation.R
import com.nyok.bottom_navigation.databinding.ViewHoldermodelBinding

class SelectModelAdapter(
    private val items: List<String>,
    private val onModelSelected: ((String) -> Unit)? = null
) : RecyclerView.Adapter<SelectModelAdapter.ViewHolder>() {

    private var selectedPosition = RecyclerView.NO_POSITION // Menggunakan nilai konstan untuk posisi yang tidak terpilih

    inner class ViewHolder(val binding: ViewHoldermodelBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ViewHoldermodelBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.modelTxt.text = items[position]

        // Menentukan apakah item ini terpilih atau tidak
        val isSelected = position == selectedPosition

        // Mengubah tampilan berdasarkan status terpilih
        holder.binding.modelayout.setBackgroundResource(
            if (isSelected) R.drawable.custom_btn_blue else R.drawable.grey_background
        )
        holder.binding.modelTxt.setTextColor(
            ContextCompat.getColor(holder.itemView.context, if (isSelected) R.color.custom_blue else R.color.black)
        )

        holder.binding.root.setOnClickListener {
            val previousSelectedPosition = selectedPosition
            selectedPosition = if (isSelected) RecyclerView.NO_POSITION else position
            onModelSelected?.invoke(items[selectedPosition]) // Callback model yang dipilih

            // Notifikasi perubahan item terpilih
            notifyItemChanged(previousSelectedPosition)
            notifyItemChanged(selectedPosition)
        }
    }

    override fun getItemCount(): Int = items.size
}
