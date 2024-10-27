package com.nyok.bottom_navigation.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nyok.bottom_navigation.R

class MainViewModel : ViewModel() {
    // LiveData untuk banner
    private val _banners = MutableLiveData<List<SliderModel>>()
    val banners: LiveData<List<SliderModel>>
        get() = _banners

    // LiveData untuk kategori
    private val _category = MutableLiveData<List<CategoryModel>>()
    val category: LiveData<List<CategoryModel>>
        get() = _category

    // LiveData untuk recomendasi
    private  val _recomendation = MutableLiveData<MutableList<ItemsModel>>()
    val recomendation: LiveData<MutableList<ItemsModel>>
        get() = _recomendation


    // Metode untuk memuat banner
    fun loadBanners() {
        // Memuat data banner (misalnya dari drawable)
        val sliderModels: MutableList<SliderModel> = ArrayList()
        sliderModels.add(SliderModel(R.drawable.banner1)) // Ganti dengan gambar yang sesuai
        sliderModels.add(SliderModel(R.drawable.banner2)) // Ganti dengan gambar yang sesuai

        // Set nilai banner
        _banners.value = sliderModels
    }


    fun loadCategory() {
        val categoryModels: MutableList<CategoryModel> = ArrayList()
        categoryModels.add(CategoryModel("Makanan", R.drawable.cat1)) // Gambar harus ada di drawable
        categoryModels.add(CategoryModel("Minuman", R.drawable.cat2))
        categoryModels.add(CategoryModel("Snack", R.drawable.cat3))
        categoryModels.add(CategoryModel("Soda", R.drawable.cat4))
        categoryModels.add(CategoryModel("Apa aja", R.drawable.cat5))
        categoryModels.add(CategoryModel("enaknya", R.drawable.cat6)) // Gambar harus ada di drawable

        _category.value = categoryModels
        Log.d("MainViewModel", "Categories loaded: ${categoryModels.size}")
    }

    fun loadRecomendation() {
        val recommendationModels: MutableList<ItemsModel> = ArrayList()

        recommendationModels.add(ItemsModel(
            title = "Makanan",
            description = "Deskripsi 1",
            drawableId = R.drawable.cat2_1, // Ganti dengan ID drawable yang sesuai
            price = 20.0,
            rating = 4.5,
            numberInCart = 1,
            showRecommended = false,
            categoryId = 1
        ))

        recommendationModels.add(ItemsModel(
            title = "Minuman",
            description = "Deskripsi 2",
            drawableId = R.drawable.cat4_1, // Ganti dengan ID drawable yang sesuai
            price = 30.0,
            rating = 4.0,
            numberInCart = 2,
            showRecommended = false,
            categoryId = 2
        ))

        recommendationModels.add(ItemsModel(
            title = "Apa ini",
            description = "Deskripsi 3",
            drawableId = R.drawable.cat3_1, // Ganti dengan ID drawable yang sesuai
            price = 30.0,
            rating = 4.0,
            numberInCart = 2,
            showRecommended = false,
            categoryId = 2
        ))

        recommendationModels.add(ItemsModel(
            title = "Apa sayang",
            description = "Deskripsi 4",
            drawableId = R.drawable.cat1_1, // Ganti dengan ID drawable yang sesuai
            price = 30.0,
            rating = 4.0,
            numberInCart = 2,
            showRecommended = false,
            categoryId = 2
        ))
        _recomendation.value = recommendationModels
    }

}
