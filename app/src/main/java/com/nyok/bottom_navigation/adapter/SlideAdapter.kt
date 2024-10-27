package com.nyok.bottom_navigation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CenterInside
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.nyok.bottom_navigation.databinding.SliderItemContainerBinding
import com.nyok.bottom_navigation.model.SliderModel

class SlideAdapter(
    private var sliderItems: List<SliderModel>,
    private val viewPager2: ViewPager2
) : RecyclerView.Adapter<SlideAdapter.SlideViewHolder>() {

    private lateinit var context: Context
    private val runnable = Runnable {
        // Jika ada logika yang diinginkan, bisa ditambahkan di sini.
        notifyDataSetChanged()
    }

    class SlideViewHolder(private val binding: SliderItemContainerBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun setImage(sliderItem: SliderModel, context: Context) {
            // Menggunakan Glide untuk memuat gambar dengan sudut bulat
            val radius = 20 // radius sudut bulat dalam piksel, bisa disesuaikan
            Glide.with(context)
                .load(sliderItem.imageResId)
                .apply(RequestOptions().transform(CenterInside(), RoundedCorners(radius)))
                .into(binding.imageSlide)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SlideViewHolder {
        context = parent.context
        val binding = SliderItemContainerBinding.inflate(LayoutInflater.from(context), parent, false)
        return SlideViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SlideViewHolder, position: Int) {
        holder.setImage(sliderItems[position], context)
        // Memastikan runnable hanya dijalankan pada slide terakhir
        if (position == sliderItems.size - 1) {
            viewPager2.post(runnable)
        }
    }

    override fun getItemCount(): Int = sliderItems.size
}
