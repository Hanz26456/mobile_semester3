package com.nyok.bottom_navigation.adapter
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.nyok.bottom_navigation.R
import com.nyok.bottom_navigation.databinding.ViewHolderrecomendationBinding
import com.nyok.bottom_navigation.menu_dalam.detail_activity
import com.nyok.bottom_navigation.model.ItemsModel

class ListsItemsAdapter (private var items: MutableList<ItemsModel>) : RecyclerView.Adapter<ListsItemsAdapter.Viewholder>(){

    class Viewholder(val binding: ViewHolderrecomendationBinding) : RecyclerView.ViewHolder(binding.root)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder {
        val binding = ViewHolderrecomendationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Viewholder(binding)
    }
    override fun onBindViewHolder(holder: Viewholder, position: Int) {
        val item = items[position]
        Log.d("ListsItemsAdapter", "Binding item at position $position: ${item.title}")
        with(holder.binding) {
            tittleTxt.text = item.title // Tampilkan judul item
            PriceTxt.text = "$${item.price}"
            RatingTxt.text = item.rating.toString()
            if (item.drawableId != 0) {
                Glide.with(holder.itemView.context)
                    .load(item.drawableId)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .into(pic)
            } else {
                pic.setImageResource(R.drawable.cat2_1) // Gambar placeholder
            }
            root.setOnClickListener {
                val intent = Intent(holder.itemView.context, detail_activity::class.java).apply {
                    putExtra("object", item)
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