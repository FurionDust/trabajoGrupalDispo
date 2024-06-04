package com.jimenez.app.ui.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.jimenez.app.data.network.entities.topNews.Data
import com.jimenez.app.databinding.ItemTopNewsBinding
import com.jimenez.app.R
import com.jimenez.app.ui.entities.NewsDataUI

class NewsAdapter(

    private val onClickItem: (NewsDataUI) -> Unit,
    private val onDeleteItem: (Int) -> Unit

) :
    RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    var itemList: List<NewsDataUI> = emptyList()

    class NewsViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val binding = ItemTopNewsBinding.bind(view)

        fun render(
            data: NewsDataUI,
            onClickItem: (NewsDataUI) -> Unit,
            onDeleteItem: (Int) -> Unit
        ) {

            binding.txtTitleNews.text = data.name
            binding.txtUrlNews.text = data.url
            binding.txtDescNews.text = data.description
            binding.imgNews
                .load(data.image) {
                    placeholder(R.drawable.img)
                }
            itemView.setOnClickListener {
                onClickItem(data)
            }
            binding.btnBorrar.setOnClickListener {
                onDeleteItem(adapterPosition)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return NewsViewHolder(
            inflater.inflate(
                R.layout.item_top_news,
                parent,
                false
            )
        )

    }

    override fun getItemCount() = itemList.size

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.render(itemList[position], onClickItem, onDeleteItem)
    }


}