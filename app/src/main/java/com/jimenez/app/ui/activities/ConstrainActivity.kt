package com.jimenez.app.ui.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.carousel.CarouselLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.jimenez.app.R
import com.jimenez.app.databinding.ActivityConstrainBinding
import com.jimenez.app.logic.usercases.GetAllTopsNewUserCase
import com.jimenez.app.ui.adapters.NewsAdapter
import com.jimenez.app.ui.entities.NewsDataUI
import com.jimenez.app.ui.fragment.MyBottomSheetFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class ConstrainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityConstrainBinding
    private var items: MutableList<NewsDataUI> = mutableListOf()
    private lateinit var newsAdapter: NewsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConstrainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initVariables()
        initListeners()
        initData()


    }

    private fun initVariables() {
        newsAdapter = NewsAdapter(
            { descriptionItem(it) },
            { deleteItem(it)})
        binding.rvTopNews.adapter = newsAdapter


       // binding.rvTopNews.layoutManager = LinearLayoutManager(
        //    this, LinearLayoutManager.HORIZONTAL, false )
        binding.rvTopNews.layoutManager = CarouselLayoutManager()

    }

    private fun initListeners() {
        binding.rvTopNews.adapter = newsAdapter
        initData()
        binding.refreshRV.isRefreshing = false

        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.listarItem -> {
                    Snackbar.make(binding.refreshRV, "Item Listar", Snackbar.LENGTH_LONG).show()
                    true
                }
                R.id.FavItem -> {
                    Snackbar.make(binding.refreshRV, "Fav Item", Snackbar.LENGTH_LONG).show()
                    val bottomSheet = MyBottomSheetFragment()
                    bottomSheet.show(supportFragmentManager, bottomSheet.tag)
                    true
                }
                R.id.NoFavItem -> {
                    Snackbar.make(binding.refreshRV, "No Fav Item", Snackbar.LENGTH_LONG).show()
                    true
                }
                else -> false
            }
        }

        binding.btnInsert.setOnClickListener {
            addItem()
        }
    }

    private fun initData() {
        binding.pgbarLoadData.visibility = View.VISIBLE

        lifecycleScope.launch(Dispatchers.IO) {

            val resultItems = GetAllTopsNewUserCase().invoke()

            withContext(Dispatchers.Main) {

                binding.pgbarLoadData.visibility = View.INVISIBLE

                resultItems.onSuccess {
                    items = it.toMutableList()
                    newsAdapter.itemList = items
                    newsAdapter.notifyDataSetChanged()
                }

                resultItems.onFailure {
                    Snackbar.make(binding.refreshRV, it.message.toString(), Snackbar.LENGTH_LONG)
                        .show()
                }
            }
        }
    }

    private fun descriptionItem(news: NewsDataUI) {
        Snackbar.make(binding.refreshRV,news.name,Snackbar.LENGTH_LONG).show()

        /*Log.d("UUID",news.id)
        val intent = Intent(this, DetailItemActivity::class.java).apply {
            putExtra("id", news.id)
        }
        startActivity(intent)*/
    }

    private fun deleteItem(position: Int) {
        items.removeAt(position)
        newsAdapter.itemList = items
        newsAdapter.notifyItemRemoved(position)
    }

    private fun addItem() {
        items.add(
            NewsDataUI(
                "1",
                "google.com",
                "Noticia Mentira",
                "",
                "Descripcion fantasma",
                "ES"
            )
        )
        newsAdapter.itemList = items
        newsAdapter.notifyItemInserted(items.size-1)
    }
}