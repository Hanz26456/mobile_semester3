package com.nyok.bottom_navigation.menu_dalam
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.nyok.bottom_navigation.R
import com.nyok.bottom_navigation.adapter.ListsItemsAdapter
import com.nyok.bottom_navigation.databinding.ActivityListItems2Binding
import com.nyok.bottom_navigation.model.MainViewModel


class ListItemsActivity2 : AppCompatActivity() {
    private lateinit var binding: ActivityListItems2Binding
    private val viewModel=MainViewModel()
    private var id: String=""
    private var title: String=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityListItems2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        getBundle()
        initList()
    }
    private fun initList() {
        binding.apply {
            progressBarlist.visibility = View.VISIBLE
            viewModel.recomendation.observe(this@ListItemsActivity2, Observer { items ->
                Log.d("ListItemsActivity2", "Received items: ${items.size}") // Log untuk pengecekan
                viewList.layoutManager = GridLayoutManager(this@ListItemsActivity2, 2)
                viewList.adapter = ListsItemsAdapter(items)
                progressBarlist.visibility = View.GONE
            })
            viewModel.loadFiltered(id)
        }
    }
    private fun getBundle() {
        id=intent.getStringExtra("id")!!
        title=intent.getStringExtra("title")!!
        binding.categoryTxt.text=title
    }
}