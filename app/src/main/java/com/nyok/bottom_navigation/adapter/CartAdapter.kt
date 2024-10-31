package com.nyok.bottom_navigation.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.project1762.Helper.ChangeNumberItemsListener
import com.example.project1762.Helper.ManagmentCart
import com.nyok.bottom_navigation.databinding.ViewholderCartBinding
import com.nyok.bottom_navigation.databinding.ViewholderCategoryBinding
import com.nyok.bottom_navigation.model.ItemsModel

class CartAdapter(
    private val listItemSelected: ArrayList<ItemsModel>,
    context: Context,
    var changeNumberItemsListener: ChangeNumberItemsListener
) : RecyclerView.Adapter<CartAdapter.Viewholder>() {

    class Viewholder(val binding: ViewholderCartBinding) : RecyclerView.ViewHolder(binding.root)

    private val managmentCart = ManagmentCart(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder {
        val binding = ViewholderCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Viewholder(binding)
    }

    override fun onBindViewHolder(holder: Viewholder, @SuppressLint("RecyclerView") position: Int) {
        val item = listItemSelected[position]

        holder.binding.titleTxt.text = item.title
        holder.binding.feeeachtim.text = "$${item.price}"
        holder.binding.totalEachItem.text = "$${Math.round(item.numberInCart * item.price)}"
        holder.binding.numberitemTxt.text = item.numberInCart.toString()

        Glide.with(holder.itemView.context)
            .load(item.drawableId)
            .into(holder.binding.pic)

        holder.binding.pluscartbtn.setOnClickListener {
            managmentCart.plusItem(listItemSelected, position, changeNumberItemsListener)
        }

        holder.binding.minuscartBtn.setOnClickListener {
            managmentCart.minusItem(listItemSelected, position, object : ChangeNumberItemsListener {
                override fun onChanged() {
                    if (item.numberInCart == 0) {
                        listItemSelected.removeAt(position) // Hapus item jika jumlahnya 0
                        notifyItemRemoved(position)
                    } else {
                        notifyItemChanged(position)
                    }
                    changeNumberItemsListener.onChanged()
                }
            })
        }
    }

    override fun getItemCount(): Int = listItemSelected.size
}
