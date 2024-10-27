package com.nyok.bottom_navigation.menu_dalam

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.project1762.Helper.ManagmentCart
import com.nyok.bottom_navigation.R
import com.nyok.bottom_navigation.adapter.PicAdapter
import com.nyok.bottom_navigation.adapter.SelectModelAdapter
import com.nyok.bottom_navigation.databinding.ActivityDetailBinding
import com.nyok.bottom_navigation.model.ItemsModel

class detail_activity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var item: ItemsModel
    private var numberOrder = 1
    private lateinit var managmentCart: ManagmentCart

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        managmentCart = ManagmentCart(this)

        getBundle() // Memperoleh data item dari intent
        initView() // Menginisialisasi tampilan
    }

    private fun getBundle() {
        // Mengambil item dari intent
        item = intent.getParcelableExtra("object") ?: throw IllegalArgumentException("Item tidak ditemukan!")
    }

    private fun initView() {
        // Menampilkan gambar utama dari drawableId
        Glide.with(this)
            .load(item.drawableId)
            .into(binding.img)

        // Mengatur tampilan teks untuk informasi item
        binding.titleTxt.text = item.title
        binding.descriptionTxt.text = item.description
        binding.PriceTxt.text = "$${item.price}"
        binding.ratingTxt.text = "${item.rating} rating"

        // Inisialisasi RecyclerView untuk modelList
        binding.modelList.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.modelList.adapter = SelectModelAdapter(item.model) { selectedModel ->
            // Tindakan saat model dipilih
            // Misalnya, Anda dapat memperbarui tampilan atau menyimpan model yang dipilih
        }

        // Inisialisasi RecyclerView untuk picList
        binding.picList.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.picList.adapter = PicAdapter(item.images) { imageId ->
            // Callback saat gambar dipilih (misalnya, tampilkan gambar yang lebih besar)
            // Anda bisa menambahkan fungsi untuk menampilkan gambar yang lebih besar di sini
        }

        // Mengatur aksi untuk tombol tambah ke keranjang
        binding.addTocartBtn.setOnClickListener {
            item.numberInCart = numberOrder
            managmentCart.insertItem(item)
        }

        // Aksi untuk tombol kembali
        binding.backbtn.setOnClickListener { finish() }
    }
}
